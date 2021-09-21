package cn.gyw.camel.route;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class RouteException extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		try {
			from("direct:exception")
			.doTry()
    			.bean("exceptionService", "doException")
    			.process(new Processor() {
    				@Override
    				public void process(Exchange exchange) throws Exception {
    					Message message = exchange.getOut();
    					message.setFault(true);
    					message.setBody("Query arguments errors.");
    				}
    			})
    		.doCatch(Exception.class)
    		    .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        System.out.println(">>> catch exception");
                    }
                })
    		.doFinally()
    		    .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        System.out.println("finally");
                    }
                })
    		.end()
			;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}

}
