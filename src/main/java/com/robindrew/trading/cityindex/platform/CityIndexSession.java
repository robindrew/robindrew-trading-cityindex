package com.robindrew.trading.cityindex.platform;

import com.robindrew.common.util.Check;

public class CityIndexSession implements ICityIndexSession {

	private final CityIndexCredentials credentials;
	private final CityIndexEnvironment environment;

	// The login session
	private volatile String sessionId = "";

	public CityIndexSession(CityIndexCredentials credentials, CityIndexEnvironment environment) {
		this.credentials = Check.notNull("credentials", credentials);
		this.environment = Check.notNull("environment", environment);
	}

	@Override
	public CityIndexCredentials getCredentials() {
		return credentials;
	}

	@Override
	public CityIndexEnvironment getEnvironment() {
		return environment;
	}

	@Override
	public boolean hasSessionId() {
		return sessionId != null;
	}

	@Override
	public String getSessionId() {
		if (sessionId.isEmpty()) {
			throw new IllegalStateException("sessionId not set");
		}
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		if (sessionId.isEmpty()) {
			throw new IllegalArgumentException("sessionId is empty");
		}
		this.sessionId = sessionId;
	}

}
