package cn.gyw.components.web.utils;

import cn.gyw.components.web.model.PageData;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * Mybatis plus 工具类
 */
public class MBPUtil {

    /**
     * 将MybatisPlus分页后的IPage转为分页信息
     */
    public static <T> PageData<T> resetPage(IPage<T> page) {
        PageData<T> result = new PageData<>();
        result.setPageNum((int) page.getCurrent());
        result.setPageSize((int) page.getSize());
        result.setTotalPage((int) page.getPages());
        result.setTotal(page.getTotal());
        result.setRecords(page.getRecords());
        return result;
    }
}
