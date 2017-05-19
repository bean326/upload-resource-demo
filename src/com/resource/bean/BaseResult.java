package com.resource.bean;


import java.io.Serializable;

import com.resource.constant.ResultValueEnum;


/**
 * 
 * @author Administrator
 *
 * @param <T>
 */
public class BaseResult<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	private int statusCode = 500;
	private String message;
	private T data;
	
	
	
	/**
	 * 
	 */
	public BaseResult() {
	}
	/**
	 * 创建成功结果实例
	 * @return 成功结果实例
	 */
	public static <T> BaseResult<T> newInstance() {
		return new BaseResult<T>(ResultValueEnum.SYS_OK);
	}
	/**
	 * 创建结果实例
	 * @param resultValue 枚举值
	 * @return BaseResult<T>
	 */
	public static <T> BaseResult<T> valueOf(ResultValueEnum resultValue) {
		return new BaseResult<T>(resultValue);
	}
	private BaseResult(ResultValueEnum resultValue) {
		super();
		this.statusCode = resultValue.getKey();
		this.message = resultValue.getMsg();
	}

	public BaseResult<T> changeStatus(ResultValueEnum resultValue) {
		this.statusCode = resultValue.getKey();
		this.message = resultValue.getMsg();
		return this;
	}
	
	public int getStatusCode() {
		return statusCode;
	}
	
	public String getMessage() {
		return message;
	}

	public void addMessage(String msg) {
		if(null != msg && !"".equals(msg)){
			this.message  += " - " + msg;	
		}
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
	
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * 是否成功
	 * @return 成功返回true，失败返回false
	 */
	public boolean isSucceed(){
		return this.getStatusCode() == ResultValueEnum.SYS_OK.getKey();
	}
	
	
}
