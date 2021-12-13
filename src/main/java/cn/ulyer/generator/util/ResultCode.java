package cn.ulyer.generator.util;

import lombok.Getter;

@Getter
public enum ResultCode {

	/**
	 * 通用成功
	 */
	SUCCESS(0, "请求成功"),

	FAILURE(1,"请求失败"),

	UNAUTHORIZED(4001, "未认证"),

	AUTHORIZE_FAIL(4001, "认证失败"),

	ACCESS_DENIED(4003,"拒绝访问");



	private Integer code;

	private String message;

	ResultCode(Integer code, String message) {
		this.code = code;
		this.message = message;
	}
}
