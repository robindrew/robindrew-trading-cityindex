package com.robindrew.trading.cityindex.platform;

public interface ICityIndexSession {

	CityIndexCredentials getCredentials();

	CityIndexEnvironment getEnvironment();

	boolean hasSessionId();

	String getSessionId();

	void setSessionId(String sessionId);

}
