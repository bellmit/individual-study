package cn.gyw.camel.service;

import org.apache.camel.Exchange;
import org.springframework.stereotype.Service;

@Service
public class LogService {

	public void writeJournal(Exchange exchange) {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println(exchange);
		System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
	}
}
