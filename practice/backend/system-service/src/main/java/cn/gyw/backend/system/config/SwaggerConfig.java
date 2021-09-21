package cn.gyw.backend.system.config;

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
                .apiBasePackage("cn.gyw.backend.system")
                .title("system-service")
                .description("系统相关后台接口")
                .contactName("masgyw")
                .version("1.0")
                .enableSecurity(false)
                .build();
    }
}
