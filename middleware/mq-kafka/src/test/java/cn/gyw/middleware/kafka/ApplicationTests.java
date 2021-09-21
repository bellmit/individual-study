package cn.gyw.middleware.kafka;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * 直接启动一个Kafka Server服务，包含四个Broker节点
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationTests.class)
@EmbeddedKafka(count = 4,ports = {9092,9093,9094,9095}, brokerPropertiesLocation = "classpath:kafka.properties")
public class ApplicationTests {

    @Test
    public void contextLoads()throws IOException {
        System.in.read();
    }

}