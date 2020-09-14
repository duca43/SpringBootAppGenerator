package org.asdm.springbootgeneratorplugin.model;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class MetaColumn extends MetaElement {
    private String type;
    private String visibility;
    private Integer lower;
    private Integer upper;
    private boolean unique;
    private String relationshipType;
    private boolean partOfPrimaryKey;
}
