package cn.gyw.frameworks.h2;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class H2Config {

	@Bean
	InitializingBean saveData(LocationRepository repo) {
		return () -> {
			repo.save(new Location(1L, "1", 38.998064, 117.317267));
			repo.save(new Location(2L, "2", 38.997793, 117.317069));
			repo.save(new Location(3L, "3", 38.998006, 117.317101));
			repo.save(new Location(4L, "4", 38.997814, 117.317332));
		};
	}

}
