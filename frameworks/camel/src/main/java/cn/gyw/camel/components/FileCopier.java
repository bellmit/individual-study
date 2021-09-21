package cn.gyw.camel.components;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

/**
 * demo
 *
 * 文件拷贝
 */
public class FileCopier {

    public static void main(String[] args) throws Exception {

        CamelContext context = new DefaultCamelContext();

        // add our route to the camelContext
        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {

                /**
                 * file: 表示使用 file component
                 * from 源文件
                 * to 目标文件
                 */
                from("file:D:\\Temp\\inbox").to("file:D:\\Temp\\outbox");
            }
        });

        context.start();

        Thread.sleep(1000);

        context.stop();
    }

}
