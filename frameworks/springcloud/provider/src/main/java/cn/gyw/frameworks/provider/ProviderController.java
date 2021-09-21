package cn.gyw.frameworks.provider;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProviderController {

    @GetMapping("/user/{name}")
    public String getUser(@PathVariable String name) {
        return "Hello, " + name;
    }

}
