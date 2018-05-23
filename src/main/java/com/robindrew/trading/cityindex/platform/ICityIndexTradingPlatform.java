package com.robindrew.trading.cityindex.platform;

import com.robindrew.trading.cityindex.ICityIndexInstrument;
import com.robindrew.trading.cityindex.platform.rest.ICityIndexRestService;
import com.robindrew.trading.cityindex.platform.streaming.ICityIndexStreamingService;
import com.robindrew.trading.platform.ITradingPlatform;

public interface ICityIndexTradingPlatform extends ITradingPlatform<ICityIndexInstrument> {

	ICityIndexRestService getRestService();

	@Override
	ICityIndexStreamingService getStreamingService();


}
