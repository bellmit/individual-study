package cn.gyw.components.web.utils;

import java.util.List;

import com.github.pagehelper.PageInfo;

import cn.gyw.components.web.model.PageData;

public class PageHelperUtil {

    /**
     * 将PageHelper分页后的Page转为分页信息
     */
    public static <T> PageData<T> resetPage(List<T> dataList) {
    	PageInfo<T> pageInfo = new PageInfo<>(dataList);
        PageData<T> result = new PageData<>();
        result.setPageNum(pageInfo.getPageNum());
        result.setPageSize(pageInfo.getPageSize());
        result.setTotalPage(pageInfo.getPages());
        result.setTotal(pageInfo.getTotal());
        result.setRecords(pageInfo.getList());
        return result;
    }

    private PageHelperUtil() {
    }
}
