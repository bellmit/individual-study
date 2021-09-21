package cn.gyw.frameworks.consumer.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "camel-service")
public interface CamelFeignClient {

    @GetMapping("/camel/rest/hello-groovy")
    void helloGroovy(@RequestParam("count") Integer count);

    @GetMapping("/hello")
    String sayHello();
}
