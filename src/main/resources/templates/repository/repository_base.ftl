package ${packageBase}.repository.base;

import ${packageBase}.model.${entity.name};
<#if entity.primaryKeyColumnCounter gt 1>
import ${packageBase}.model.${entity.primaryKeyType};
</#if>
import org.springframework.data.jpa.repository.JpaRepository;

public interface ${entity.name}RepositoryBase extends JpaRepository<${entity.name}, ${entity.primaryKeyType}> {
}