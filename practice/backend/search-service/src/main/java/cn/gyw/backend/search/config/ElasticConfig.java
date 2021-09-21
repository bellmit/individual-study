package cn.gyw.backend.search.config;

import java.io.IOException;

import javax.annotation.PreDestroy;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 参考：https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/#preface.versions
 */
@Configuration
public class ElasticConfig {

	private static final Logger log = LoggerFactory.getLogger(ElasticConfig.class);

	@Autowired
	private RestHighLevelClient client;

	@Bean
	public RestHighLevelClient restClient(@Value("${es.host}") String host, @Value("${es.port}") int port,
			@Value("${es.schema}") String schema) {

		RestHighLevelClient restHighLevelClient = new RestHighLevelClient(
				RestClient.builder(new HttpHost(host, port, schema)));
		return restHighLevelClient;
	}

	@PreDestroy
	public void destroy() {
		if (client != null) {
			try {
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
