package com.robindrew.trading.cityindex.platform.rest;

public class CityIndexJsonObject {

	@Override
	public String toString() {
		return CityIndexRestJson.toJson(this);
	}
}
