package cn.gyw.springboot.webmvc;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * RestTemplate Test on Controller
 */
@RestController
@RequestMapping("/foo")
public class FooController {

    @GetMapping("/get")
    public String httpGet() {
        return LocalDateTime.now() + "get";
    }

    @PutMapping("/put")
    public String httpPut(@RequestBody String body) {
        return body + "/put" + LocalDateTime.now();
    }

    @PostMapping("/post")
    public String httpPost(@RequestBody String body) {
        return body + "/post" + LocalDateTime.now();
    }

    @PatchMapping("/patch")
    public String httpPatch(@RequestBody String body) {
        return body + "/patch" + LocalDateTime.now();
    }

    @DeleteMapping("/delete")
    public String httpDelete(@RequestParam String id) {
        return id + "/delete" + LocalDateTime.now();
    }
}
