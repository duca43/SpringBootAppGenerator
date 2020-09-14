package org.asdm.springbootgeneratorplugin.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class MetaEntity extends MetaElement {
    private String visibility;
    private List<MetaColumn> columns;
    private String primaryKeyName;
    private String primaryKeyType;
    private Integer primaryKeyColumnCounter;
}
