package cn.gyw.backend.application.config;

import cn.gyw.backend.components.common.config.AbstractSwaggerConfig;
import cn.gyw.backend.components.common.model.SwaggerProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig extends AbstractSwaggerConfig {

    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.SwaggerPropertiesBuilder.aSwaggerProperties()
                .apiBasePackage("cn.gyw.backend.service")
                .title("backend-apis")
                .description("后台接口")
                .contactName("masgyw")
                .version("1.0")
                .enableSecurity(false)
                .build();
    }
}
