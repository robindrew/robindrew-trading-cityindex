package com.robindrew.trading.cityindex.platform.rest.executor;

import static com.robindrew.common.util.Check.notNull;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.GsonBuilder;
import com.robindrew.common.text.Strings;
import com.robindrew.trading.cityindex.platform.CityIndexSession;
import com.robindrew.trading.cityindex.platform.rest.ICityIndexRestService;
import com.robindrew.trading.httpclient.HttpClientException;
import com.robindrew.trading.httpclient.HttpClientExecutor;
import com.robindrew.trading.httpclient.HttpClients;
import com.robindrew.trading.log.ITransactionLog;

public abstract class CityIndexRestExecutor<R> extends HttpClientExecutor<R> {

	private static final Logger log = LoggerFactory.getLogger(CityIndexRestExecutor.class);

	private final ICityIndexRestService rest;

	protected CityIndexRestExecutor(ICityIndexRestService rest) {
		this.rest = notNull("rest", rest);;
	}

	public String getName() {
		String name = getClass().getSimpleName();
		if (name.endsWith("Executor")) {
			name = name.substring(0, name.length() - "Executor".length());
		}
		return name;
	}

	protected boolean logRequest() {
		return true;
	}

	protected boolean logResponse() {
		return true;
	}

	protected ITransactionLog getTransactionLog() {
		return rest.getTransactionLog();
	}

	public CityIndexSession getSession() {
		return rest.getSession();
	}

	protected boolean isLoginAttempt() {
		return false;
	}

	protected String getUrl(String path) {
		return getSession().getEnvironment().getRestUrl() + path;
	}

	protected void addStandardHeaders(HttpUriRequest request) {

		// Basic Headers
		request.addHeader("Accept", "application/json; charset=UTF-8");
		request.addHeader("Accept-Encoding", "gzip, deflate, sdch, br");
		request.addHeader("Accept-Language", "en-GB,en-US;q=0.8,en;q=0.6,et;q=0.4,it;q=0.2,mt;q=0.2,sv;q=0.2");
		request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
		request.addHeader("Host", request.getURI().getHost());
	}

	protected HttpResponse execute(HttpClient client, HttpUriRequest request) throws Exception {
		logRequest(request);
		return super.execute(client, request);
	}

	protected R handleResponse(HttpUriRequest request, HttpResponse response) {

		// Failed!
		if (response.getStatusLine().getStatusCode() != 200) {
			log.warn("[HTTP Response]\n" + HttpClients.toString(response, true));
			throw getFailureException(request, response);
		}

		// Get the content
		String json = HttpClients.getTextContent(response.getEntity());
		logResponse(request, response, json);

		// Parse the JSON
		Class<R> responseType = getResponseType();
		R parsed = new GsonBuilder().create().fromJson(json, responseType);

		// Sanity check (TODO: remove this?)
		String parsedJson = parsed.toString();
		if (!parsedJson.equals(json)) {
			log.warn("Before Parsing: {}", json);
			log.warn("After Parsing:  {}", parsedJson);
		}
		return parsed;

	}

	private HttpClientException getFailureException(HttpUriRequest request, HttpResponse response) {
		return new HttpClientException("Invalid request: " + request.getRequestLine());
	}

	private void logResponse(HttpUriRequest request, HttpResponse response, String json) {
		if (logResponse()) {
			log.info("[HTTP Response]\n" + HttpClients.toString(response, json));
		}

		// Transaction Log
		json = Strings.json(json);
		getTransactionLog().log(request.getURI().toString(), json);
	}

	private void logRequest(HttpUriRequest request) {
		if (logRequest()) {
			log.info("[HTTP Request]\n" + HttpClients.toString(request));
		}
	}

	public void setJsonContent(HttpEntityEnclosingRequestBase request, Object content) {

		// Build the JSON
		String json = Strings.json(content, true);

		// Set the HTTP content (N.B. content type & length headers are automatically added)
		HttpClients.setJsonContent(request, json);
	}

	protected abstract Class<R> getResponseType();

}
