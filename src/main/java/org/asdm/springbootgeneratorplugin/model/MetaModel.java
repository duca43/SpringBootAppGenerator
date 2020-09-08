package org.asdm.springbootgeneratorplugin.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MetaModel {

	private static MetaModel model;

	@Setter
	private MetaAppInfo metaAppInfo;

	@Setter
	private String packageBase;

	private final List<MetaEntity> entities = new ArrayList<>();
	private final List<MetaEnumeration> enumerations = new ArrayList<>();

	private MetaModel() {
	}
	
	public static MetaModel getInstance() {
		if (model == null) {
			model = new MetaModel();
		}
		return model;
	}
}