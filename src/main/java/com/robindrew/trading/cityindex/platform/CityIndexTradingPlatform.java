package com.robindrew.trading.cityindex.platform;

import com.robindrew.common.util.Check;
import com.robindrew.trading.cityindex.ICityIndexInstrument;
import com.robindrew.trading.cityindex.platform.rest.ICityIndexRestService;
import com.robindrew.trading.cityindex.platform.streaming.CityIndexStreamingService;
import com.robindrew.trading.cityindex.platform.streaming.ICityIndexStreamingService;
import com.robindrew.trading.platform.TradingPlatform;

public class CityIndexTradingPlatform extends TradingPlatform<ICityIndexInstrument> implements ICityIndexTradingPlatform {

	private final ICityIndexRestService rest;
	private final ICityIndexStreamingService streaming;

	public CityIndexTradingPlatform(ICityIndexRestService rest) {
		this.rest = Check.notNull("rest", rest);
		this.streaming = new CityIndexStreamingService(rest);
	}

	@Override
	public ICityIndexStreamingService getStreamingService() {
		return streaming;
	}

	@Override
	public ICityIndexRestService getRestService() {
		return rest;
	}

}
