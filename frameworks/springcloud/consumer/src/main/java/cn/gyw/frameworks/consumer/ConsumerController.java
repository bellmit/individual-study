package cn.gyw.frameworks.consumer;

import cn.gyw.frameworks.consumer.feign.CamelFeignClient;
import cn.gyw.frameworks.consumer.feign.ProviderFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsumerController {

    @Autowired
    private ProviderFeignClient providerFeignClient;
    @Autowired
    private CamelFeignClient camelFeignClient;

    @RequestMapping("/hello")
    public String sayHello() {
        return providerFeignClient.hello("demo");
    }

    @RequestMapping("/camel")
    public String callCamelService() {
        camelFeignClient.helloGroovy(99);
        return "调用camel-service api 成功";
    }
}
