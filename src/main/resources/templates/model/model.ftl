package ${packageBase}.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

<#list libraries as library>
import ${library};
</#list>
<#list properties as property>
<#if property.upper == -1>
import java.util.HashSet;
import java.util.Set;
<#break>
</#if>
</#list>

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
${entity.visibility} class ${entity.name} {
<#if entity.primaryKeyColumnCounter == 0>

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

<#elseif entity.primaryKeyColumnCounter gt 1>

    @EmbeddedId
    private ${entity.name}Id id;

</#if>
<#list properties as property>
<#if entity.primaryKeyColumnCounter == 1 && property.partOfPrimaryKey>

    @Id
    ${property.visibility} ${property.type} ${property.name};

</#if>
<#if property.upper == 1 && !property.partOfPrimaryKey>
<#if property.relationshipType??>
<#if property.relationshipType == "OneToOne">
    @OneToOne
<#elseif property.relationshipType == "ManyToOne">
    @ManyToOne
    @JoinColumn(<#if property.unique??>unique = ${property.unique?c} </#if><#if property.lower??>, nullable = <#if property.lower == 0>true<#else>false</#if></#if>)
</#if>
    ${property.visibility} ${property.type} ${property.name};

<#else>
    @Column(<#if property.unique??>unique = ${property.unique?c} </#if><#if property.lower??>, nullable = <#if property.lower == 0>true<#else>false</#if></#if>)
    ${property.visibility} ${property.type} ${property.name};

</#if>
<#elseif property.upper == -1>
<#if property.relationshipType??>
<#if property.relationshipType == "OneToMany">
    @OneToMany(mappedBy="${property.relationshipOwner}")
<#elseif property.relationshipType == "ManyToMany">
<#if property.relationshipOwner??>
    @ManyToMany(mappedBy="${property.relationshipOwner}")
<#else>
    @ManyToMany
    @JoinTable
</#if>
</#if>
</#if>
    ${property.visibility} Set<${property.type}> ${property.name} = new HashSet<>();

</#if>
</#list>
}