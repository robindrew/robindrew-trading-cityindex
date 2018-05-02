package com.robindrew.trading.cityindex.platform;

import com.robindrew.common.util.Check;

public class CityIndexSession {

	private final CityIndexCredentials credentials;
	private final CityIndexEnvironment environment;

	public CityIndexSession(CityIndexCredentials credentials, CityIndexEnvironment environment) {
		this.credentials = Check.notNull("credentials", credentials);
		this.environment = Check.notNull("environment", environment);
	}

	public CityIndexCredentials getCredentials() {
		return credentials;
	}

	public CityIndexEnvironment getEnvironment() {
		return environment;
	}

}
