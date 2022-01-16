package cn.gyw.backend.components.common.model;

import cn.gyw.backend.components.common.enums.CommonRespEnum;
import cn.gyw.backend.components.common.IRespCode;

import java.io.Serializable;

/**
 * 基础返回对象
 */
public class BaseResponse implements Serializable {

    private static final long serialVersionUID = 2951887768275356440L;
    
    // 结果编码
    protected int code;
    // 结果信息
    protected String message;

    public BaseResponse() {
    }

    public BaseResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }
    
    public BaseResponse(IRespCode respCode) {
        this.code = respCode.getCode();
        this.message = respCode.getMessage();
    }
    
    public static BaseResponse success() {
    	return new BaseResponse(CommonRespEnum.SUCCESS);
    }
    
    public static BaseResponse error(int code, String message) {
        return new BaseResponse(code, message);
    }

    public static BaseResponse error(IRespCode respCode) {
        return new BaseResponse(respCode);
    }
    
	public static BaseResponse error(IRespCode respCode, String message) {
		BaseResponse result = error(respCode);
		result.setMessage(message);
		return result;
	}

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
