package com.star72.search.solrmodule.core;

import java.util.ResourceBundle;

/**
 * 从系统配置文件solr.properties中获取属性值
 * SOLR_URL:远程Solr访问地址
 * SHOW_QUERY:是否打印查询语句
 * ...
 * @author wz
 *
 */
public class GlobalConfig {
	
	/**
	 * 配置文件的basename
	 */
	public static final String SOLR_CONFIG = "solr";
	
	/**
	 * 远程Solr的地址
	 */
	public static final String SOLR_URL = "SOLR_URL";
	
	/**
	 * 用于保存是否打印查询语句的字段
	 */
	public static final String SHOW_QUERY = "SHOW_QUERY";

	/**
	 * 是否打印查询语句
	 * @return
	 */
	public static boolean showQuery() {
		String value = getConfigValue(SHOW_QUERY);
		if(value != null) {
			return Boolean.parseBoolean(value);
		}
		return false;
	}
	
	/**
	 * 从配置文件中获取属性值
	 * @param key
	 * @return
	 */
	public static String getConfigValue(String key) {
		ResourceBundle rb = ResourceBundle.getBundle(SOLR_CONFIG);
		if(rb.containsKey(key)) {
			return rb.getString(key);
		} else {
			return null;
		}
	}
	
}
