package com.robindrew.trading.cityindex.platform.rest;

import static com.robindrew.common.test.UnitTests.getProperty;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.lightstreamer.ls_client.ConnectionInfo;
import com.lightstreamer.ls_client.ConnectionListener;
import com.lightstreamer.ls_client.ExtendedTableInfo;
import com.lightstreamer.ls_client.HandyTableListener;
import com.lightstreamer.ls_client.LSClient;
import com.lightstreamer.ls_client.PushConnException;
import com.lightstreamer.ls_client.PushServerException;
import com.lightstreamer.ls_client.SubscrException;
import com.lightstreamer.ls_client.SubscribedTableKey;
import com.lightstreamer.ls_client.UpdateInfo;
import com.robindrew.common.util.Threads;
import com.robindrew.trading.cityindex.CityIndexInstrument;
import com.robindrew.trading.cityindex.ICityIndexInstrument;
import com.robindrew.trading.cityindex.platform.CityIndexCredentials;
import com.robindrew.trading.cityindex.platform.CityIndexEnvironment;
import com.robindrew.trading.cityindex.platform.CityIndexSession;
import com.robindrew.trading.cityindex.platform.rest.executor.login.LoginExecutor;
import com.robindrew.trading.cityindex.platform.rest.executor.login.LoginResponse;
import com.robindrew.trading.log.ITransactionLog;
import com.robindrew.trading.log.StubTransactionLog;

public class CityIndexRestTest {

	private static final Logger log = LoggerFactory.getLogger(CityIndexRestTest.class);

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

		List<CityIndexInstrument> instruments1 = Lists.newArrayList(CityIndexInstrument.SPOT_USD_JPY);
		List<CityIndexInstrument> instruments2 = Lists.newArrayList(CityIndexInstrument.SPOT_USD_CHF);

		ConnectionInfo info = new ConnectionInfo();
		info.user = session.getCredentials().getUsername();
		info.password = sessionId;
		info.pushServerUrl = session.getEnvironment().getStreamUrl();
		info.adapter = "STREAMINGALL";

		ExtendedTableInfo tableInfo1 = getTableInfo(instruments1);
		ExtendedTableInfo tableInfo2 = getTableInfo(instruments2);

		LSClient client = new LSClient();
		client.openConnection(info, new MyConnectionListener());

		SubscribedTableKey tableKey1 = client.subscribeTable(tableInfo1, new MyTableListener(), false);
		SubscribedTableKey tableKey2 = client.subscribeTable(tableInfo2, new MyTableListener(), false);

		Threads.sleepForever();
	}

	private ExtendedTableInfo getTableInfo(List<? extends ICityIndexInstrument> instruments) throws SubscrException {
		String[] items = new String[instruments.size()];
		for (int i = 0; i < items.length; i++) {
			items[i] = "PRICE." + instruments.get(i).getMarketId();
		}

		String mode = "MERGE";
		String[] fields = new String[] { "MarketId", "TickDate", "Bid", "Offer", "Price", "High", "Low", "Change", "Direction", "AuditId" };
		boolean snapshot = true;

		ExtendedTableInfo tableInfo = new ExtendedTableInfo(items, mode, fields, snapshot);
		tableInfo.setDataAdapter("PRICES");
		return tableInfo;
	}

	class MyConnectionListener implements ConnectionListener {

		@Override
		public void onActivityWarning(boolean arg0) {

		}

		@Override
		public void onClose() {

		}

		@Override
		public void onConnectionEstablished() {

		}

		@Override
		public void onDataError(PushServerException arg0) {

		}

		@Override
		public void onEnd(int arg0) {

		}

		@Override
		public void onFailure(PushServerException arg0) {

		}

		@Override
		public void onFailure(PushConnException arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onNewBytes(long arg0) {

		}

		@Override
		public void onSessionStarted(boolean arg0) {

		}

	}

	class MyTableListener implements HandyTableListener {

		@Override
		public void onRawUpdatesLost(int arg0, String arg1, int arg2) {

		}

		@Override
		public void onSnapshotEnd(int arg0, String arg1) {

		}

		@Override
		public void onUnsubscr(int arg0, String arg1) {

		}

		@Override
		public void onUnsubscrAll() {

		}

		@Override
		public void onUpdate(int arg0, String arg1, UpdateInfo info) {
			log.info("onUpdate({}, {}, {})", arg0, arg1, info);
		}

	}
}
