package com.robindrew.trading.cityindex.platform;

import com.robindrew.common.util.Check;

public class CityIndexCredentials {

	private final String appKey;
	private final String username;
	private final String password;

	public CityIndexCredentials(String appKey, String username, String password) {
		this.appKey = Check.notEmpty("appKey", appKey);
		this.username = Check.notEmpty("username", username);
		this.password = Check.notEmpty("password", password);
	}

	public String getAppKey() {
		return appKey;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	@Override
	public String toString() {
		return username + "/" + appKey;
	}

}
