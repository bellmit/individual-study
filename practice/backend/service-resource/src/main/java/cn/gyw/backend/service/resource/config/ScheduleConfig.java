package cn.gyw.backend.service.resource.config;

import cn.gyw.backend.service.resource.ext.HouseInfoCsvReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@Configuration
public class ScheduleConfig {

    private static final Logger log = LoggerFactory.getLogger(ScheduleConfig.class);

    private HouseInfoCsvReader houseInfoCsvReader;

    /**
     * 读取本地CSV数据并入库
     */
    @Scheduled(cron = "0 0 8,10,12 * * ?")
    public void readCsvAndSave() {
        log.info("Start read csv file and save to db...");
        boolean result = houseInfoCsvReader.readAndSaveDB();
        log.info("End of save to db, result :{}", result);
    }

    @Autowired
    public void setHouseInfoCsvReader(HouseInfoCsvReader houseInfoCsvReader) {
        this.houseInfoCsvReader = houseInfoCsvReader;
    }
}
