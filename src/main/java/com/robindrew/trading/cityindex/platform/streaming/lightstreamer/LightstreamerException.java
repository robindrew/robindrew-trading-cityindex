package com.robindrew.trading.cityindex.platform.streaming.lightstreamer;

import com.robindrew.trading.TradingException;

public class LightstreamerException extends TradingException {

	private static final long serialVersionUID = 183561165661611879L;

	public LightstreamerException(String message) {
		super(message);
	}

	public LightstreamerException(Throwable cause) {
		super(cause);
	}

	public LightstreamerException(String message, Throwable cause) {
		super(message, cause);
	}

}
