package cn.gyw.components.idgenerator.client;

import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * ID 生成服务
 * 
 * 双Buff 模式
 */
@Service
@ConditionalOnProperty(value = "idserver.enable", havingValue = "true", matchIfMissing = false)
public class IdGenerationService {

    private static final Logger LOG = LoggerFactory.getLogger(IdGenerationService.class);

    // 0 : read ; 1 : write;
    private AtomicLong[] idBuffer = new AtomicLong[] { new AtomicLong(0L), new AtomicLong(0L) };
    private long threshold;
    private long maxId;

    private boolean writeBufRefreshed = false;
    private long backThreshold;
    private long backMaxId;

    @Value("${idserver.api}")
    private String idServerApi;

    // 服务标识
    @Value("${idserver.biz-tag}")
    private String bizTag;

    public long getId() throws Exception {
        checkCurId();
        return idBuffer[0].getAndIncrement();
    }

    private void checkCurId() throws Exception {
        if (idBuffer[0].get() > threshold && !writeBufRefreshed) {
            doGetMaxIdFromServer();
            this.writeBufRefreshed = true;
            LOG.debug("current id buffer from {} to {}, threshold:{}", this.idBuffer[0].get(), this.maxId,
                    this.threshold);
        }
        if (idBuffer[0].get() > maxId) {
            enableBackIdBuffer();
            this.writeBufRefreshed = false;
        }
    }

    /**
     * 每次重启后，buff 里的id 就清空了，必须从idserver 里重新获取区间
     * 
     * @throws JsonProcessingException
     * @throws JsonMappingException
     */
    @PostConstruct
    public void init() throws Exception {
        doGetMaxIdFromServer();
        enableBackIdBuffer();
    }

    private void doGetMaxIdFromServer() throws Exception {
        String uri = idServerApi + "/" + bizTag;
        // {"errorCode":0,"code":20000,"message":"Success","data":{"bizTag":"user-service","maxId":10010,"step":10,"bizDesc":"yonghu"}}
        RestTemplate restTemplate = new RestTemplate();
        String resp = restTemplate.getForObject(uri, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readValue(resp, JsonNode.class);
        JsonNode code = jsonNode.get("code");
        if ("20000".equals(code.asText())) {
            JsonNode data = jsonNode.get("data");
            long curMaxId = data.get("maxId").asLong();
            int step = data.get("step").asInt();
            this.idBuffer[1].set(curMaxId);
            this.backMaxId = curMaxId + step;
            this.backThreshold = curMaxId + (step >> 1);
        } else {
            throw new RuntimeException("Can not fetch database maxId and step.");
        }
    }

    private synchronized void enableBackIdBuffer() {
        this.idBuffer[0] = this.idBuffer[1];
        this.idBuffer[1] = new AtomicLong(0);
        this.maxId = this.backMaxId;
        this.threshold = this.backThreshold;
        LOG.debug("current id buffer from {} to {}, threshold:{}", this.idBuffer[0].get(), this.maxId,
                this.threshold);
    }
}
