package org.asdm.springbootgeneratorplugin.model;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MetaEnumeration extends MetaElement {

	@Getter
	private final List<String> values = new ArrayList<>();

	public MetaEnumeration(final String name) {
		super(name);
	}
}