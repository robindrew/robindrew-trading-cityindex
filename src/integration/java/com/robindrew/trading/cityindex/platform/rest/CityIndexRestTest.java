package com.robindrew.trading.cityindex.platform.rest;

import static com.robindrew.common.test.UnitTests.getProperty;

import org.junit.Test;

import com.lightstreamer.ls_client.ConnectionInfo;
import com.lightstreamer.ls_client.ExtendedTableInfo;
import com.robindrew.trading.cityindex.platform.CityIndexCredentials;
import com.robindrew.trading.cityindex.platform.CityIndexEnvironment;
import com.robindrew.trading.cityindex.platform.CityIndexSession;
import com.robindrew.trading.cityindex.platform.rest.executor.login.LoginExecutor;
import com.robindrew.trading.cityindex.platform.rest.executor.login.LoginResponse;
import com.robindrew.trading.log.ITransactionLog;
import com.robindrew.trading.log.StubTransactionLog;

public class CityIndexRestTest {

	@Test
	public void testLogin() throws Exception {

		String appKey = getProperty("appKey");
		String username = getProperty("username");
		String password = getProperty("password");

		CityIndexEnvironment environment = CityIndexEnvironment.PROD;
		CityIndexCredentials credentials = new CityIndexCredentials(appKey, username, password);
		CityIndexSession session = new CityIndexSession(credentials, environment);
		ITransactionLog transactionLog = new StubTransactionLog();
		CityIndexRestService rest = new CityIndexRestService(session, transactionLog);

		LoginExecutor executor = new LoginExecutor(rest);
		LoginResponse response = executor.execute();

		String sessionId = response.getSession();

		ConnectionInfo info = new ConnectionInfo();
		info.user = session.getCredentials().getUsername();
		info.password = session.getCredentials().getPassword();
		info.pushServerUrl = session.getEnvironment().getStreamUrl();
		info.adapter = "STREAMINGALL";

		String[] items = new String[] { "PRICE.154297" };
		String mode = "MERGE";
		String[] fields = new String[] { "MarketId", "TickDate", "Bid", "Offer", "Price", "High", "Low", "Change", "Direction", "AuditId" };
		boolean snapshot = true;
		ExtendedTableInfo tableInfo = new ExtendedTableInfo(items, mode, fields, snapshot);
		tableInfo.setDataAdapter("PRICES");

	}

}
