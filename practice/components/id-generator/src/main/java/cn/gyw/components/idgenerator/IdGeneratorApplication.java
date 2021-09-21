package cn.gyw.components.idgenerator;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ID generator service run application
 */
@SpringBootApplication(scanBasePackages = { "cn.gyw" })
public class IdGeneratorApplication implements CommandLineRunner, ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(IdGeneratorApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("CommandLineRunner run update args");
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("ApplicationRunner run update args");
    }
}
