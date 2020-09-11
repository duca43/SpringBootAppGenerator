package org.asdm.springbootgeneratorplugin.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MetaEntity extends MetaElement {
	private String visibility;
	private List<MetaColumn> columns = new ArrayList<>();
	private String primaryKeyType;
	private Integer primaryKeyColumnCounter;

	public MetaEntity(final String name, final String visibility) {
		super(name);
		this.visibility = visibility;
	}
}