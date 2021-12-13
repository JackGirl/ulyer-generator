package cn.ulyer.generator.util;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


/**
 * @description : 统一返回类
 * @author      :    ulyer
 * @date        : 17:33 2019/9/15
 */
@Getter
@Setter
public class Result<T> implements Serializable {

	private static final long serialVersionUID = -6776276512636418223L;

	private Integer code;

	private String message;

	private T data;


	private Result(Integer code, String message){
		this.code = code;
		this.message = message;
	}



	public static Result getInstance(boolean success){
		return success?Result.ok():Result.fail(ResultCode.FAILURE);
	}

	public Result data(T data) {
		this.data = data;
		return this;
	}

	public Result message(String message){
		this.message = message;
		return this;
	}


	public static Result ok(String message){
		return new Result(ResultCode.SUCCESS.getCode(),message);
	}

	public static Result ok(){
		return ok(ResultCode.SUCCESS);
	}


	public static Result ok(ResultCode ResultCode) {
		return new Result( ResultCode.getCode(), ResultCode.getMessage());
	}

	public static Result fail(String message) {
		return new Result(ResultCode.FAILURE.getCode(), message);
	}

	public static Result fail(ResultCode ResultCode) {
		return new Result(ResultCode.getCode(), ResultCode.getMessage());
	}



}
