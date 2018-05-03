package com.robindrew.trading.cityindex.platform.rest.executor.login;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;

import com.robindrew.trading.cityindex.platform.rest.ICityIndexRestService;
import com.robindrew.trading.cityindex.platform.rest.executor.CityIndexRestExecutor;

public class LoginExecutor extends CityIndexRestExecutor<Boolean> {

	public LoginExecutor(ICityIndexRestService rest) {
		super(rest);
	}

	@Override
	protected Class<Boolean> getResponseType() {
		return Boolean.class;
	}

	@Override
	protected HttpUriRequest createRequest() throws Exception {
		HttpPost post = new HttpPost(getUrl("/session"));
		addStandardHeaders(post);
		setJsonContent(post, new LoginRequest(getSession().getCredentials()));
		return post;
	}

}
