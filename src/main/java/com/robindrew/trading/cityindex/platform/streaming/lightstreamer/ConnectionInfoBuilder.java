package com.robindrew.trading.cityindex.platform.streaming.lightstreamer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lightstreamer.ls_client.ConnectionInfo;
import com.robindrew.common.util.Check;
import com.robindrew.trading.cityindex.platform.ICityIndexSession;

public class ConnectionInfoBuilder {

	private static final Logger log = LoggerFactory.getLogger(ConnectionInfoBuilder.class);

	private final ICityIndexSession session;

	public ConnectionInfoBuilder(ICityIndexSession session) {
		this.session = Check.notNull("session", session);
	}

	public ConnectionInfo build() {

		ConnectionInfo info = new ConnectionInfo();
		info.user = session.getCredentials().getUsername();
		info.password = session.getSessionId();
		info.pushServerUrl = session.getEnvironment().getStreamUrl();
		info.adapter = "STREAMINGALL";

		log.info("Username: {}", info.user);
		log.info("Server: {}", info.pushServerUrl);
		return info;
	}

}
