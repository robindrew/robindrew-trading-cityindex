package com.robindrew.trading.cityindex.platform.streaming;

import com.robindrew.trading.cityindex.ICityIndexInstrument;
import com.robindrew.trading.platform.streaming.IStreamingService;

public interface ICityIndexStreamingService extends IStreamingService<ICityIndexInstrument> {

	boolean isConnected();

	void connect();

}
