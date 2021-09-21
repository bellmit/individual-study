package cn.gyw.middleware.activemq;

import cn.gyw.middleware.activemq.config.ActiveMQConfig;
import cn.gyw.middleware.activemq.producer.CommonProducer;

public class ActiveMQApplication {

    public static void main(String[] args) {
        String result = ActiveMQConfig.build().get("activemq.brokerURL");

        System.out.println(result);

        CommonProducer producer = new CommonProducer();
        producer.sendMessage();


    }
}
