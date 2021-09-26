package cn.gyw.platform.gorm;

import cn.gyw.platform.gorm.entity.Person;

import javax.sql.DataSource;
import java.util.List;

public class PersonDao extends BaseDaoSupport<Person, Integer> {

    @Override
    public void setDataSource(DataSource dataSource) {
        super.setDataSourceReadOnly(dataSource);
        super.setDataSourceWrite(dataSource);
    }

    public List<Person> selectAll() {
        QueryRule queryRule = QueryRule.getInstance();
        queryRule.andEqual("", "");
        return super.select(queryRule);
    }

    @Override
    protected String getPKColumn() {
        return "id";
    }
}
