package cn.gyw.backend.components.common.base.mgb;

import java.util.List;
import java.util.Map;

public interface IBaseService<T> {

	int count(T condition);

	List<T> queryAll();

	List<T> query(T condition);
	
	List<T> query(T condition, Integer pageNum, Integer pageSize);

	List<T> query(Map<String, Object> params, Integer pageNum, Integer pageSize);

	int remove(Object key);

	int save(T record);

	int update(T record);

	T selectOne(T record);
}
