package com.robindrew.trading.cityindex.platform.rest.executor.login;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

	@SerializedName("Session")
	private String session;
	@SerializedName("PasswordChangeRequired")
	private boolean passwordChangeRequired;
	@SerializedName("AllowedAccountOperator")
	private boolean allowedAccountOperator;
	@SerializedName("StatusCode")
	private int statusCode;
	@SerializedName("AdditionalInfo")
	private String additionalInfo;
	@SerializedName("Is2FAEnabled")
	private boolean is2FAEnabled;
	@SerializedName("TwoFAToken")
	private String twoFAToken;
	@SerializedName("Additional2FAMethods")
	private String additional2FAMethods;
	@SerializedName("UserType")
	private int userType;

	public String getSession() {
		return session;
	}

	public boolean isPasswordChangeRequired() {
		return passwordChangeRequired;
	}

	public boolean isAllowedAccountOperator() {
		return allowedAccountOperator;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public boolean isIs2FAEnabled() {
		return is2FAEnabled;
	}

	public String getTwoFAToken() {
		return twoFAToken;
	}

	public String getAdditional2FAMethods() {
		return additional2FAMethods;
	}

	public int getUserType() {
		return userType;
	}
}
