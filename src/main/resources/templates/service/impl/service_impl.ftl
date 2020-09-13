package ${packageBase}.service.impl;

import ${packageBase}.service.impl.base.${entity.name}ServiceImplBase;
import ${packageBase}.repository.${entity.name}Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public abstract class ${entity.name}ServiceImpl extends ${entity.name}ServiceImplBase {

    @Autowired
    public ${entity.name}ServiceImpl(${entity.name}Repository ${entity.name?uncap_first}Repository) {
        super(${entity.name?uncap_first}Repository);
    }
}