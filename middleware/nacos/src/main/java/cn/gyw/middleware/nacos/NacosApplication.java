package cn.gyw.middleware.nacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @desc
 * @createdTime 2021/12/22 23:06
 */
@SpringBootApplication
public class NacosApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(NacosApplication.class);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

}
