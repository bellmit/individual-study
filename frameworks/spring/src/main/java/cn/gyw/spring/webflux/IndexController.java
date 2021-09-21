package cn.gyw.spring.webflux;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/webflux")
public class IndexController {

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/mono")
    public Mono<String> mono() {
        return Mono.justOrEmpty("this is mono");
    }

    @GetMapping("/flux")
    public Flux<String> flux() {
        return null;
    }
}
