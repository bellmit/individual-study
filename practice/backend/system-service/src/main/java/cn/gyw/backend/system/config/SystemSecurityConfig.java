package cn.gyw.backend.system.config;

import cn.gyw.backend.system.model.entity.Resource;
import cn.gyw.backend.system.service.AdminService;
import cn.gyw.backend.system.service.ResourceService;
import cn.gyw.components.security.component.DynamicSecurityService;
import cn.gyw.components.security.config.BaseSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SystemSecurityConfig extends BaseSecurityConfig {

    @Autowired
    private AdminService adminService;
    @Autowired
    private ResourceService resourceService;

    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        //获取登录用户信息
        return username -> adminService.loadUserByUsername(username);
    }

    @Bean
    public DynamicSecurityService dynamicSecurityService() {
        return () -> {
            Map<String, ConfigAttribute> map = new ConcurrentHashMap<>();
            List<Resource> resourceList = resourceService.queryAll();
            for (Resource resource : resourceList) {
                map.put(resource.getUrl(), new SecurityConfig(resource.getId() + ":" + resource.getName()));
            }
            return map;
        };
    }
}
