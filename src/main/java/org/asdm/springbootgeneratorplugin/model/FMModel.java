package org.asdm.springbootgeneratorplugin.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class FMModel {

	private static FMModel model;

	@Getter
	@Setter
	private FMAppInfo fmAppInfo;

	@Getter
	private final List<FMClass> classes = new ArrayList<>();

	@Getter
	private final List<FMEnumeration> enumerations = new ArrayList<>();

	private FMModel() {
	}
	
	public static FMModel getInstance() {
		if (model == null) {
			model = new FMModel();			
		}
		return model;
	}
}