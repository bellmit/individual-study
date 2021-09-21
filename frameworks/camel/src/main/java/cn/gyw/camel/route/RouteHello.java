package cn.gyw.camel.route;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import cn.gyw.camel.service.HelloService;

@Component
public class RouteHello extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("direct:hello").bean(HelloService.class, "sayHello")
		.process(new Processor() {
			@Override
			public void process(Exchange exchange) throws Exception {
				System.out.println("Exchange:" + exchange);
				exchange.getOut().setBody("6 l 6 l");
			}
		})
		;
	}

}
