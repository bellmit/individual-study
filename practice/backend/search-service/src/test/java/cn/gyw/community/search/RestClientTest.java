package cn.gyw.backend.search;

import cn.gyw.backend.search.domain.EsBlog;
import cn.gyw.backend.search.util.RestClientUtil;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.ShardSearchFailure;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.GetSourceRequest;
import org.elasticsearch.client.core.GetSourceResponse;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * SpringBoot + RestClient
 *
 * 实现高级搜索
 */
public class RestClientTest {

    private String indexName;

    private RestHighLevelClient client;

    private List<EsBlog> mockData;

    @Before
    public void init() {
        this.indexName = "blog";
        this.client = RestClientUtil.makeConnection();
        this.mockData = new ArrayList<>(3);
        mockData.add(new EsBlog("登鹳雀楼", "王之涣的登鹳雀楼", "白日依山尽，黄河入海流。欲穷千里目，更上一层楼。"));
    }

    /**
     * 创建或更新index
     * @throws JsonProcessingException 
     */
    @Test
    public void createIndex() throws JsonProcessingException {
        IndexRequest indexRequest = new IndexRequest(this.indexName);
        indexRequest.id("1");
        String jsonString = new ObjectMapper().writeValueAsString(mockData.get(0));
        indexRequest.source(jsonString, XContentType.JSON);

        IndexResponse indexResponse = null;
        try {
            indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String index = indexResponse.getIndex();
        String id = indexResponse.getId();
        if (indexResponse.getResult() == DocWriteResponse.Result.CREATED) {
            System.out.println("the case where the document was created for the first time");
        } else if (indexResponse.getResult() == DocWriteResponse.Result.UPDATED) {
            System.out.println("the case where the document was rewritten as it was already existing");
        }
        ReplicationResponse.ShardInfo shardInfo = indexResponse.getShardInfo();
        if (shardInfo.getTotal() != shardInfo.getSuccessful()) {
            System.out.println("Handle the situation where number of successful shards is less than total shards");
        }
        if (shardInfo.getFailed() > 0) {
            for (ReplicationResponse.ShardInfo.Failure failure :
                    shardInfo.getFailures()) {
                String reason = failure.reason();
                System.out.println("Handle the potential failures");
            }
        }
    }

    /**
     * 获取索引
     */
    @Test
    public void getIndex() {
        GetRequest getRequest = new GetRequest(this.indexName, "1");
        // 配置请求参数
        // Configure source inclusion for specific fields
        String[] includes = new String[]{"message", "*Date"};
        String[] excludes = Strings.EMPTY_ARRAY;
        FetchSourceContext fetchSourceContext =
                new FetchSourceContext(true, includes, excludes);
        getRequest.fetchSourceContext(fetchSourceContext);

        // ？
        //getRequest.routing("routing");

        //getRequest.preference("preference");

        GetResponse getResponse = null;
        try {
            getResponse = client.get(getRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String index = getResponse.getIndex();
        String id = getResponse.getId();
        if (getResponse.isExists()) {
            long version = getResponse.getVersion();
            String sourceAsString = getResponse.getSourceAsString();
            System.out.println("Retrieve the document as a String");
            Map<String, Object> sourceAsMap = getResponse.getSourceAsMap();
            System.out.println("Retrieve the document as a Map<String, Object>");
            byte[] sourceAsBytes = getResponse.getSourceAsBytes();
            System.out.println("Retrieve the document as a byte[]");
        } else {
            System.out.println("document is not exists");
        }
    }

    /**
     * This API helps to get only the _source field of a document.
     */
    @Test
    public void getSourceApi() {
        GetSourceRequest getSourceRequest = new GetSourceRequest(this.indexName, "1");

        // config request
        String[] includes = Strings.EMPTY_ARRAY;
        String[] excludes = new String[]{"postDate"};
        getSourceRequest.fetchSourceContext(
                new FetchSourceContext(true, includes, excludes));

        GetSourceResponse response = null;
        try {
            response = client.getSource(getSourceRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String, Object> source = response.getSource();
    }

    /**
     * 删除索引
     */
    @Test
    public void deleteIndex() {

    }

    /**
     * 搜索 Index
     */
    @Test
    public void searchApi() {
        SearchRequest searchRequest = new SearchRequest(this.indexName);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // Add a match_all query to the SearchSourceBuilder
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchSourceBuilder.query(QueryBuilders.termQuery("user", "kimchy"));
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(5);
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        searchRequest.source(searchSourceBuilder);

        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

            RestStatus status = searchResponse.status();
            TimeValue took = searchResponse.getTook();
            Boolean terminatedEarly = searchResponse.isTerminatedEarly();
            boolean timedOut = searchResponse.isTimedOut();

            int totalShards = searchResponse.getTotalShards();
            int successfulShards = searchResponse.getSuccessfulShards();
            int failedShards = searchResponse.getFailedShards();
            for (ShardSearchFailure failure : searchResponse.getShardFailures()) {
                // failures should be handled here
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
