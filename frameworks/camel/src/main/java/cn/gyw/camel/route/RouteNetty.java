package cn.gyw.camel.route;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import groovy.json.JsonOutput;

@Component
public class RouteNetty extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("direct:netty-test").routeId("do-netty-test")
		.process(new Processor() {
			
			@Override
			public void process(Exchange exchange) throws Exception {
				Map<String, String> data = new HashMap<>();
				data.put("header", "wre");
				String jsonStr = JsonOutput.toJson(data);
				Message message = exchange.getIn();
				message.setBody(1024);
			}
		})
		.to("netty4:tcp://0.0.0.0:6020")
		.process(new Processor() {
			
			@Override
			public void process(Exchange exchange) throws Exception {
				System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
				System.out.println(exchange);
				System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
			}
		});
		
	}

}
