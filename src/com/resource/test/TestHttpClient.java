package com.resource.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.alibaba.fastjson.JSONObject;
import com.resource.bean.BaseResult;
import com.resource.config.CommonConfig;
import com.resource.httpclient.HttpRequester;
import com.resource.httpclient.HttpRespons;

/**
 */
public class TestHttpClient {

	public static void main(String[] args) throws Exception {
		test();
	}
	
	public static void test(){
		String url = CommonConfig.yuepai_server_mina_http_host_and_port + CommonConfig.yuepai_server_mina_http_context;
		System.out.println("url = " + url);
		Map<String,Object> map = new HashMap<>();
		map.put("method", "verifyTryst");
		map.put("guid", 18);
		map.put("themeStr", "THEMESTR");
		map.put("themeNum", "THEMTNUM");
		map.put("startTime", "1446020581");
		map.put("endTime", "1446025581");
		map.put("trystType", "1");
		map.put("moneyCount", "25");
		HttpRequester request = new HttpRequester();
		try {
			HttpRespons respons = request.send(url, "POST", map, null);
//			System.out.println("message = "+respons.getMessage());
//			System.out.println("content:" + respons.getContent());
//			System.out.println("-----------------------------");
//			Vector<String> contentCollection = respons.getContentCollection();
//			for (String string : contentCollection) {
//				System.out.println(string);
//			}
			String content = respons.getContent();
			BaseResult<?> baseResult = JSONObject.parseObject(content, BaseResult.class);
			System.out.println(baseResult.getMessage());
			System.out.println(baseResult.getStatusCode());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
}
