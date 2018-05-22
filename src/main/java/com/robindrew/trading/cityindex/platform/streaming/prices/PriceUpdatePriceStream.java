package com.robindrew.trading.cityindex.platform.streaming.prices;

import static com.robindrew.common.collect.PopulatingMap.createConcurrentMap;
import static com.robindrew.common.text.Strings.number;
import static com.robindrew.trading.cityindex.platform.streaming.prices.PriceUpdateFields.FIELD_AUDIT_ID;
import static com.robindrew.trading.cityindex.platform.streaming.prices.PriceUpdateFields.FIELD_BID;
import static com.robindrew.trading.cityindex.platform.streaming.prices.PriceUpdateFields.FIELD_CHANGE;
import static com.robindrew.trading.cityindex.platform.streaming.prices.PriceUpdateFields.FIELD_DIRECTION;
import static com.robindrew.trading.cityindex.platform.streaming.prices.PriceUpdateFields.FIELD_MARKET_ID;
import static com.robindrew.trading.cityindex.platform.streaming.prices.PriceUpdateFields.FIELD_OFFER;
import static com.robindrew.trading.cityindex.platform.streaming.prices.PriceUpdateFields.FIELD_TICK_DATE;
import static com.robindrew.trading.cityindex.platform.streaming.prices.PriceUpdateFields.getSubscriptionKey;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lightstreamer.ls_client.ExtendedTableInfo;
import com.lightstreamer.ls_client.HandyTableListener;
import com.lightstreamer.ls_client.SubscribedTableKey;
import com.lightstreamer.ls_client.UpdateInfo;
import com.robindrew.common.collect.PopulatingMap;
import com.robindrew.common.concurrent.IEventConsumer;
import com.robindrew.common.concurrent.LoopingEventConsumerThread;
import com.robindrew.common.date.Delay;
import com.robindrew.common.util.Check;
import com.robindrew.trading.cityindex.ICityIndexInstrument;
import com.robindrew.trading.cityindex.platform.streaming.ICityIndexInstrumentPriceStream;
import com.robindrew.trading.cityindex.platform.streaming.lightstreamer.ExtendedTableInfoBuilder;
import com.robindrew.trading.cityindex.platform.streaming.lightstreamer.LoggingTableListener;
import com.robindrew.trading.platform.streaming.InstrumentPriceStream;

public class PriceUpdatePriceStream extends InstrumentPriceStream<ICityIndexInstrument> implements ICityIndexInstrumentPriceStream {

	private static final Logger log = LoggerFactory.getLogger(PriceUpdatePriceStream.class);

	private static boolean isInvalid(String text) {
		return text == null || text.isEmpty() || text.equals("null") || text.equals("NULL");
	}

	private static String getValue(UpdateInfo update, String fieldName, AtomicReference<String> cached) {
		String value = update.getNewValue(fieldName);
		if (!isInvalid(value)) {
			cached.set(value);
			return value;
		}
		value = update.getOldValue(fieldName);
		if (!isInvalid(value)) {
			cached.set(value);
			return value;
		}
		return cached.get();
	}

	/**
	 * Maintain a count of all updates.
	 */
	private static final PopulatingMap<String, AtomicLong> countMap = createConcurrentMap(key -> new AtomicLong(0));

	private final AtomicBoolean active = new AtomicBoolean(true);

	private final LoopingEventConsumerThread<OnUpdate> eventConsumer;
	private final Delay loggingDelay = new Delay(1, TimeUnit.MINUTES);

	private final HandyTableListener tableListener;
	private final ExtendedTableInfo tableInfo;
	private volatile SubscribedTableKey key = null;

	private final AtomicReference<String> cachedBid = new AtomicReference<>();
	private final AtomicReference<String> cachedOffer = new AtomicReference<>();

