package com.robindrew.trading.cityindex.platform.streaming;

import static java.util.concurrent.TimeUnit.MINUTES;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robindrew.common.concurrent.LoopingRunnableThread;
import com.robindrew.common.date.UnitTime;
import com.robindrew.common.util.Check;
import com.robindrew.trading.cityindex.platform.ICityIndexTradingPlatform;
import com.robindrew.trading.cityindex.platform.rest.ICityIndexRestService;

public class CityIndexStreamingServiceMonitor implements AutoCloseable {

	private static final Logger log = LoggerFactory.getLogger(CityIndexStreamingServiceMonitor.class);

	private final ICityIndexTradingPlatform platform;
	private final LoopingRunnableThread thread;

	public CityIndexStreamingServiceMonitor(ICityIndexTradingPlatform platform) {
		this.platform = Check.notNull("platform", platform);
		this.thread = new LoopingRunnableThread("CityStreamingServiceMonitor", new Reconnector());
		this.thread.setInterval(new UnitTime(1, MINUTES));
	}

	public void start() {
		thread.start();
	}

	@Override
	public void close() {
		thread.close();
	}

	private class Reconnector implements Runnable {

		@Override
		public void run() {
			try {
				ICityIndexStreamingService streaming = platform.getStreamingService();
				if (!streaming.isConnected()) {
					ICityIndexRestService rest = platform.getRestService();

					// Login
					rest.login();

					// Reconnect
					streaming.connect();
				}
			} catch (Exception e) {
				log.warn("Failed to reconnect", e);
			}
		}
	}
}
