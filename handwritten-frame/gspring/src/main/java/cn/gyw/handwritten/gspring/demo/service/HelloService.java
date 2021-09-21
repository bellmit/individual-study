package cn.gyw.handwritten.gspring.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.gyw.handwritten.gspring.annotation.GService;

@GService
public class HelloService implements IHelloService {

	private static final Logger LOG = LoggerFactory.getLogger(HelloService.class);
	
    @Override
    public String sayHello(String name) {
    	LOG.info("run helloService sayHello method");
        return "hello, " + name;
    }

	@Override
	public void mockException() {
		LOG.info("mock exception");
		throw new RuntimeException("This is manual exception");
	}
}
