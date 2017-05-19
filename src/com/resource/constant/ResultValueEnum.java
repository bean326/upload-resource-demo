package com.resource.constant;

/**
 * 
 * @author Administrator
 *
 */
public enum ResultValueEnum implements BaseEnum<Integer>{
	/**1:系统成功*/
	SYS_OK(1001,"OK"),
	/**2:系统错误*/
	SYS_ERROR(1002,"系统错误"),
	/**3:参数错误*/
	SYS_PARAMETER_ERROR(1003,"参数错误"),
	/**6:签名错误**/
	SYS_SIGN_ERROR(1004,"参数签名错误"),
	/**7:应用程序错误*/
	SYS_APPLICATION_ERROR(1005,"应用程序错误"),
	
	//---------创建交易信息--------------
	TRADE_PARAMETER_ERROR(2001,"参数错误"),
	TRADE_CREATE_PAY_INFO_ERROR(2002,"支付错误，不能重复创建支付交易信息"),
	//----------- alipay value enum ------------------------------------------------
	ALI_PAY_NOT_STARTED(3001,"未收到通知"),
	ALI_PAY_WAITING(3002,"等待支付"),
	ALI_PAY_SUCCEED(3003,"支付成功"),
	ALI_PAY_CLOSED(3004,"支付关闭"),
	ALI_PAY_UNKNOW(3005,"未知错误"),
	ALI_PAY_QUERY_UNKNOW(3006,"没有对应的交易信息"),
	;
	
	private int key;
	private String msg;
	
	private ResultValueEnum(int key,String msg){
		this.key=key;
		this.msg=msg;
	}
	public Integer getKey() {
		return this.key;
	}

	public String getMsg() {
		return this.msg;
	}
	/**
	 * 根据错误编号查询具体错误类型
	 * @param key 错误编号
	 * @return {@link ResultValueEnum}
	 */
	public static ResultValueEnum queryByKey(Integer key){
		ResultValueEnum result=query(key);
		if(null==result){
			result=ResultValueEnum.SYS_ERROR;
		}
		return result;
	}
	private static ResultValueEnum query(Integer key){
		ResultValueEnum[] values= ResultValueEnum.values();
		for(ResultValueEnum result:values){
			if(result.getKey().intValue() == key.intValue()){
				return result;
			}
		}
		return null;
	}
	@Override
	public String toString() {
		return "[key="+this.key+",title="+this.name()+"]";
	}
	
	
}
