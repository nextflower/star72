package com.star72.search.solrmodule.condition;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.util.ClientUtils;

/**
 * 查询细分的条目,可以多个拼接
 * 
 * @author wz
 *
 */
public class SolrCommonItem extends AbstractSolrItem {
	
	private final int fullSentenceBoost = 10;
	private String field;//字段
	private String value;//取值
	private Integer boost;//权重,默认为1
	private boolean forbidAnalysis = false;//是否禁止分词
	private List<SolrCommonItem> childItemList;//子级查询条目

	/**
	 * 构造器
	 * @param field 要查询的字段
	 * @param value 要查询的字段值
	 */
	public SolrCommonItem(String field, String value) {
		this(field, value, 1, false);
	}
	
	/**
	 * 构造器
	 * @param field 要查询的字段
	 * @param value 要查询的字段值
	 * @param forbidAnalysis 是否强制分词
	 */
	public SolrCommonItem(String field, String value, boolean forbidAnalysis) {
		this(field, value, 1, forbidAnalysis);
	}
	
	public SolrCommonItem(String field, String value, Integer boost, boolean forbidAnalysis, String[] excludeChar) {
		this.field = field;
		setValue(value);
		this.boost = boost;
		this.forbidAnalysis = forbidAnalysis;
		this.childItemList = new ArrayList<SolrCommonItem>();
	}
	
	/**
	 * 构造器
	 * @param field 要查询的字段
	 * @param value 要查询的字段值
	 * @param boost 权重
	 * @param forbidAnalysis 是否强制分词
	 */
	public SolrCommonItem(String field, String value, Integer boost, boolean forbidAnalysis) {
		this.field = field;
		setValue(value);
		this.boost = boost;
		this.forbidAnalysis = forbidAnalysis;
		this.childItemList = new ArrayList<SolrCommonItem>();
	}

	/**
	 * 生成当前item对象对应的查询语句
	 */
	public String getQueryString() {
		
		if(StringUtils.isBlank(field) || StringUtils.isBlank(value)) {
			return "*:*";
		}
		
		StringBuffer buf = new StringBuffer();
		buf.append("(");
		
		buf.append(getField() + ":(" + getValue());
		
		//子级查询条件拼接
		for(SolrCommonItem item : childItemList) {
			buf.append(" " + item.getRelation().name() + " " + item.getValue());
		}
		
		buf.append(")) ");
		
		//同级查询条件拼接
		buf.append(getSiblingItemQueryString());
		
		String result = buf.toString().replaceAll(" +", " ");
		
		if(siblingItemList.size() > 0) {
			result = "(" + result + ")";
		}
		
//		System.out.println(result);
		return result;
		
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getValue() {
		//中国 OR "中国"^10
		String keyword = null;
		if(this.forbidAnalysis) {
			keyword = "\"" + this.value + "\"";
		} else {
			keyword = this.value;
		}
		if(this.boost != 1) {
			keyword = keyword + "^" + this.boost;
		} 
		return keyword + " " + SolrItem.Relation.OR.name() + " \"" + this.value + "\"" + "^" + this.fullSentenceBoost;
	}

	public void setValue(String value) {
		this.value = value;
//		this.value = ClientUtils.escapeQueryChars(value);//过滤特殊字符：+ - && || ! ( ) { } [ ] ^ ~ * ? :--->替换为\\
	}

	public Integer getBoost() {
		return boost;
	}

	public void setBoost(Integer boost) {
		this.boost = boost;
	}

	public List<SolrCommonItem> getChildItemList() {
		return childItemList;
	}

	public void setChildItemList(List<SolrCommonItem> childItemList) {
		this.childItemList = childItemList;
	}
	
	/**
	 * 添加一个子查询item,即增加一个和当前查询相同field的item
	 * @param value 值
	 * @param relation 关联关系
	 * @param boost 权重
	 * @return
	 */
	public SolrCommonItem addChildItem(String value, SolrItem.Relation relation, Integer boost, boolean forbidAnalysis) {
		SolrCommonItem item = new SolrCommonItem(this.field, value, boost, forbidAnalysis);
		item.setRelation(relation);
		this.childItemList.add(item);
		return this;
	}

	public void setForbidAnalysis(boolean forbidAnalysis) {
		this.forbidAnalysis = forbidAnalysis;
	}

	public boolean getForbidAnalysis() {
		return forbidAnalysis;
	}
	
}
