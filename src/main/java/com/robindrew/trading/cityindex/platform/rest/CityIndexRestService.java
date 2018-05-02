package com.robindrew.trading.cityindex.platform.rest;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robindrew.trading.cityindex.platform.CityIndexSession;
import com.robindrew.trading.httpclient.HttpClientExecutor;
import com.robindrew.trading.log.ITransactionLog;

public class CityIndexRestService<R> extends HttpClientExecutor<R> implements ICityIndexRestService {

	private static final Logger log = LoggerFactory.getLogger(CityIndexRestService.class);

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

	@Override
	public String getName() {
		String name = getClass().getSimpleName();
		if (name.endsWith("Executor")) {
			name = name.substring(0, name.length() - "Executor".length());
		}
		return name;
	}

	@Override
	protected HttpUriRequest createRequest() throws Exception {
		return null;
	}

	@Override
	protected R handleResponse(HttpUriRequest request, HttpResponse response) throws Exception {
		return null;
	}

}
