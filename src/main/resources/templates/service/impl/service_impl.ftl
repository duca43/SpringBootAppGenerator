package ${packageBase}.service.impl;

import ${packageBase}.service.impl.base.${entity.name}ServiceImplBase;
import ${packageBase}.repository.${entity.name}Repository;
import ${packageBase}.repository.base.${entity.name}RepositoryBase;
import ${packageBase}.service.${entity.name}Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ${entity.name}ServiceImpl extends ${entity.name}ServiceImplBase implements ${entity.name}Service {

    private ${entity.name}Repository ${entity.name?uncap_first}Repository;

    @Autowired
    public ${entity.name}ServiceImpl(${entity.name}Repository ${entity.name?uncap_first}Repository) {
        this.${entity.name?uncap_first}Repository = ${entity.name?uncap_first}Repository;
    }

    @Override
    protected ${entity.name}RepositoryBase get${entity.name}Repository() {
        return this.${entity.name?uncap_first}Repository;
    }
}