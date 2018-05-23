package com.robindrew.trading.cityindex.platform.streaming.prices;

import java.math.BigDecimal;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.price.candle.ITickPriceCandle;
import com.robindrew.trading.price.candle.TickPriceCandle;
import com.robindrew.trading.price.decimal.Decimals;
import com.robindrew.trading.price.precision.IPricePrecision;

public class PriceUpdate {

	private final IInstrument instrument;
	private final IPricePrecision precision;
	private final long timestamp;
	private final BigDecimal bid;
	private final BigDecimal ask;

	public PriceUpdate(IInstrument instrument, IPricePrecision precision, String itemName, String date, String bid, String ask) {
		this.timestamp = PriceUpdateFields.toTimestamp(date);
		this.bid = new BigDecimal(bid);
		this.ask = new BigDecimal(ask);
		this.instrument = instrument;
		this.precision = precision;
	}

	public IInstrument getInstrument() {
		return instrument;
	}

	public IPricePrecision getPrecision() {
		return precision;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public BigDecimal getBid() {
		return bid;
	}

	public BigDecimal getAsk() {
		return ask;
	}

	public ITickPriceCandle toPriceTick() {
		BigDecimal bid = getBid();
		BigDecimal ask = getAsk();
		int decimalPlaces = precision.getDecimalPlaces();
		int bidPrice = Decimals.toBigInt(bid, decimalPlaces);
		int askPrice = Decimals.toBigInt(ask, decimalPlaces);
		return new TickPriceCandle(bidPrice, askPrice, getTimestamp(), decimalPlaces);
	}

}
