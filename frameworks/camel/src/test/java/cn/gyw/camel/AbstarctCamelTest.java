package cn.gyw.camel;

import java.util.concurrent.CountDownLatch;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultCamelContext;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

public abstract class AbstarctCamelTest {

	protected static CamelContext camelContext;
	protected static CountDownLatch latch;
	
	@After
	public void start() {
		try {
			camelContext.start();
		} catch (Exception e) {
			throw new RuntimeException("Can not run camel");
		}
	}
	
	protected static void printExchange(final Exchange exchange) {
		System.out.println("-------------------------------------------------------");
		System.out.println("properites :" + exchange.getProperties());
		System.out.println("in :" + exchange.getIn());
		System.out.println("body :" + exchange.getMessage().getBody());
		System.out.println("out :" + exchange.getOut());
		System.out.println("-------------------------------------------------------");
	}
	
	@BeforeClass
	public static void init() {
		camelContext = new DefaultCamelContext();
		latch = new CountDownLatch(1);
	}
	
	@AfterClass
	public static void release() {
		try {
			System.out.println("latch count :" + latch.getCount());
			latch.await();
			camelContext.stop();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
