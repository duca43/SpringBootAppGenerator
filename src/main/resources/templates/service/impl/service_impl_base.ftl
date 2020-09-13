package ${packageBase}.service.impl.base;

import ${packageBase}.exception.No${entity.name}FoundException;
import ${packageBase}.model.${entity.name};
<#if entity.primaryKeyColumnCounter gt 1>
import ${packageBase}.model.${entity.primaryKeyType};
</#if>
import ${packageBase}.repository.${entity.name}Repository;
import ${packageBase}.service.${entity.name}Service;

import java.util.List;

public abstract class ${entity.name}ServiceImplBase implements ${entity.name}Service {

    protected ${entity.name}Repository ${entity.name?uncap_first}Repository;

    public ${entity.name}ServiceImplBase(${entity.name}Repository ${entity.name?uncap_first}Repository) {
        this.${entity.name?uncap_first}Repository = ${entity.name?uncap_first}Repository;
    }

    @Override
    public ${entity.name} findById(${entity.primaryKeyType} id){
        return this.${entity.name?uncap_first}Repository.findById(id).orElse(null);
    }

    @Override
    public List<${entity.name}> findAll(){
        return this.${entity.name?uncap_first}Repository.findAll();
    }

    @Override
    public ${entity.name} save(${entity.name} ${entity.name?uncap_first}){
        return this.${entity.name?uncap_first}Repository.save(${entity.name?uncap_first});
    }

    @Override
    public ${entity.name} update(${entity.name} ${entity.name?uncap_first}){
        if (this.findById(${entity.name?uncap_first}.get${entity.primaryKeyName?cap_first}()) == null) {
            throw new No${entity.name}FoundException(${entity.name?uncap_first}.get${entity.primaryKeyName?cap_first}());
        }

        return this.save(${entity.name?uncap_first});
    }

    @Override
    public void deleteById(${entity.primaryKeyType} id){
        this.${entity.name?uncap_first}Repository.deleteById(id);
    }

    @Override
    public void deleteAll(){
        this.${entity.name?uncap_first}Repository.deleteAll();
    }
}