	public PriceUpdatePriceStream(ICityIndexInstrument instrument) {
		super(instrument);

		// Create the event consumer
		String threadName = "ChartTickConsumer[" + getInstrument().getName() + "]";
		this.eventConsumer = new LoopingEventConsumerThread<OnUpdate>(threadName, new OnUpdateConsumer());

		// Lightstreamer: Create the table listener
		this.tableListener = new TableListener();

		// Lightstreamer: Create the table info
		ExtendedTableInfoBuilder builder = new ExtendedTableInfoBuilder();
		builder.addItem(getSubscriptionKey(getInstrument()));
		builder.addFields(FIELD_MARKET_ID, FIELD_TICK_DATE, FIELD_BID, FIELD_OFFER, FIELD_CHANGE, FIELD_DIRECTION, FIELD_AUDIT_ID);
		this.tableInfo = builder.build();
	}

	@Override
	public ExtendedTableInfo getExtendedTableInfo() {
		return tableInfo;
	}

	@Override
	public HandyTableListener getHandyTableListener() {
		return tableListener;
	}

	@Override
	public SubscribedTableKey getSubscribedTableKey() {
		if (key == null) {
			throw new IllegalStateException("key not set");
		}
		return key;
	}

	@Override
	public void setKey(SubscribedTableKey key) {
		this.key = Check.notNull("key", key);
	}

	private class OnUpdate {

		private final int itemPos;
		private final String itemName;
		private final UpdateInfo updateInfo;

		public OnUpdate(int itemPos, String itemName, UpdateInfo updateInfo) {
			this.itemPos = itemPos;
			this.itemName = itemName;
			this.updateInfo = updateInfo;
		}

		private long increment(String key) {
			AtomicLong count = countMap.get(key, true);
			return count.incrementAndGet();
		}

		public void execute() {
			long count = increment(itemName);

			// Extract the information we are interested in
			String timestamp = updateInfo.getNewValue(FIELD_TICK_DATE);

			// Bid
			String bid = getValue(updateInfo, FIELD_BID, cachedBid);
			String offer = getValue(updateInfo, FIELD_OFFER, cachedOffer);

			/** Last Traded Volume. */
			// String volume = updateInfo.getNewValue(FIELD_LTV);
			/** Incremental Trading Volume. */
			// String incremental = updateInfo.getNewValue(FIELD_TTV);

			// Invalid update?
			if (isInvalid(timestamp) || isInvalid(bid) || isInvalid(offer)) {
				log.debug("[Invalid Tick] - onUpdate({}, {}, {})", itemName, itemPos, updateInfo);
				return;
			}

			// Enqueue tick ...
			final PriceUpdate tick;
			try {
				log.debug("[Tick] - onUpdate({}, {}, {})", itemName, itemPos, updateInfo);
				// tick = new PriceUpdate(getInstrument(), getPrecision(), itemName, timestamp, bid, offer);
			} catch (Exception e) {
				log.warn("[Invalid Tick] - onUpdate(" + itemPos + ", " + itemName + ", " + updateInfo + ")", e);
				return;
			}

			// Consume tick
			try {

				// IPriceCandle next = tick.toPriceTick();
				// putNextCandle(next);

			} catch (Exception e) {
				log.error("Error consuming tick", e);
			}

			// As these are ticks (spam!) only log after a period of time
			if (loggingDelay.expired(true)) {
				log.info("onUpdate(" + itemName + ") called " + number(count) + " times");
			}
		}
	}

	private class OnUpdateConsumer implements IEventConsumer<OnUpdate> {

		@Override
		public void consumeEvents(List<OnUpdate> events) {
			for (OnUpdate event : events) {
				event.execute();
			}
		}
	}

	private class TableListener extends LoggingTableListener {

		@Override
		public void onUpdate(int itemPos, String itemName, UpdateInfo updateInfo) {
			eventConsumer.publishEvent(new OnUpdate(itemPos, itemName, updateInfo));
		}

		@Override
		public void onUnsubscr(int itemPos, String itemName) {
			super.onUnsubscr(itemPos, itemName);
			active.set(false);
		}

		@Override
		public void onUnsubscrAll() {
			super.onUnsubscrAll();
			active.set(false);
		}
	}

}
