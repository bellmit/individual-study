package cn.gyw.components.web.model;

import cn.gyw.components.web.IRespCode;
import cn.gyw.components.web.enums.CommonRespEnum;

/**
 * 通用返回对象
 * @param <T>
 */
public class DataResponse<T> extends BaseResponse {
    private static final long serialVersionUID = 1L;
    
    T data;

    public static <T> DataResponse<T> success(T data) {
        DataResponse<T> result = new DataResponse<>();
        result.setCode(CommonRespEnum.SUCCESS.getCode());
        result.setMessage(CommonRespEnum.SUCCESS.getMessage());
        result.setData(data);
        return result;
    }

    public static <T> DataResponse<T> error(IRespCode respCode, T data) {
        DataResponse<T> result = new DataResponse<>();
        result.setCode(respCode.getCode());
        result.setMessage(respCode.getMessage());
        result.setData(data);
        return result;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
