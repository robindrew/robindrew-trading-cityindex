package com.robindrew.trading.cityindex;

import com.robindrew.trading.TradingException;

public class CityIndexException extends TradingException {

	private static final long serialVersionUID = -3002939279246429659L;

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
