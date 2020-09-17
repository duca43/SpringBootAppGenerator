package ${packageBase}.model;

public enum ${enum.name}{
    <#list values as value> ${value}<#if !value?is_last>,</#if></#list>
}