package ${packageBase}.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ${packageBase}.controller.base.${entity.name}ControllerBase;
import ${packageBase}.service.${entity.name}Service;
import ${packageBase}.service.base.${entity.name}ServiceBase;

@RestController
@RequestMapping("/${entity.name?uncap_first}")
public class ${entity.name}Controller extends ${entity.name}ControllerBase {

    private final ${entity.name}Service ${entity.name?uncap_first}Service;

    @Autowired
    public ${entity.name}Controller(final ${entity.name}Service ${entity.name?uncap_first}Service) {
        this.${entity.name?uncap_first}Service = ${entity.name?uncap_first}Service;
    }

    @Override
    protected ${entity.name}ServiceBase get${entity.name}Service() {
        return ${entity.name?uncap_first}Service;
    }
}
