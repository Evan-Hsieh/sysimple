package org.bit.linc.web.base;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EncodeType{
	
	private static final String CHARACTER_TYPE="utf-8";
	
	private static final String CONTENT_TYPE="text/html";
	
	
	public static void setCharacterEncoding(HttpServletResponse res){
		res.setCharacterEncoding(CHARACTER_TYPE);
	}
	
	public static void setContentType(HttpServletResponse res){
		res.setContentType(CONTENT_TYPE);
	}
}
