package cn.gyw.backend.components.common;

/**
 * 通用返回接口
 */
public interface IRespCode {

    String KEY_RESPONSE_CODE = "code";

    int getCode();

    String getMessage();
}
