package com.robindrew.trading.cityindex.platform.rest;

import static com.robindrew.common.test.UnitTests.getProperty;

import org.junit.Test;

import com.robindrew.trading.cityindex.platform.CityIndexCredentials;
import com.robindrew.trading.cityindex.platform.CityIndexEnvironment;
import com.robindrew.trading.cityindex.platform.CityIndexSession;
import com.robindrew.trading.cityindex.platform.rest.executor.login.LoginExecutor;
import com.robindrew.trading.log.ITransactionLog;
import com.robindrew.trading.log.StubTransactionLog;

public class CityIndexRestTest {

	@Test
	public void testLogin() {

		String appKey = getProperty("appKey");
		String username = getProperty("username");
		String password = getProperty("password");

		CityIndexEnvironment environment = CityIndexEnvironment.PROD;
		CityIndexCredentials credentials = new CityIndexCredentials(appKey, username, password);
		CityIndexSession session = new CityIndexSession(credentials, environment);
		ITransactionLog transactionLog = new StubTransactionLog();
		CityIndexRestService rest = new CityIndexRestService(session, transactionLog);

		LoginExecutor executor = new LoginExecutor(rest);
		executor.execute();
	}

}
