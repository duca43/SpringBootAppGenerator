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
public class FMProperty extends FMElement  {
	private String type;
	private String visibility;
	private Integer lower;
	private Integer upper;
	private boolean unique;
	private boolean isPartOfRelationship;
	private String relationshipType;
}