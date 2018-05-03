package com.robindrew.trading.cityindex.platform.rest;

import com.robindrew.trading.cityindex.platform.CityIndexSession;
import com.robindrew.trading.log.ITransactionLog;

public class CityIndexRestService implements ICityIndexRestService {

	private final CityIndexSession session;
	private final ITransactionLog transactionLog;

	public CityIndexRestService(CityIndexSession session, ITransactionLog transactionLog) {
		if (session == null) {
			throw new NullPointerException("session");
		}
		if (transactionLog == null) {
			throw new NullPointerException("transactionLog");
		}
		this.session = session;
		this.transactionLog = transactionLog;
	}

	@Override
	public CityIndexSession getSession() {
		return session;
	}

	@Override
	public ITransactionLog getTransactionLog() {
		return transactionLog;
	}

}
