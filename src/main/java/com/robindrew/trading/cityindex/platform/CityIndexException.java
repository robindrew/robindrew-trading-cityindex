package com.robindrew.trading.cityindex.platform;

import com.robindrew.trading.TradingException;

public class CityIndexException extends TradingException {

	private static final long serialVersionUID = -9088519888458336881L;

	public CityIndexException(String message) {
		super(message);
	}

	public CityIndexException(Throwable cause) {
		super(cause);
	}

	public CityIndexException(String message, Throwable cause) {
		super(message, cause);
	}

}
