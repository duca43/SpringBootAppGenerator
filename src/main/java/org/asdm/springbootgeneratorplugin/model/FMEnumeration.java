package org.asdm.springbootgeneratorplugin.model;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class FMEnumeration extends FMType {

	@Getter
	private final List<String> values = new ArrayList<>();
	
	public FMEnumeration(final String name, final String typePackage) {
		super(name, typePackage);
	}
}