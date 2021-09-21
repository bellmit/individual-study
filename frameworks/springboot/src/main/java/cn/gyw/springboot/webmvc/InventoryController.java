package cn.gyw.springboot.webmvc;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @RequestMapping("info")
    public String info() {
        return "inventory";
    }
}
