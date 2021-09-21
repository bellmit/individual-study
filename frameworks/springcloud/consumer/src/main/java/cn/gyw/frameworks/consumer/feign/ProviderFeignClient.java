package cn.gyw.frameworks.consumer.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "provider-service", url = "127.0.0.1:8090")
public interface ProviderFeignClient {

    @GetMapping("/user/{name}")
    String hello(@PathVariable("name") String name);
}
