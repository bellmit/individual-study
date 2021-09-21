package cn.gyw.camel.components;

import org.apache.camel.CamelContext;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class HttpComponent {

    public static void main(String[] args) throws Exception {
        CamelContext context = new DefaultCamelContext();

        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {

                from("http://127.0.0.1:8080/").to("direct:getContent");

                from("direct:getContent").to("http://127.0.0.1:8082/sb/order")
                        .process((exchange -> {
                            Message message = exchange.getOut();
                            System.out.println(message.getBody());
                        }));
            }
        });

        context.start();

        Thread.sleep(30000);
    }
}
