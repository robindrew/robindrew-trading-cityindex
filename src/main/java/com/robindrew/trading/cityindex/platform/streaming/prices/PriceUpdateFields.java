package com.robindrew.trading.cityindex.platform.streaming.prices;

import com.robindrew.trading.cityindex.ICityIndexInstrument;

public class PriceUpdateFields {

	public static final String FIELD_OFFER = "Offer";
	public static final String FIELD_BID = "Bid";
	public static final String FIELD_TICK_DATE = "TickDate";
	public static final String FIELD_MARKET_ID = "MarketId";
	public static final String FIELD_PRICE = "Price";
	public static final String FIELD_HIGH = "High";
	public static final String FIELD_LOW = "Low";
	public static final String FIELD_CHANGE = "Change";
	public static final String FIELD_DIRECTION = "Direction";
	public static final String FIELD_AUDIT_ID = "AuditId";
	public static final String FIELD_IMPLIED_VOLATILITY = "ImpliedVolatility";
	public static final String FIELD_STATUS_SUMMARY = "StatusSummary";

	public static String getSubscriptionKey(ICityIndexInstrument instrument) {
		return "PRICE." + instrument.getMarketId();
	}

	public static long parseTimestamp(String date) {
		int index1 = date.indexOf('(');
		int index2 = date.lastIndexOf(')');
		return Long.parseLong(date.substring(index1 + 1, index2));
	}
}
