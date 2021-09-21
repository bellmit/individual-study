package cn.gyw.spring.db.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spirng 事务操作类
 */
@Service
public class TxOperations {
	
	@Autowired
	private TxPhoneService txPhoneService;

    @Transactional(propagation = Propagation.REQUIRED) // 需要事务，若无，新建；
    // @Transactional(propagation = Propagation.SUPPORTS) // 支持事务，若无，以非事务方式运行；
    // @Transactional(propagation = Propagation.MANDATORY) // 必须事务，若无，异常；
    // @Transactional(propagation = Propagation.REQUIRES_NEW) // 新建事务，若有，挂起；
    // @Transactional(propagation = Propagation.NO_SUPPORTED) // 不支持事务，若有，挂起；
    // @Transactional(propagation = Propagation.NEVER) // 不支持事务，若有，异常；
    // @Transactional(propagation = Propagation.NESTED) // 嵌入子事务；
    public void selectList() {
    	txPhoneService.selectList();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void insertAndQuery() {
        try {
        	txPhoneService.selectList();
        } catch (Exception e) {
            // 捕获嵌入式异常，主事务完成，嵌入式事务失败
        }
    }
}
