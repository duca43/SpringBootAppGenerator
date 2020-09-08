package ${packageBase}.service.impl.base;

import ${packageBase}.model.${entity.name};
import ${packageBase}.service.${entity.name}Service;

import java.util.List;

public abstract class ${entity.name}ServiceImplBase implements ${entity.name}Service {

    ${entity.name} findById(${entity.primaryKeyType} id){
    }

    List<${entity.name}> findAll(){
    }

    ${entity.name} save(${entity.name} ${entity.name?uncap_first}){
    }

    ${entity.name} update(${entity.name} ${entity.name?uncap_first}){
    }

    void delete(${entity.primaryKeyType} id){
    }
}