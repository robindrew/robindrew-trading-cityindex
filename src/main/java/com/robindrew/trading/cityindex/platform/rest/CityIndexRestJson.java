package com.robindrew.trading.cityindex.platform.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class CityIndexRestJson {

	public static final Gson buildGson() {
		return new GsonBuilder().serializeNulls().disableHtmlEscaping().create();
	}

	public static final String toJson(Object object) {
		return buildGson().toJson(object);
	}

	public static <T> T fromJson(String json, Class<T> type) {
		return buildGson().fromJson(json, type);
	}

}
