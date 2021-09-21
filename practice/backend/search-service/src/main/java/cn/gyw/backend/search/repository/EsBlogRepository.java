package cn.gyw.backend.search.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.gyw.backend.search.domain.EsBlog;

@Repository
public class EsBlogRepository {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EsBlogRepository.class);

    @Autowired
    private RestHighLevelClient client;

    /**
     * 分页查询（去重）
     *
     * @param title
     * @param summary
     * @param content
     * @param page
     * @param limit
     * @return
     */
    public List<EsBlog> findDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContaining(String title,
                                                                                                  String summary, String content, int page, int limit) {
    	SearchRequest searchRequest = new SearchRequest("blog");
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.termQuery("title", title));
        builder.from(page);
        builder.size(limit);
        builder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        
        SearchResponse response = null;
        try {
            response = client.search(searchRequest, RequestOptions.DEFAULT);
            TotalHits total = response.getHits().getTotalHits();
            LOGGER.debug("Total {}", total.value);
            List<EsBlog> datas = new ArrayList<>((int)total.value);
            for (SearchHit hit : response.getHits().getHits()) {
            	float score = hit.getScore();
            	LOGGER.debug("Match score is {}", score);
            	String source = hit.getSourceAsString();
            	EsBlog blog = new ObjectMapper().readValue(source, EsBlog.class);
            	datas.add(blog);
            }
            return datas;
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return Collections.emptyList();
    }
}
