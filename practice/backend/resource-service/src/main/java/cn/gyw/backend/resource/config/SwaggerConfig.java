package cn.gyw.backend.resource.config;

import cn.gyw.components.web.config.AbstractSwaggerConfig;
import cn.gyw.components.web.model.SwaggerProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig extends AbstractSwaggerConfig {

    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.SwaggerPropertiesBuilder.aSwaggerProperties()
                .apiBasePackage("cn.gyw.backend.resource")
                .title("resource-service")
                .description("资源相关后台接口")
                .contactName("masgyw")
                .version("1.0")
                .enableSecurity(false)
                .build();
    }
}
