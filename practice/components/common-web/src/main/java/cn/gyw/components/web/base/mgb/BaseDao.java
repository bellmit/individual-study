package cn.gyw.components.web.base.mgb;

import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.ExampleMapper;

public interface BaseDao<T> extends BaseMapper<T>, ExampleMapper<T> {

}
