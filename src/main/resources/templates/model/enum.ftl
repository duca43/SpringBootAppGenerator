package ${packageBase}.model;

public enum  ${enum.name}Enum{
    <#list values as value> ${value}<#if !value?is_last>,</#if></#list>
}