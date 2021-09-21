package cn.gyw.camel.route;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

/**
 * Rest API
 */
@Component
public class RouteRest extends RouteBuilder {
	@Override
	public void configure() throws Exception {
		
		onCompletion().bean("logService", "writeJournal");
		
		onException(Exception.class).bean("exceptionHandler", "handleException");
		
		errorHandler(defaultErrorHandler()
				.maximumRedeliveries(5)
				.backOffMultiplier(2)
				.retryAttemptedLogLevel(LoggingLevel.WARN));

		restConfiguration().component("servlet").bindingMode(RestBindingMode.json)
				.dataFormatProperty("prettyPrint", "true").apiContextPath("/api-doc")
				.apiProperty("api.title", "User API").apiProperty("api.version", "1.0.0").apiProperty("cors", "true");

		rest("api").id("rest-interfaces").description("rest interface for transaction processing")
				.consumes("application/json")
				.produces("application/json")
				.get("/")
				.route()
				.process(new Processor() {
					@Override
					public void process(Exchange exchange) throws Exception {
						System.out.println("request rest api ....");
					}
				})
				.to("direct:netty-test").process(new Processor() {
					@Override
					public void process(Exchange exchange) throws Exception {
						// do nothing
					}
				})
				.to("direct:hello").process(new Processor() {
					@Override
					public void process(Exchange exchange) throws Exception {
						exchange.getIn().setBody("hello, this is camel!");
					}
				})
				.to("direct:exception").process(new Processor() {
					@Override
					public void process(Exchange exchange) throws Exception {
						
					}
				})
				.endRest();
	}
}
