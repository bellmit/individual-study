package cn.gyw.spring.lifecycle;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

public class Product implements InitializingBean, DisposableBean {

	private String name = "1010";
	
	public Product() {
		System.out.println("Call Product constructor");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		System.out.println("Call Product setName() method");
		this.name = name;
	}
	
	public void customInit() {
		System.out.println("Product customInit()");
	}
	
	public void customeDestroy() {
		System.out.println("Product customeDestroy()");
	}
	
	@PostConstruct
	public void postConstruct() {
		System.out.println("@PostConstruct method()");
	}
	
	@PreDestroy
	public void preDestroy() {
		System.out.println("@PreDestroy method()");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("InitializingBean afterPropertiesSet()");
		
	}

	@Override
	public void destroy() throws Exception {
		System.out.println("DisposableBean destroy()");
	}
}
