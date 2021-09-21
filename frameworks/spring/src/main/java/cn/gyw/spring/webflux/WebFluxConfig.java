package cn.gyw.spring.webflux;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.DispatcherHandler;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.server.WebHandler;

@Configuration
@EnableWebFlux
@ComponentScan(value = "cn.gyw.spring.webflux",
includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = RestController.class),
        @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Controller.class)},
useDefaultFilters = false
)
public class WebFluxConfig implements WebFluxConfigurer {

	@Bean
	public WebHandler webHandler(ApplicationContext applicationContext) {
		DispatcherHandler dispatcherHandler = new DispatcherHandler(applicationContext);
		return dispatcherHandler;
	}

}
