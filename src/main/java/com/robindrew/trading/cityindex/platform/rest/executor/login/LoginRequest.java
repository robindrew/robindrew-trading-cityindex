package com.robindrew.trading.cityindex.platform.rest.executor.login;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;

import com.robindrew.trading.cityindex.platform.CityIndexSession;
import com.robindrew.trading.cityindex.platform.rest.executor.CityIndexRequest;

public class LoginRequest extends CityIndexRequest {

	public LoginRequest(CityIndexSession session) {
		super(session);
	}

	@Override
	public HttpUriRequest toRequest() {
		HttpPost post = new HttpPost(getUrl("/session"));
		addStandardHeaders(post);
		setJsonContent(post, new LoginRequestJson(getSession().getCredentials()));
		return post;
	}

}
