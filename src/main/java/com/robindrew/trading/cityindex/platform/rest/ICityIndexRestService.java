package com.robindrew.trading.cityindex.platform.rest;

import com.robindrew.trading.cityindex.platform.ICityIndexSession;
import com.robindrew.trading.cityindex.platform.rest.executor.login.LoginResponse;
import com.robindrew.trading.log.ITransactionLog;

public interface ICityIndexRestService {

	ICityIndexSession getSession();

	ITransactionLog getTransactionLog();

	LoginResponse login();

	void logout();

}
