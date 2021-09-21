package ${controllerPackage};

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;

<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if entityClassPackage??>
import ${entityClassPackage};
</#if>
<#if entityDtoPackage??>
import ${entityDtoPackage}.${entityDtoName};
</#if>
<#if servicePackage??>
import ${servicePackage}.${serviceName};
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if moduleLower??>/${moduleLower}</#if>")
<#if superControllerName??>
public class ${controllerName} extends ${superControllerName}<${entityName},${entityDtoName}> {
<#else>
public class ${controllerName} {
</#if>

    @Autowired
    private ${serviceName} ${fieldServiceName};
	
}
