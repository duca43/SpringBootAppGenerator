package ${packageBase}.controller.base;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ${packageBase}.model.${entity.name};
<#if entity.primaryKeyColumnCounter gt 1>
import ${packageBase}.model.${entity.primaryKeyType};
</#if>
import ${packageBase}.service.base.${entity.name}ServiceBase;

import java.util.List;

@RequestMapping("/${entity.name?uncap_first}")
public abstract class ${entity.name}ControllerBase {

    protected abstract ${entity.name}ServiceBase get${entity.name}Service();

<#if entity.primaryKeyColumnCounter lt 2>
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<${entity.name}> findById(@PathVariable ${entity.primaryKeyType} id) {
        return ResponseEntity.ok(get${entity.name}Service().findById(id));
    }
</#if>

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<${entity.name}>> findAll() {
        return ResponseEntity.ok(get${entity.name}Service().findAll());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<${entity.name}> save(@RequestBody ${entity.name} ${entity.name?uncap_first}) {
        return ResponseEntity.ok(get${entity.name}Service().save(${entity.name?uncap_first}));
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<${entity.name}> update(@RequestBody ${entity.name} ${entity.name?uncap_first}) {
        return ResponseEntity.ok(get${entity.name}Service().update(${entity.name?uncap_first}));
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable ${entity.primaryKeyType} id) {
        get${entity.name}Service().deleteById(id);
    }

    @DeleteMapping
    public void delete() {
        get${entity.name}Service().deleteAll();
    }
}
