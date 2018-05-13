package com.robindrew.trading.cityindex.platform.rest;

import static com.robindrew.trading.cityindex.platform.rest.CityIndexRestJson.fromJson;
import static com.robindrew.trading.cityindex.platform.rest.CityIndexRestJson.toJson;

import org.junit.Assert;
import org.junit.Test;

import com.robindrew.trading.cityindex.platform.rest.executor.login.LoginResponse;

public class JsonParsingTest {

	@Test
	public void parseLoginResponse() {
		String json = "{\"Session\":\"4464730d-41c9-466f-b9e5-ba215149bac1\",\"PasswordChangeRequired\":false,\"AllowedAccountOperator\":false,\"StatusCode\":1,\"AdditionalInfo\":null,\"Is2FAEnabled\":false,\"TwoFAToken\":null,\"Additional2FAMethods\":null,\"UserType\":1}";

		LoginResponse object = fromJson(json, LoginResponse.class);
		String toJson = toJson(object);

		Assert.assertEquals(json, toJson);
	}

}
