package cn.gyw.components.web.enums;

import cn.gyw.components.web.exceptions.CommonExceptionAssert;

/**
 * 通用返回
 * 
 * 说明：
 * 1. 0 成功
 * 2. 00|000 前两位区分系统，后三位异常码值
 * 3. 通用异常固定前两位：10
 */
public enum CommonRespEnum implements CommonExceptionAssert {

	SUCCESS(0, "Success"),
	SYSTEM_ERROR(-1, "System internal error"),

	PARAM_NOT_NULL(10001, "argument not null"),
	PARAM_NULL(10002, "argument [{0}] is null"),
	SERVER_ERROR(10003, "Server internal error"),
	NO_DATA(10004, "No data"),
	TOKEN_ILLEGAL(10005, "Token illegal"),
	UN_AUTHORIZED(10006, "unauthorized"),
	ACCESS_FORBIDDEN(10007, "access forbidden"),
	;

	CommonRespEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}

	private int code;
	private String message;

	@Override
	public int getCode() {
		return code;
	}

	@Override
	public String getMessage() {
		return message;
	}

}