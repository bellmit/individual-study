package cn.gyw.components.web.log.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import cn.gyw.components.web.log.entity.ApiLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import cn.gyw.components.web.log.dao.ApiLogShardingDao;

public class ApiLogShardingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiLogShardingService.class);
    
    @Autowired
    private ApiLogShardingDao apiLogShardingDao;
    
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyMMddHHmmss");
    private static final int SEQUENCE_START = 1;
    
    // 最新的时间戳
    private String lastTimestamp = "";
    
    private int sequence = 0;
    
    @Value("${apiLog.tableName:apilog}")
    private String tableName;
    
    @Value("${apiLog.daysToKeep:390}")
    private int daysToKeep;
    
    public void save(ApiLog apiLog) {
        LOGGER.debug("save api use log:[{}]", apiLog);
        String globalSeq = generateSequenceNum();
        apiLog.setSequenceNum(globalSeq);
        
        String table = tableName + getTableNameIndex();
        apiLogShardingDao.insert(table, apiLog);
        LOGGER.info("Api logs saved in table: {}", table);
    }
    
    /**
     * 生成唯一的序列号
     * @return
     */
    private String generateSequenceNum() {
        synchronized (ApiLogShardingService.class) {
            LocalDateTime now = LocalDateTime.now();
            String timestamp = DTF.format(now);
            if (!timestamp.equals(lastTimestamp)) {
                sequence = SEQUENCE_START;
                lastTimestamp = timestamp;
            }
            
            String sequenceNum = timestamp + String.format("%03d", sequence);
            LOGGER.debug("sequence number created :[{}]", sequenceNum);
            return sequenceNum;
        }
    }
    
    /**
     * 获取表名后缀
     * @return
     */
    private int getTableNameIndex() {
        long mescOfDay = 86400000;// milliseconds in a day
        long timeZoneMSEC = 8 * 3600000;// milliseconds offset for timezone
        long onlineNumberOfPartitions = daysToKeep;// total number of journal tables (from 0 - number-1)
        long onlineInterval = 1 * mescOfDay;
        long currentday = System.currentTimeMillis();
        long currentonlineSeq = (currentday + timeZoneMSEC) / onlineInterval % onlineNumberOfPartitions;
        return (int) currentonlineSeq;
    }
}
