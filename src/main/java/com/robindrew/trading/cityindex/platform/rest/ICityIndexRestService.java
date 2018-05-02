package com.robindrew.trading.cityindex.platform.rest;

import com.robindrew.trading.cityindex.platform.CityIndexSession;
import com.robindrew.trading.log.ITransactionLog;

public interface ICityIndexRestService {

	CityIndexSession getSession();

	ITransactionLog getTransactionLog();

}
