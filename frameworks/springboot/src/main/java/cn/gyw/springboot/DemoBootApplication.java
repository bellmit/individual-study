package cn.gyw.springboot;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import cn.gyw.springboot.profiles.ProfileProperties;

@SpringBootApplication(scanBasePackages = { "cn.gyw.springboot", "cn.gyw.platform","springfox.documentation.swagger2" })
public class DemoBootApplication {

	public static void main(String[] args) {
		ApplicationContext context = new SpringApplicationBuilder(DemoBootApplication.class).bannerMode(Banner.Mode.OFF)
//        	.logStartupInfo(false)
				.build().run(args);

		ProfileProperties profileProperties = context.getBean(ProfileProperties.class);
		System.out.println("server.host.name:" + profileProperties.getServerHost());
	}
}
