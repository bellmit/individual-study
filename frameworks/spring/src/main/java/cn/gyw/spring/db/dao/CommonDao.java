package cn.gyw.spring.db.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 通用sql 查询
 */
@Repository
public class CommonDao {

    private Logger log = LoggerFactory.getLogger(CommonDao.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 获取当前事务的id
     * @return
     */
    public String getCurrentTrxId() {
        String sql = "SELECT TRX_ID" +
                " FROM INFORMATION_SCHEMA.INNODB_TRX" +
                " WHERE TRX_MYSQL_THREAD_ID = CONNECTION_ID()";
        String trxId = jdbcTemplate.queryForObject(sql, String.class);
        log.info("事务ID：{}", trxId);
        return trxId;
    }

}
