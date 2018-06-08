package com.robindrew.trading.cityindex;

import com.robindrew.trading.provider.ITradingProviderInstrument;

public interface ICityIndexInstrument extends ITradingProviderInstrument {

	int getMarketId();

	InstrumentCategory getCategory();
}
