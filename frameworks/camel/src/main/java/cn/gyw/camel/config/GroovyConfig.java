package cn.gyw.camel.config;

import groovy.lang.GroovyShell;
import org.apache.camel.Exchange;
import org.apache.camel.language.groovy.GroovyShellFactory;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.ImportCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// @Configuration
public class GroovyConfig implements GroovyShellFactory {

    /**
     * 自定义GroovyShell
     * @param exchange
     * @return
     */
    @Bean
    @Override
    public GroovyShell createGroovyShell(Exchange exchange) {
        ImportCustomizer importCustomizer = new ImportCustomizer();
        // 默认导入类
        // importCustomizer.addStaticStars("**");
        CompilerConfiguration configuration = new CompilerConfiguration();
        configuration.addCompilationCustomizers(importCustomizer);

        return new GroovyShell(configuration);
    }
}
