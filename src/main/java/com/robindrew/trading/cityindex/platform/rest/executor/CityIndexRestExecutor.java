package com.robindrew.trading.cityindex.platform.rest.executor;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.util.EntityUtils;

import com.google.common.base.Charsets;
import com.robindrew.common.util.Java;

public abstract class CityIndexRestExecutor<Q extends CityIndexRequest, R extends CityIndexResponse> extends HttpClientExecutor<Q, R> {

	@Override
	protected R toResponse(long number, HttpResponse response) {

		// Read the HTTP content to JSON
		String json = toJson(response.getEntity());
		logResponse(number, response, json);

		// Parse the response from the JSON
		return parseResponse(json);
	}

	private String toJson(HttpEntity entity) {
		try {
			return EntityUtils.toString(entity, Charsets.UTF_8);
		} catch (Exception e) {
			throw Java.propagate(e);
		}
	}

	@Override
	protected void beforeExecute(long number, HttpUriRequest request) {
		logRequest(number, request);
	}

	protected abstract R parseResponse(String json);

}
