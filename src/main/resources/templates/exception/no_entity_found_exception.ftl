package ${packageBase}.exception;

<#if entity.primaryKeyColumnCounter gt 1>
import ${packageBase}.model.${entity.primaryKeyType};
</#if>
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class No${entity.name}FoundException extends RuntimeException {
    public No${entity.name}FoundException(${entity.primaryKeyType} ${entity.primaryKeyName}) {
        super("${entity.name} with ${entity.primaryKeyName} " + ${entity.primaryKeyName}.toString() + " is not found.");
    }
}