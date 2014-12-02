package com.star72.common.utils;

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

}
