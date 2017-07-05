package edu.csu.utils.excelhandle;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

public class FilterBrowerEncode {
	
	/*
	 * 
	 * 解决下载文件时的在不同浏览器中的中文文件名乱码问题
	 */
	public String FilterBrCode(HttpServletRequest req,String myFileName) throws UnsupportedEncodingException{
		String fileName=null;
		String agent = req.getHeader("USER-AGENT");    //解析浏览器
        if (null != agent && -1 != agent.indexOf("MSIE") || null != agent  
                && -1 != agent.indexOf("Trident")) {// ie  

        	fileName = java.net.URLEncoder.encode(myFileName, "UTF8");  //以UTF8的编码方式解码
        } else  {// 火狐,chrome等  

        	fileName = new String(myFileName.getBytes("UTF-8"), "iso-8859-1");  //以UTF8的编码形式获得二进制编码格式，再以iso-8859-1对二进制编码进行解码
        }  
        return fileName;
	}

}
