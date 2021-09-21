package cn.gyw.spring.db;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.gyw.spring.db.dao.MemberDao;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DBAutoRouteConfig.class})
public class DataSourceTest {

    @Autowired
    private MemberDao memberDao;

    /**
     * 数据源自动路由
     * 分库分表
     */
    @Test
    public void dataBaseAutoRoute() {
        this.memberDao.insert(null);
    }
}
