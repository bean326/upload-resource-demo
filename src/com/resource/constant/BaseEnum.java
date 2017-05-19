package com.resource.constant;
/**
 * 枚举
 * 主要用于值和名的对应和查询
 *
 */
public interface BaseEnum<T> {
	/**
	 * 获取值
	 * @return T
	 */
	public T getKey();
	/**
	 * 获取名称
	 * @return String
	 */
	public String getMsg();
}
