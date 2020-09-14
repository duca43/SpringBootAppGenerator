package org.asdm.springbootgeneratorplugin.model;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;


@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class MetaEnumeration extends MetaElement {

    private final List<String> values = new ArrayList<>();
}
