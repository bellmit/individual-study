package cn.gyw.spring.db;

import java.sql.Connection;
import java.sql.Statement;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.gyw.spring.db.tx.TxOperations;
import cn.gyw.spring.db.tx.TxPhoneService;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TransactionConfig.class})
public class TransactionTest {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private TxOperations txOperations;
    
    @Autowired
    private TxPhoneService txPhoneService;
    
    @Test
    public void insertPhone() {
    	this.txPhoneService.insertPhone();
    }

    @Test
    public void showJdbcTemplate() {
        System.out.println(jdbcTemplate);
    }

    @Test
    public void showDatasource() {
        System.out.println("datasource :" + dataSource);
        System.out.println(jdbcTemplate.getDataSource());
    }

    @Test
    public void jdbcTransaction() {
        try {
            // 获取连接
            Connection connection = null;
            // 通过TransactionManager 获得可以回滚的机会
            connection.setAutoCommit(false);

            Statement statement = connection.createStatement();
            statement.execute("");

            connection.commit();
            //connection.rollback();

        } catch (Exception e) {

        }
    }
}
