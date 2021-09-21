package cn.gyw.springboot.webmvc;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * nginx 学习使用
 * 
 * 1.负载均衡 port:8081,8082
 * 2.故障转移
 * 3.服务雪崩
 * 4.熔断机制
 * 5.CDN
 * 
 * 参考手册：guide-manual/nginx
 *
 */
@RestController
public class NginxLearnController {
	
	@Value("${server.port}")
	private String serverPort;

	@GetMapping("/time")
	public String time(@RequestParam("timeout") Integer timeout) {
		try {
			TimeUnit.SECONDS.sleep(timeout);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "hello, this is server port:" + serverPort;
	}
}
