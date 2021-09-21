package cn.gyw.backend.fileserver.config;

import java.util.Properties;

import cn.gyw.components.web.condition.LinuxCondition;
import cn.gyw.components.web.condition.WindowsCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import cn.gyw.backend.fileserver.model.ServiceProperties;
import cn.gyw.platform.common.util.SnowFlake;

@Configuration
public class RootConfig {

	@Value("${snowflake.datacenterId:10000}")
	private long datacenterId;

	@Value("${snowflake.machineId:10000}")
	private long machineId;

	@Bean
	public SnowFlake snowFlake() {
		return new SnowFlake(datacenterId, machineId);
	}

	@Bean
	@Conditional(WindowsCondition.class)
	public ServiceProperties windowsProperties(@Value("${file-service.windows.storageDir}") String storageDir,
											   @Value("${file-service.windows.mdUrlPattern}") String mdUrlPattern,
											   @Value("${file-service.windows.mdDiskDir}") String mdDiskDir) {
		ServiceProperties serviceProperties = new ServiceProperties();
		serviceProperties.setStorageDir(storageDir);
		serviceProperties.setMdUrlPattern(mdUrlPattern);
		serviceProperties.setMdDiskDir(mdDiskDir);
		return serviceProperties;
	}

	@Bean
	@Conditional(LinuxCondition.class)
	public ServiceProperties linuxProperties(@Value("${file-service.linux.storageDir}") String storageDir,
											   @Value("${file-service.linux.mdUrlPattern}") String mdUrlPattern,
											   @Value("${file-service.linux.mdDiskDir}") String mdDiskDir) {
		ServiceProperties serviceProperties = new ServiceProperties();
		serviceProperties.setStorageDir(storageDir);
		serviceProperties.setMdUrlPattern(mdUrlPattern);
		serviceProperties.setMdDiskDir(mdDiskDir);
		return serviceProperties;
	}

	@Bean
	public ApplicationRunner appRunner(@Autowired Environment env) {
		return new ApplicationRunner() {

			@Override
			public void run(ApplicationArguments args) throws Exception {
				System.out.println("********************System properties***************");
				Properties properties = System.getProperties();
				properties.entrySet().forEach(entry -> {
					System.out.println(entry.getKey() + " : " + entry.getValue());
				});
				System.out.println("*****************************************************");
			}
		};
	}
}
