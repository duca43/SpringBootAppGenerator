package ${packageBase}.repository.base;

import ${packageBase}.model.${entity.name};
import org.springframework.data.jpa.repository.JpaRepository;

public interface ${entity.name}RepositoryBase extends JpaRepository<${entity.name}, ${entity.primaryKeyType}> {
}