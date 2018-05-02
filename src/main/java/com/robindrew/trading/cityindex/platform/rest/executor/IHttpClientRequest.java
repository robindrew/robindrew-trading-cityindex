package com.robindrew.trading.cityindex.platform.rest.executor;

import org.apache.http.client.methods.HttpUriRequest;

public interface IHttpClientRequest {

	HttpUriRequest toRequest();
}
