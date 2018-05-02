package com.robindrew.trading.cityindex.platform.rest.executor;

import java.util.concurrent.atomic.AtomicLong;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Stopwatch;
import com.robindrew.common.text.LineBuilder;
import com.robindrew.common.util.Java;
import com.robindrew.trading.httpclient.HttpClients;

public abstract class HttpClientExecutor<Q extends IHttpClientRequest, R extends IHttpClientResponse> {

	private static final Logger log = LoggerFactory.getLogger(HttpClientExecutor.class);
	private static final AtomicLong count = new AtomicLong(0);

	private boolean logRequest = true;
	private boolean logResponse = true;

	public boolean isLogRequest() {
		return logRequest;
	}

	public boolean isLogResponse() {
		return logResponse;
	}

	public void setLogRequest(boolean logRequest) {
		this.logRequest = logRequest;
	}

	public void setLogResponse(boolean logResponse) {
		this.logResponse = logResponse;
	}

	public String getName() {
		return getClass().getSimpleName();
	}

	public R execute(Q request) {
		long number = count.incrementAndGet();
		try {
			Stopwatch timer = Stopwatch.createStarted();

			// Build the request
			HttpUriRequest httpRequest = request.toRequest();

			// Create a client
			HttpClient httpClient = HttpClientBuilder.create().build();

			// Execute the request
			beforeExecute(number, httpRequest);
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			afterExecute(number, httpRequest, httpResponse);

			// Parse the response
			R response = toResponse(number, httpResponse);
			timer.stop();
			log.info("[{}] executed in {}", getName(), timer);
			return response;

		} catch (Exception e) {
			throw Java.propagate(e);
		}
	}

	protected void beforeExecute(long number, HttpUriRequest request) {
	}

	protected void afterExecute(long number, HttpUriRequest httpRequest, HttpResponse httpResponse) {
	}

	protected void logRequest(long number, HttpUriRequest request) {
		if (isLogRequest()) {

			LineBuilder text = new LineBuilder();
			text.appendLine(request.getRequestLine());
			for (Header header : request.getAllHeaders()) {
				text.append(header.getName()).append(": ").append(header.getValue()).appendLine();
			}
			if (request instanceof HttpEntityEnclosingRequestBase) {
				HttpEntityEnclosingRequestBase base = (HttpEntityEnclosingRequestBase) request;
				text.appendLine();
				text.append(HttpClients.getTextContent(base.getEntity()));
			}
			log.info("[{} Request #{}]\n{}", getName(), number, text);
		}
	}

	public void logResponse(long number, HttpResponse response, String content) {
		if (isLogResponse()) {

			// HTTP response
			LineBuilder text = new LineBuilder();
			text.appendLine(response.getStatusLine());
			for (Header header : response.getAllHeaders()) {
				text.append(header.getName()).append(": ").append(header.getValue()).appendLine();
			}
			text.appendLine();
			if (content != null) {
				text.append(content);
			}
			log.info("[{} Response #{}]\n{}", getName(), number, text);
		}
	}

	protected abstract R toResponse(long number, HttpResponse response);

}
