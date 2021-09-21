package cn.gyw.spring.db.tx;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TxPhoneService {

	private static final String TABLE_PHONE = "t_phone";
	
	@Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public void insertPhone() {
        String insertSql = "INSERT INTO " + TABLE_PHONE +" ( `name`,price ) VALUES( '"
        		+ UUID.randomUUID().toString() + "', " + 6999.9
        		+ " ) ";
        int result = jdbcTemplate.update(insertSql);
        System.out.println(result);
    }

	public void selectList() {
		String insertSql = "INSERT INTO " + TABLE_PHONE +" ( `name`,price ) VALUES( '"
        		+ UUID.randomUUID().toString() + "', " + 6999.9
        		+ " ) ";
        int result = jdbcTemplate.update(insertSql);
        System.out.println(result);
	}
}
