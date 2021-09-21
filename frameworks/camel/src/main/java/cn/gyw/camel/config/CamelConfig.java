package cn.gyw.camel.config;

import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.DispatcherServlet;

/**
 *
 */
@Configuration
public class CamelConfig {

    @Value("${server.camel-url}")
    private String camelBaseUrl;

    @Autowired
    private Environment env;

    @Bean
    public Object propertyBean() {
        return new Object() {
            public String get(String key) {
                String value = env.getProperty(key);
                return value;
            }
        };
    }

    /**
     * Spring DispatcherServlet 配置
     * @param dispatcherServlet
     * @return
     */
    @Bean
    public ServletRegistrationBean dispatcherRegistration(DispatcherServlet dispatcherServlet) {
        ServletRegistrationBean registration =
                new ServletRegistrationBean(dispatcherServlet);
        registration.getUrlMappings().clear();
        registration.addUrlMappings("/");
        return registration;
    }

    /**
     * Camel Servlet
     * @return
     */
    @Bean
    public ServletRegistrationBean camelServletRegistrationBean() {
        ServletRegistrationBean registration =
                new ServletRegistrationBean(new CamelHttpTransportServlet(), camelBaseUrl);
        registration.setName("CamelServlet");
        return registration;
    }
}
