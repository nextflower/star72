package com.star72.search.solrmodule.condition;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery.ORDER;

/**
 * 定义排序规则
 * 
 * @author wz
 *
 */
public class SortedValue implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8194385257107975137L;
	
	private Map<String, ORDER> map = new HashMap<String, ORDER>();
	
	/**
	 * 无参构造器
	 */
	public SortedValue() {
		this("score", ORDER.desc);
	}
	
	/**
	 * 构造器
	 * @param field 排序字段
	 * @param order 排序规则,使用ORDER的枚举类型
	 */
	public SortedValue(String field, ORDER order) {
		this.map.put(field, order);
	}
	
	/**
	 * 新增排序规则,可以增加多个
	 * @param field 排序字段
	 * @param order 排序规则,使用ORDER的枚举类型
	 * @return
	 */
	public SortedValue addSortedValue(String field, ORDER order) {
		this.map.put(field, order);
		return this;
	}
	
	/**
	 * 获取所有的排序规则
	 * @return
	 */
	public Map<String, ORDER> getValues() {
		return this.map;
	}

}
