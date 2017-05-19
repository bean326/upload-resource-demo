package com.resource.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class InitConfig {

	public static void init(){
		initConfig();
		System.out.println("------加载properties文件完成--------");
	}
	/**
	 * 用户支付配置信息
	 */
	private static void initConfig(){
		String fileName = "config.properties";
		Properties properties = new Properties();
		InputStream input =  null;
		try {
			input = InitConfig.class.getClassLoader().getResourceAsStream(fileName);
			properties.load(input);
			final String applicationProperty = properties.get("applicationProperty").toString().trim();
			final String property = applicationProperty + "_";
			CommonConfig.yuepai_server_mina_http_host_and_port = properties.get(property + "yuepai_server_mina_http_host_and_port").toString().trim();
			CommonConfig.yuepai_server_mina_http_encoding = properties.get("yuepai_server_mina_http_encoding").toString().trim();
			CommonConfig.yuepai_server_mina_http_context = properties.get("yuepai_server_mina_http_context").toString().trim();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(input != null ){
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
}
