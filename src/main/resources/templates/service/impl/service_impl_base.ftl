package ${packageBase}.service.impl.base;

import ${packageBase}.exception.No${entity.name}FoundException;
import ${packageBase}.model.${entity.name};
<#if entity.primaryKeyColumnCounter gt 1>
import ${packageBase}.model.${entity.primaryKeyType};
</#if>
import ${packageBase}.repository.base.${entity.name}RepositoryBase;
import ${packageBase}.service.base.${entity.name}ServiceBase;

import java.util.List;

public abstract class ${entity.name}ServiceImplBase implements ${entity.name}ServiceBase {

    protected abstract ${entity.name}RepositoryBase get${entity.name}Repository();

    @Override
    public ${entity.name} findById(${entity.primaryKeyType} id){
        return this.get${entity.name}Repository().findById(id).orElse(null);
    }

    @Override
    public List<${entity.name}> findAll(){
        return this.get${entity.name}Repository().findAll();
    }

    @Override
    public ${entity.name} save(${entity.name} ${entity.name?uncap_first}){
        return this.get${entity.name}Repository().save(${entity.name?uncap_first});
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
        this.get${entity.name}Repository().deleteById(id);
    }

    @Override
    public void deleteAll(){
        this.get${entity.name}Repository().deleteAll();
    }
}