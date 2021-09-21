package cn.gyw.service.registry;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 服务注册中心
 */
@SpringBootApplication
@EnableEurekaServer
public class RegistryApplication {

	@Value("${server.port}")
	private static int port;
	
	public static void main(String[] args) {
		SpringApplication.run(RegistryApplication.class, args);
		System.out.println("http://localhost:" + port);
	}
}
