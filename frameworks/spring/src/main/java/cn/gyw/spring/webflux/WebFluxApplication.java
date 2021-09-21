package cn.gyw.spring.webflux;

import java.io.IOException;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;

import reactor.netty.http.server.HttpServer;

public class WebFluxApplication {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
				WebFluxConfig.class);
		// 通过ApplicationContext创建HttpHandler
		HttpHandler httpHandler = WebHttpHandlerBuilder.applicationContext(applicationContext).build();
		ReactorHttpHandlerAdapter httpHandlerAdapter = new ReactorHttpHandlerAdapter(httpHandler);
		HttpServer.create().host("localhost").port(8080)
			.handle(httpHandlerAdapter).bind().block();
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
