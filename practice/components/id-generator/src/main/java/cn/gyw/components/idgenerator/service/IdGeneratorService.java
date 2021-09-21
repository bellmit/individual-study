package cn.gyw.components.idgenerator.service;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import cn.gyw.components.idgenerator.dto.IdStrategyDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gyw.components.idgenerator.dao.IdStrategyDao;
import cn.gyw.components.idgenerator.entity.IdStrategy;

@Service
public class IdGeneratorService {

    private static final Logger LOG = LoggerFactory.getLogger(IdGeneratorService.class);

    private IdStrategyDao idStrategyDao;
    
    private final Lock lock = new ReentrantLock();

    /**
     * 每次只允许一个线程获取，可能会造成爆发阻塞
     * 
     * 解决方案：客户端使用双buff 模式
     * @param bizTag 业务类型
     * @return
     */
    public IdStrategyDto queryAndUpdate(String bizTag) {
        LOG.info("current query business is :{}", bizTag);
        IdStrategyDto idDto = new IdStrategyDto();
        try {
            lock.lock();
            IdStrategy origin = this.idStrategyDao.findByBizTag(bizTag);
            BeanUtils.copyProperties(origin, idDto);
            origin.setMaxId(origin.getMaxId() + origin.getStep() + 1);
            this.idStrategyDao.save(origin);
        } finally {
            lock.unlock();
        }
        return idDto;
    }

    @Autowired
    public void setIdStrategyDao(IdStrategyDao idStrategyDao) {
        this.idStrategyDao = idStrategyDao;
    }
}
