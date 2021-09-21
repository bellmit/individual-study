package cn.gyw.components.web.base.mybatisplus;

import cn.gyw.components.web.log.entity.ApiLog;

/**
 * 标准 Dao
 */
public interface IBaseDao {

   int selectCount();
   
   boolean insert(String table, ApiLog apiLog);
   
   boolean update(String table, ApiLog apiLog);
}
