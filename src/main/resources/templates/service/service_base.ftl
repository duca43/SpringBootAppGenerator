package ${packageBase}.service.base;

import ${packageBase}.model.${entity.name};
<#if entity.primaryKeyColumnCounter gt 1>
import ${packageBase}.model.${entity.primaryKeyType};
</#if>
import java.util.List;

public interface ${entity.name}ServiceBase {

    ${entity.name} findById(${entity.primaryKeyType} id);

    List<${entity.name}> findAll();

    ${entity.name} save(${entity.name} ${entity.name?uncap_first});

    ${entity.name} update(${entity.name} ${entity.name?uncap_first});

    void deleteById(${entity.primaryKeyType} id);

    void deleteAll();
}