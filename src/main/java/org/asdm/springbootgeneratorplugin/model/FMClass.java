package org.asdm.springbootgeneratorplugin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class FMClass extends FMType {
	private String visibility;
	private List<FMProperty> properties = new ArrayList<>();
	private List<String> importedPackages = new ArrayList<>();
}