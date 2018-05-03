package com.robindrew.trading.cityindex.platform.rest.executor.login;

import com.google.gson.annotations.SerializedName;
import com.robindrew.trading.cityindex.platform.CityIndexCredentials;

public final class LoginRequest {

	@SerializedName("Password")
	private final String password;
	@SerializedName("AppVersion")
	private final String version;
	@SerializedName("AppComments")
	private final String comments;
	@SerializedName("UserName")
	private final String username;
	@SerializedName("AppKey")
	private final String appKey;

	public LoginRequest(CityIndexCredentials credentials) {
		this.appKey = credentials.getAppKey();
		this.username = credentials.getUsername();
		this.password = credentials.getPassword();
		this.comments = "";
		this.version = "1";
	}

	public String getPassword() {
		return password;
	}

	public String getVersion() {
		return version;
	}

	public String getComments() {
		return comments;
	}

	public String getUsername() {
		return username;
	}

	public String getAppKey() {
		return appKey;
	}
}