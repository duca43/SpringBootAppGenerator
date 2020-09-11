package ${packageBase}.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class ${entity.name}Id implements Serializable {

<#list properties as property>
    <#if property.relationshipType??>
    @ManyToOne
    </#if>
    ${property.visibility} ${property.type} ${property.name};

</#list>
}