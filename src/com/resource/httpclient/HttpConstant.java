package com.resource.httpclient;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * @author FXW
 * 2015年11月3日
 */
public class HttpConstant {

	public final static String defaultEncoding = "GB2312";
	public final static CharsetDecoder decoder = Charset.forName(defaultEncoding).newDecoder();;
	
}
