package com.robindrew.trading.cityindex;

import com.robindrew.trading.provider.ITradeDataProviderInstrument;

public interface ICityIndexInstrument extends ITradeDataProviderInstrument {

	int getMarketId();

	InstrumentCategory getCategory();
}
