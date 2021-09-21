package cn.gyw.camel.service;

import org.apache.camel.Exchange;
import org.springframework.stereotype.Service;

@Service
public class ExceptionHandler {
	
	public void handleException(Exchange exchange, Exception e) {
		System.out.println("+++++++++++++++++");
		e.printStackTrace();
		System.out.println("-----------------");
	}

}
