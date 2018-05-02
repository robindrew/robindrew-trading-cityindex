package com.robindrew.trading.cityindex.platform.rest.executor;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

import com.robindrew.common.text.Strings;
import com.robindrew.common.util.Check;
import com.robindrew.trading.cityindex.platform.CityIndexSession;

public abstract class CityIndexRequest implements IHttpClientRequest {

	private final CityIndexSession session;

	protected CityIndexRequest(CityIndexSession session) {
		this.session = Check.notNull("session", session);
	}

	public CityIndexSession getSession() {
		return session;
	}

	public String getUrl(String path) {
		return session.getEnvironment().getRestUrl() + path;
	}

	public void addStandardHeaders(HttpUriRequest request) {
		String host = null;
		try {
			host = new URL(session.getEnvironment().getRestUrl()).getHost();
		} catch (MalformedURLException e) {
		}

		request.addHeader("Accept", "application/json; charset=UTF-8");
		request.addHeader("Accept-Encoding", "gzip, deflate, sdch, br");
		request.addHeader("Accept-Language", "en-GB,en-US;q=0.8,en;q=0.6,et;q=0.4,it;q=0.2,mt;q=0.2,sv;q=0.2");
		request.addHeader("User-Agent", "HttpClient");
		if (host != null) {
			request.addHeader("Host", host);
		}
	}

	public void setJsonContent(HttpEntityEnclosingRequestBase request, IJsonObject jsonObject) {

		// Build the JSON
		String json = Strings.json(jsonObject, true);

		// Set the HTTP content
		// NOTE: HttpClient adds these headers automatically
		// request.addHeader("Content-Type", "application/json; charset=UTF-8");
		// request.addHeader("Content-Length", String.valueOf(content.length));
		request.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));
	}
}
