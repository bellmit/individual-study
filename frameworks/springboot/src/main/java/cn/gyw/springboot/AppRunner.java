package cn.gyw.springboot;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements ApplicationRunner, ApplicationContextAware {

	private ApplicationContext applicationContext;
	
	@Value("${beans.print.flag}")
	private boolean isPrintBeans;
	@Value("${server.port}")
	private int port;
	@Value("${server.servlet.context-path}")
	private String contentPath;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		if (!args.containsOption("website")) {
			System.err.println("no website specified");
		} else {
			List<String> vals = args.getOptionValues("website");
			String url = vals.get(0);
			System.out.println("url > " + url);
		}

		if (isPrintBeans) {
			Arrays.asList(applicationContext.getBeanDefinitionNames()).forEach(beanName -> {
				System.out.println(">>" + beanName);
			});
		}
		String baseUrl = "http://localhost:" + port + contentPath;
		System.out.println("Base URL        : " + baseUrl);
        System.out.println("GraphQL API     : " + baseUrl + "/graphql");
        System.out.println("GraphiQL        : " + baseUrl + "/graphiql");
        System.out.println("Static resource : " + baseUrl + "/static/{*.html}");
        System.out.println("Swagger ui page : " + baseUrl + "/swagger-ui.html");
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

}
