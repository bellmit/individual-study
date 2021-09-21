package ${servicePackage};

<#if entityClassPackage??>
import ${entityClassPackage};
</#if>
<#if mapperClassPackage??>
import ${mapperClassPackage};
</#if>
<#if superServiceClassPackage??>
import ${superServiceClassPackage};
</#if>
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
<#if superServiceName??>
public class ${serviceName} extends ${superServiceName}<${entityName}> {
<#else>
public class ${serviceName} {
</#if>

	@Autowired
    private ${mapperName} ${fieldMapperName};
}
