package cn.gyw.gbatis.mapper;

import java.util.List;

import cn.gyw.gbatis.annotations.Mapper;
import cn.gyw.gbatis.annotations.Select;
import cn.gyw.gbatis.model.Person;

@Mapper
public interface PersonMapper {

    List<Person> queryAll();

    /**
     * 根据 id 查询
     * @param id
     * @return
     */
    @Select(value = "select * from t_person where id=?")
    Person queryById(Integer id);
}
