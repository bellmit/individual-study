package cn.gyw.backend.resource.config;

import cn.gyw.backend.resource.houseinfo.ext.HouseInfoCsvReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@Configurable
public class ScheduleConfig {

    private static final Logger log = LoggerFactory.getLogger(ScheduleConfig.class);

    private HouseInfoCsvReader houseInfoCsvReader;

    /**
     * 读取本地CSV数据并入库
     */
    @Scheduled(cron = "0 0 8 * * ?")
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
