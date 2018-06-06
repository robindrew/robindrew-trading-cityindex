package com.robindrew.trading.cityindex.platform.streaming;

import static com.robindrew.trading.provider.TradingProvider.IGINDEX;

import java.util.concurrent.atomic.AtomicReference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lightstreamer.ls_client.ConnectionInfo;
import com.lightstreamer.ls_client.ConnectionListener;
import com.lightstreamer.ls_client.PushConnException;
import com.lightstreamer.ls_client.PushServerException;
import com.robindrew.common.util.Check;
import com.robindrew.common.util.Java;
import com.robindrew.common.util.Quietly;
import com.robindrew.trading.cityindex.ICityIndexInstrument;
import com.robindrew.trading.cityindex.platform.ICityIndexSession;
import com.robindrew.trading.cityindex.platform.rest.ICityIndexRestService;
import com.robindrew.trading.cityindex.platform.streaming.lightstreamer.LighstreamerConnection;
import com.robindrew.trading.cityindex.platform.streaming.priceupdate.PriceUpdatePriceStream;
import com.robindrew.trading.platform.streaming.AbstractStreamingService;
import com.robindrew.trading.platform.streaming.IInstrumentPriceStream;

public class CityIndexStreamingService extends AbstractStreamingService<ICityIndexInstrument> implements ICityIndexStreamingService {

	private static final Logger log = LoggerFactory.getLogger(CityIndexStreamingService.class);

	private final ICityIndexRestService rest;
	private final AtomicReference<LighstreamerConnection> serviceConnection = new AtomicReference<>();

	public CityIndexStreamingService(ICityIndexRestService rest) {
		super(IGINDEX);
		this.rest = Check.notNull("rest", rest);
	}

	@Override
	public boolean isConnected() {
		return serviceConnection.get() != null;
	}

	@Override
	public boolean subscribe(ICityIndexInstrument instrument) {
		if (isSubscribed(instrument)) {
			return true;
		}

		// Initialise by getting the latest price
		// TODO: ITickPriceCandle candle = getLatestPrice(instrument);

		// Create the underlying stream
		PriceUpdatePriceStream stream = new PriceUpdatePriceStream(instrument);
		// TODO: stream.getPrice().update(candle);

		registerStream(stream);
		stream.start();

		// Subscribe
		LighstreamerConnection connection = serviceConnection.get();
		if (connection != null) {
			connection.subscribe(stream);
		}
		return true;
	}

	@Override
	public boolean unsubscribe(ICityIndexInstrument instrument) {
		if (!isSubscribed(instrument)) {
			return false;
		}

		IInstrumentPriceStream<ICityIndexInstrument> stream = getPriceStream(instrument);
		super.unregisterStream(stream.getInstrument());

		LighstreamerConnection connection = serviceConnection.get();
		if (connection != null) {
			connection.unsubscribe(stream);
		}
		return true;
	}

	@Override
	public synchronized void close() {

		// Close Streams
		super.close();

		// Close Connection
		LighstreamerConnection connection = serviceConnection.get();
		if (connection != null) {
			Quietly.close(connection);
			serviceConnection.set(null);
		}
	}

	@Override
	public synchronized void connect() {
		try {

			// Connect
			log.info("Connecting ...");
			ICityIndexSession session = rest.getSession();
			LighstreamerConnection connection = new LighstreamerConnection(session);
			connection.connect(new Listener(connection.getInfo()));
			serviceConnection.set(connection);

			// Subscribe existing subscriptions
			for (IInstrumentPriceStream<ICityIndexInstrument> stream : getPriceStreams()) {
				connection.subscribe(stream);
			}

		} catch (Exception e) {
			log.warn("Failed to connect client", e);
			serviceConnection.set(null);
			throw Java.propagate(e);
		}
	}

	private class Listener implements ConnectionListener {

		private final ConnectionInfo info;

		public Listener(ConnectionInfo info) {
			this.info = info;
		}

		@Override
		public void onConnectionEstablished() {
			log.info("Connection Established: " + info.user + "@" + info.pushServerUrl);
		}

		@Override
		public void onSessionStarted(boolean isPolling) {
			log.info("Session Started (" + (isPolling ? "Polling" : "Non-Polling") + ")");
		}

		@Override
		public void onNewBytes(long bytes) {
			log.trace("onNewBytes(" + bytes + ")");
		}

		@Override
		public void onDataError(PushServerException e) {
			log.warn("Data Error", e);
		}

		@Override
		public void onActivityWarning(boolean warningOn) {
			log.warn("Activity Warning (" + (warningOn ? "Started" : "Finished") + ")");
		}

		@Override
		public void onClose() {
			log.info("Connection Closed");
			close();
		}

		@Override
		public void onEnd(int cause) {
			log.warn("onEnd(" + cause + ")");
		}

		@Override
		public void onFailure(PushServerException e) {
			log.error("onFailure()", e);
		}

		@Override
		public void onFailure(PushConnException e) {
			log.error("onFailure()", e);
		}
	}

}
