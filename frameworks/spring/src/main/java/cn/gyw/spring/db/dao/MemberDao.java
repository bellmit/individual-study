package cn.gyw.spring.db.dao;

import cn.gyw.spring.db.DataSourceType;
import cn.gyw.spring.db.entity.Member;
import cn.gyw.spring.db.route.DynamicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insert(Member member) {

        String sql = "insert into t_member (id,name,addr,age) values (1,'abc','beijing',23)";
        // 此处可以添加数据源切换逻辑
        DynamicDataSource dataSource = (DynamicDataSource) jdbcTemplate.getDataSource();
        dataSource.getDynamicDataSourceEntry().set(DataSourceType.SLAVER);

        jdbcTemplate.update(sql);
    }
}
