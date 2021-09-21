package cn.gyw.platform.crawler;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import cn.gyw.platform.crawler.interfaces.IWebCrawler;
import cn.gyw.platform.crawler.service.JsoupCrawler;

/**
 * web crawler
 */
@SpringBootApplication
public class WebCrawlerApplication {

	public static void main(String[] args) {
		IWebCrawler crawler = new JsoupCrawler();
		crawler.fetchWeb();
		
		System.out.println("fetch web knowledge success...");
	}
}
