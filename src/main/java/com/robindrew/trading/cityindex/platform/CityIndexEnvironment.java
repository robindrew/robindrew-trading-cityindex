package com.robindrew.trading.cityindex.platform;

public enum CityIndexEnvironment {

	/** Production Environment. */
	PROD("https://ciapi.cityindex.com/tradingapi/", "https://push.cityindex.com/"),
	/** Demo Environment. */
	DEMO("https://ciapipreprod.cityindextest9.co.uk/TradingApi", "https://pushpreprod.cityindextest9.co.uk");

	private final String restUrl;
	private final String streamUrl;

	private CityIndexEnvironment(String restUrl, String streamUrl) {
		this.restUrl = restUrl;
		this.streamUrl = streamUrl;
	}

	public String getStreamUrl() {
		return streamUrl;
	}

	public String getRestUrl() {
		return restUrl;
	}

}
