package com.robindrew.trading.cityindex.platform.rest;

import com.robindrew.trading.cityindex.platform.ICityIndexSession;
import com.robindrew.trading.cityindex.platform.rest.executor.login.LoginExecutor;
import com.robindrew.trading.cityindex.platform.rest.executor.login.LoginResponse;
import com.robindrew.trading.log.ITransactionLog;

public class CityIndexRestService implements ICityIndexRestService {

	private final ICityIndexSession session;
	private final ITransactionLog transactionLog;

	public CityIndexRestService(ICityIndexSession session, ITransactionLog transactionLog) {
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
	public ICityIndexSession getSession() {
		return session;
	}

	@Override
	public ITransactionLog getTransactionLog() {
		return transactionLog;
	}

	@Override
	public synchronized LoginResponse login() {
		LoginResponse response = new LoginExecutor(this).execute();
		session.setSessionId(response.getSession());
		return response;
	}

	@Override
	public synchronized void logout() {
		// TODO: Logout!
	}

}
