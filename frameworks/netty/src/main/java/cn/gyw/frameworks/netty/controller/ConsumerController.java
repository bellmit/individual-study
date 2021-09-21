package cn.gyw.frameworks.netty.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.gyw.frameworks.netty.client.NettyClient;

@RestController
public class ConsumerController {

	@Autowired
	private NettyClient nettyClient;

	@GetMapping("/send")
	public String send(@RequestParam("message") String message) {
		nettyClient.sendMsg(message);

		return "send success";
	}
}
