package org.asdm.springbootgeneratorplugin.model;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MetaColumn extends MetaElement {
	private String type;
	private String visibility;
	private Integer lower;
	private Integer upper;
	private boolean unique;
	private boolean isPartOfRelationship;
	private String relationshipType;
	private boolean partOfPrimaryKey;

	public MetaColumn(final String name, final String type, final String visibility, final Integer lower, final Integer upper,
					  final boolean unique, final boolean isPartOfRelationship, final String relationshipType, final boolean partOfPrimaryKey) {
		super(name);
		this.type = type;
		this.visibility = visibility;
		this.lower = lower;
		this.upper = upper;
		this.unique = unique;
		this.isPartOfRelationship = isPartOfRelationship;
		this.relationshipType = relationshipType;
		this.partOfPrimaryKey = partOfPrimaryKey;
	}
}