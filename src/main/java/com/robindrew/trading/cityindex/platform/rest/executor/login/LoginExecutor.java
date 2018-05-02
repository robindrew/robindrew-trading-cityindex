package com.robindrew.trading.cityindex.platform.rest.executor.login;

import com.robindrew.trading.cityindex.platform.rest.executor.CityIndexRestExecutor;

public class LoginExecutor extends CityIndexRestExecutor<LoginRequest, LoginResponse> {

	@Override
	protected LoginResponse parseResponse(String json) {
		return null;
	}

}
