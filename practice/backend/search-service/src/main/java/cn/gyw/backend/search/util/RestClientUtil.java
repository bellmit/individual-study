package cn.gyw.backend.search.util;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

public class RestClientUtil {

//    private static final String HOST = "192.168.1.181";
    private static final String HOST = "10.1.7.225";
    // Elasticsearch查询服务器使用9200端口，我们可以通过RESTful API直接查询数据库。
    private static final int PORT_ONE = 9200;
    // REST服务器使用9201端口，外部客户端可以使用它来连接和执行操作。
    private static final int PORT_TWO = 9201;
    // 通信方式
    private static final String SCHEME = "http";
    // 高级客户端实例
    private static RestHighLevelClient restHighLevelClient;
    // Jackson 的转换类
    //private static ObjectMapper objectMapper = new ObjectMapper();
    // 索引名称
    private static final String INDEX = "persondata";
    // 索引类型
    private static final String TYPE = "person";

    /**
     * 实现了单例模式
     * 不会为ES创建多个连接，从而节省大量内存，并保证线程安全
     *
     * @return RestHighLevelClient
     */
    public static synchronized RestHighLevelClient makeConnection() {

        if (restHighLevelClient == null) {
            restHighLevelClient = new RestHighLevelClient(
                    RestClient.builder(
                            new HttpHost(HOST, PORT_ONE, SCHEME)));
        }

        return restHighLevelClient;
    }

    public static synchronized void closeConnection() throws IOException {
        restHighLevelClient.close();
        restHighLevelClient = null;
    }
}
