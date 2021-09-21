package cn.gyw.backend.im.schedule;

import cn.gyw.backend.im.service.MaintenanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * 定时服务
 */
@Service
public class ScheduleService {

    private static final Logger LOG = LoggerFactory.getLogger(ScheduleService.class);

    @Autowired
    private MaintenanceService maintenanceService;

    /**
     * 启动更新用户登录状态线程
     */
    @Scheduled(cron = "0 0/5 * * * ?")
    public void updateUserState() {
        LOG.info("Do startUpdateUserLoginInfoServerice start ...");
        maintenanceService.updateLoginUserState();
        LOG.info("startUpdateUserLoginInfoServerice end");
    }

    /**
     * 维护用户发送消息队列线程
     */
    @Scheduled(cron = "0 0 0/1 * * ?")
    public void maintenanceRedisQueue() {
        maintenanceService.maintenanceHisMessage();
    }
}
