package cn.gyw.backend.im.config;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
@EnableScheduling
public class RootConfig implements SchedulingConfigurer {

    /**
     * 配置定时任务参数
     * @param taskRegistrar
     */
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        // 线程池
        ScheduledExecutorService ses =
                Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
        taskRegistrar.setScheduler(ses);
    }

    @Bean
    public ApplicationRunner applicationRunner(
            @Value("${server.servlet.context-path}") String contextPath,
            @Value("${server.port}") int port) {
        return (args) -> {
        	String basePath = "http://127.0.0.1:" + port + contextPath;
        	System.out.println("Base url     >>" + basePath);
        	System.out.println("Home page url>>" + (basePath + "/static/home.html"));
        };
    }
}
