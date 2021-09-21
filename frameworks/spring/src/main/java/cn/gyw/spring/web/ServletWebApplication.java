package cn.gyw.spring.web;

import org.apache.catalina.startup.Tomcat;

public class ServletWebApplication {

	public static void main(String[] args) throws Exception {
		Tomcat tomcat = new Tomcat();
		tomcat.setPort(8080);
		tomcat.addWebapp("/", "D:/Temp");
		tomcat.start();
		tomcat.getServer().await();
	}
}
