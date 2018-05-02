package com.robindrew.trading.cityindex.platform.rest;

import org.junit.Test;

import com.robindrew.trading.cityindex.platform.CityIndexCredentials;
import com.robindrew.trading.cityindex.platform.CityIndexEnvironment;
import com.robindrew.trading.cityindex.platform.CityIndexSession;
import com.robindrew.trading.cityindex.platform.rest.executor.login.LoginExecutor;
import com.robindrew.trading.cityindex.platform.rest.executor.login.LoginRequest;

public class CityIndexRestTest {

	@Test
	public void testLogin() {

		String apiKey = System.getProperty("apiKey");
		String username = System.getProperty("username");
		String password = System.getProperty("password");
		CityIndexEnvironment environment = CityIndexEnvironment.DEMO;

		CityIndexCredentials credentials = new CityIndexCredentials(apiKey, username, password);

		CityIndexSession session = new CityIndexSession(credentials, environment);
		LoginRequest request = new LoginRequest(session);

		LoginExecutor executor = new LoginExecutor();
		executor.execute(request);
	}

}
