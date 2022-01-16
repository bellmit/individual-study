package cn.gyw.backend.application;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.SpringVersion;

/**
 * @desc 启动类
 * @createdTime 2022/1/8 14:46
 */
@EnableAspectJAutoProxy
@SpringBootApplication(scanBasePackages = {"cn.gyw.backend", "cn.gyw.platform"},
        exclude = {FreeMarkerAutoConfiguration.class})
public class Application {

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class)
                .main(SpringVersion.class) // 加载spring版本
                .bannerMode(Banner.Mode.OFF)
                .run(args);
    }
}
