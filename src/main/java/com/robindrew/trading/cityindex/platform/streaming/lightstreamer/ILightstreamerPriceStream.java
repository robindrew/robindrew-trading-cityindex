package com.robindrew.trading.cityindex.platform.streaming.lightstreamer;

import com.lightstreamer.ls_client.ExtendedTableInfo;
import com.lightstreamer.ls_client.HandyTableListener;
import com.lightstreamer.ls_client.SubscribedTableKey;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.platform.streaming.IInstrumentPriceStream;

public interface ILightstreamerPriceStream<I extends IInstrument> extends IInstrumentPriceStream<I> {

	ExtendedTableInfo getExtendedTableInfo();

	HandyTableListener getHandyTableListener();

	SubscribedTableKey getSubscribedTableKey();

	void setKey(SubscribedTableKey key);

}
