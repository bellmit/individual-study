package cn.gyw.camel.components;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mybatis.MyBatisComponent;
import org.junit.Test;

import cn.gyw.camel.AbstarctCamelTest;

public class MybatisInCamelTest extends AbstarctCamelTest {

	@Test
	public void connectDB() throws Exception {
		MyBatisComponent mybatisComp = MybatisCom.build();
		
		camelContext.addComponent("mybatis", mybatisComp);
		
		camelContext.addRoutes(new RouteBuilder() {

			@Override
			public void configure() throws Exception {
				from("file:D:\\Workspaces\\space-my\\GLearn\\framework-camel\\src\\test\\resources\\orders?noop=true")
				.routeId("mybatis-test")
				.process(new Processor() {
					@Override
					public void process(Exchange exchange) throws Exception {
						Message out = exchange.getOut();
						Map<String, Object> datas = new HashMap<>();
						datas.put("name", "aa");
						datas.put("content", "bb");
						out.setBody(datas);
					}
				})
				.to("mybatis:cn.gyw.camel.BlogMapper.insertJournal?statementType=insert")
				.process(new Processor() {
					@Override
					public void process(Exchange exchange) throws Exception {
						printExchange(exchange);
					}
				})
				.process(new Processor() {
					@Override
					public void process(Exchange exchange) throws Exception {
						latch.countDown();
					}
				});
			}
		});
		
	}
	
}
