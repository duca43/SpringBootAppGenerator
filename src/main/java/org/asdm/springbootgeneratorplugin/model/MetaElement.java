package org.asdm.springbootgeneratorplugin.model;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public abstract class MetaElement {
    private String name;
}
