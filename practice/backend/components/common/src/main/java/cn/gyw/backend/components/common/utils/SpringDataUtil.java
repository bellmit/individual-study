package cn.gyw.backend.components.common.utils;

import org.springframework.data.domain.Page;

import cn.gyw.backend.components.common.model.PageData;

/**
 * 基于Spring data 工具类
 */
public class SpringDataUtil {

    /**
     * 将SpringData分页后的Page转为分页信息
     */
    public static <T> PageData<T> resetPage(Page<T> page) {
        PageData<T> result = new PageData<>();
        result.setPageNum(page.getNumber());
        result.setPageSize(page.getSize());
        result.setTotalPage(page.getTotalPages());
        result.setTotal(page.getTotalElements());
        result.setRecords(page.getContent());
        return result;
    }

    private SpringDataUtil() {
    }
}
