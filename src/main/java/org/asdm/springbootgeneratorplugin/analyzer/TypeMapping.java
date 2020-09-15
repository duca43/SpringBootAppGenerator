package org.asdm.springbootgeneratorplugin.analyzer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TypeMapping {
    private String modelType;
    private String destinationType;
    private String libraryName;
}
