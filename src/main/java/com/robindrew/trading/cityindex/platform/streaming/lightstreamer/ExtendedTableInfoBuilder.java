package com.robindrew.trading.cityindex.platform.streaming.lightstreamer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.Lists;
import com.lightstreamer.ls_client.ExtendedTableInfo;
import com.lightstreamer.ls_client.SubscrException;
import com.robindrew.common.util.Check;

public class ExtendedTableInfoBuilder {

	private static String[] toArray(List<String> list) {
		return list.toArray(new String[list.size()]);
	}

	private String mode = ExtendedTableInfo.MERGE;
	private final List<String> fields = new ArrayList<>();
	private final List<String> items = new ArrayList<>();
	private boolean snapshot = true;
	private String dataAdapter = "PRICES";

	public ExtendedTableInfoBuilder setMode(String mode) {
		this.mode = Check.notEmpty("mode", mode);
		return this;
	}

	public ExtendedTableInfoBuilder setDataAdapter(String dataAdapter) {
		this.dataAdapter = Check.notEmpty("dataAdapter", dataAdapter);
		return this;
	}

	public ExtendedTableInfoBuilder setCommandMode() {
		return setMode(ExtendedTableInfo.COMMAND);
	}

	public ExtendedTableInfoBuilder setDistinctMode() {
		return setMode(ExtendedTableInfo.DISTINCT);
	}

	public ExtendedTableInfoBuilder setMergeMode() {
		return setMode(ExtendedTableInfo.MERGE);
	}

	public ExtendedTableInfoBuilder setSnapshot(boolean snapshot) {
		this.snapshot = snapshot;
		return this;
	}

	public ExtendedTableInfoBuilder addFields(String... fields) {
		return addFields(Lists.newArrayList(fields));
	}

	public ExtendedTableInfoBuilder addFields(Collection<? extends String> fields) {
		for (String field : fields) {
			addField(field);
		}
		return this;
	}

	public ExtendedTableInfoBuilder addField(String item) {
		fields.add(Check.notEmpty("item", item));
		return this;
	}

	public ExtendedTableInfoBuilder addItems(String... items) {
		return addItems(Lists.newArrayList(items));
	}

	public ExtendedTableInfoBuilder addItems(Collection<? extends String> items) {
		for (String item : items) {
			addItem(item);
		}
		return this;
	}

	public ExtendedTableInfoBuilder addItem(String item) {
		items.add(Check.notEmpty("item", item));
		return this;
	}

	public ExtendedTableInfo build() {
		if (mode == null) {
			throw new IllegalStateException("mode not set");
		}
		if (fields.isEmpty()) {
			throw new IllegalStateException("no fields set");
		}
		if (items.isEmpty()) {
			throw new IllegalStateException("no items set");
		}

		// Items - instruments we are subscribing for
		String[] items = toArray(this.items);

		// Fields - the fields we want for the instrument updates
		String[] fields = toArray(this.fields);

		try {
			ExtendedTableInfo info = new ExtendedTableInfo(items, mode, fields, snapshot);
			if (dataAdapter != null) {
				info.setDataAdapter(dataAdapter);
			}
			return info;
		} catch (SubscrException e) {
			throw new LightstreamerException(e);
		}

	}

}
