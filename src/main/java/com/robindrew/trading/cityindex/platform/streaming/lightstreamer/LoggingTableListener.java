package com.robindrew.trading.cityindex.platform.streaming.lightstreamer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lightstreamer.ls_client.HandyTableListener;
import com.lightstreamer.ls_client.UpdateInfo;

public class LoggingTableListener implements HandyTableListener {

	private static final Logger log = LoggerFactory.getLogger(LoggingTableListener.class);

	@Override
	public void onUpdate(int itemPos, String itemName, UpdateInfo updateInfo) {
		log.info("onUpdate({} , {}, {})", itemPos, itemName, updateInfo);
	}

	@Override
	public void onSnapshotEnd(int itemPos, String itemName) {
		log.info("onSnapshotEnd({} , {})", itemPos, itemName);
	}

	@Override
	public void onRawUpdatesLost(int itemPos, String itemName, int lostUpdates) {
		log.info("onRawUpdatesLost({}, {}, lostUpdates={})", itemPos, itemName, lostUpdates);
	}

	@Override
	public void onUnsubscr(int itemPos, String itemName) {
		log.info("onUnsubscr(" + itemPos + ", " + itemName + ")");
	}

	@Override
	public void onUnsubscrAll() {
		log.info("onUnsubscrAll()");
	}
}