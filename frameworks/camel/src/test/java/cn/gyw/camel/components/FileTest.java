package cn.gyw.camel.components;

import java.util.concurrent.CountDownLatch;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.json.simple.JsonObject;
import org.apache.camel.model.dataformat.JsonLibrary;

public class FileTest {

	/**
	 * file:directoryName[?options]
	 * 
	 * options
	 * 
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		CamelContext context = new DefaultCamelContext();
		CountDownLatch latch = new CountDownLatch(1);

		// 添加路由
		context.addRoutes(new RouteBuilder() {

			@Override
			public void configure() throws Exception {
				// 异常处理
				errorHandler(defaultErrorHandler().maximumRedeliveries(0));

				// 文件读取
				from("file:D:\\Workspaces\\space-my\\GLearn\\framework-camel\\src\\test\\resources\\orders?noop=true")
						.routeId("main").log("parent:${file:parent}, Incoming file :${file:onlyname}").unmarshal()
						.json(JsonLibrary.Jackson, JsonObject.class).process(new Processor() {
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

		context.start();
		latch.await();
		context.stop();
	}

	private static void printExchange(final Exchange exchange) {
		System.out.println("-------------------------------------------------------");
		System.out.println("properites :" + exchange.getProperties());
		System.out.println("in :" + exchange.getIn());
		System.out.println("body :" + exchange.getMessage().getBody());
		System.out.println("out :" + exchange.getOut());
		System.out.println("-------------------------------------------------------");
	}
}
