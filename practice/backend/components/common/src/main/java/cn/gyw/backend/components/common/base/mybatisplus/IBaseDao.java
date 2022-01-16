package cn.gyw.backend.components.common.base.mybatisplus;

import cn.gyw.backend.components.common.log.entity.ApiLog;

/**
 * 标准 Dao
 */
public interface IBaseDao {

   int selectCount();
   
   boolean insert(String table, ApiLog apiLog);
   
   boolean update(String table, ApiLog apiLog);
}
