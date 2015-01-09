package com.star72.common.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * 用于字符串相关操作的工具类
 * 
 * @author larry
 * @since 2014-12-01
 */
public class StarStringUtils {
	
	/**
	 * 禁止实例化
	 */
	private StarStringUtils() {}
	
	/**
	 * 删除所有的HTML标签,包括转义字符
	 * 
	 * @param source 需要进行除HTML的文本
	 * @return
	 */
	public static String deleteAllHTMLTag(String source) {
		String s = deleteHTMLTag(source);
		s = s.replaceAll("&.{2,6}?;", "");
		return s;
	}
	
	/**
	 * 仅删除普通HTML标签
	 * 
	 * @param source 需要进行除HTML的文本
	 * @return
	 */
	public static String deleteHTMLTag(String source) {
		if(source == null) {
			return "";
		}
		String s = source;
		/** 删除普通标签  */
		s = s.replaceAll("//|\\|:|/*|\"|<|>||", "");
		return s;
	}
	
	/**
	 * 将字符串分解成为单个的字符串列表：
	 * 例如：abc==>a,b,c
	 * @param source
	 * @return
	 */
	public static List<String> parseStr2SingleStrList(String source) {
		if(source == null) {
			return new ArrayList<String>();
		}
		List<String> result = new ArrayList<String>();
		char[] charArray = source.toCharArray();
		for(char ch : charArray) {
			result.add(new String(new char[]{ch}));
		}
		return result;
	}
	
	/**
	 * 根据长度截取字符串
	 * @param source
	 * @param length
	 * @return
	 */
	public static List<String> partedStringByLength(String source, int length) {
		if(source == null) {
			return new ArrayList<String>();
		}
		List<String> result = new ArrayList<String>();
		
		do {
			if(source.length() < length) {
				result.add(source);
			} else {
				result.add(source.substring(0, length));
				source = source.substring(length);
			}
		} while (source.length() > length);
		
		return result;
	}
	
	
	/**
	 * 删除标点符号:    
	 *    !！？%*）%！,，。!.*
	 * @param str
	 * @return
	 */
	public static String deleteBiaodian(String str) {
       return str.replaceAll("[\\pP\\p{Punct}]", ""); 
	}

}
