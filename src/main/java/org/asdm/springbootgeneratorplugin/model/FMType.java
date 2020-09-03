package org.asdm.springbootgeneratorplugin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class FMType extends FMElement {

	//Empty string for standard library types
	private String typePackage;

	public FMType(final String name, final String typePackage) {
		super(name);
		this.typePackage = typePackage;
	}
}