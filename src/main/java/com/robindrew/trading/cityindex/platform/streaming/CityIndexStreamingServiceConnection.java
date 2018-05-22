package com.robindrew.trading.cityindex.platform.streaming;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lightstreamer.ls_client.ConnectionInfo;
import com.lightstreamer.ls_client.ConnectionListener;
import com.lightstreamer.ls_client.ExtendedTableInfo;
import com.lightstreamer.ls_client.HandyTableListener;
import com.lightstreamer.ls_client.LSClient;
import com.lightstreamer.ls_client.SubscribedTableKey;
import com.robindrew.trading.cityindex.CityIndexException;
import com.robindrew.trading.cityindex.ICityIndexInstrument;
import com.robindrew.trading.cityindex.platform.ICityIndexSession;
import com.robindrew.trading.platform.streaming.IInstrumentPriceStream;

public class CityIndexStreamingServiceConnection implements AutoCloseable {

	private static final Logger log = LoggerFactory.getLogger(CityIndexStreamingServiceConnection.class);

	private static ConnectionInfo createInfo(ICityIndexSession session) {

		ConnectionInfo info = new ConnectionInfo();
		info.user = session.getCredentials().getUsername();
		info.password = session.getSessionId();
		info.pushServerUrl = session.getEnvironment().getStreamUrl();
		info.adapter = "STREAMINGALL";

		log.info("Username: {}", info.user);
		log.info("Server: {}", info.pushServerUrl);
		return info;
	}

	private final ICityIndexSession session;
	private final LSClient client = new LSClient();
	private final ConnectionInfo info;

	public CityIndexStreamingServiceConnection(ICityIndexSession session) {
		if (session == null) {
			throw new NullPointerException("session");
		}
		this.session = session;
		this.info = createInfo(session);
	}

	public ICityIndexSession getSession() {
		return session;
	}

	public ConnectionInfo getInfo() {
		return info;
	}

	public void connect(ConnectionListener listener) {
		try {
			ConnectionInfo info = getInfo();
			client.openConnection(info, listener);
		} catch (Exception e) {
			throw new CityIndexException(e);
		}
	}

	public void subscribe(IInstrumentPriceStream<ICityIndexInstrument> stream) {
		if (stream instanceof ICityIndexInstrumentPriceStream) {
			subscribe((ICityIndexInstrumentPriceStream) stream);
		} else {
			throw new IllegalArgumentException("Subscription not supported: " + stream);
		}
	}

	public void unsubscribe(IInstrumentPriceStream<ICityIndexInstrument> stream) {
		if (stream instanceof ICityIndexInstrumentPriceStream) {
			unsubscribe((ICityIndexInstrumentPriceStream) stream);
		} else {
			throw new IllegalArgumentException("Subscription not supported: " + stream);
		}
	}

	public void subscribe(ICityIndexInstrumentPriceStream stream) {
		try {
			ExtendedTableInfo tableInfo = stream.getExtendedTableInfo();
			HandyTableListener listener = stream.getHandyTableListener();
			SubscribedTableKey key = client.subscribeTable(tableInfo, listener, false);
			stream.setKey(key);
		} catch (Exception e) {
			throw new CityIndexException(e);
		}
	}

	public void unsubscribe(ICityIndexInstrumentPriceStream stream) {
		try {
			SubscribedTableKey key = stream.getSubscribedTableKey();
			client.unsubscribeTable(key);
		} catch (Exception e) {
			throw new CityIndexException(e);
		}
	}

	@Override
	public void close() {
		client.closeConnection();
	}
}
