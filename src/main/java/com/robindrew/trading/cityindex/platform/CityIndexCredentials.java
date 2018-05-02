package com.robindrew.trading.cityindex.platform;

import com.robindrew.common.util.Check;

public class CityIndexCredentials {

	private final String apiKey;
	private final String username;
	private final String password;

	public CityIndexCredentials(String apiKey, String username, String password) {
		this.apiKey = Check.notEmpty("apiKey", apiKey);
		this.username = Check.notEmpty("username", username);
		this.password = Check.notEmpty("password", password);
	}

	public String getApiKey() {
		return apiKey;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	@Override
	public String toString() {
		return username + "/" + apiKey;
	}

}
