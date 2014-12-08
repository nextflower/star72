package com.star72.search.solrmodule.condition;

import com.star72.search.solrmodule.core.SolrSearchConstants;


/**
 * 针对动态字段的检索:
 * @author wz
 *
 */
public class SolrDynamicItem extends SolrCommonItem {
	
	/*
	 * 动态字段前缀
	 */
	private String dynamicPrefix = null;

	public SolrDynamicItem(String field, String value) {
		this(field, value, null);
	}
	
	public SolrDynamicItem(String field, String value, String dynamicPrefix) {
		super(field, value);
		this.dynamicPrefix = dynamicPrefix == null ? SolrSearchConstants.DYNAMIC_FILED_PREFIX : dynamicPrefix;
	}
	
	@Override
	public String getField() {
		return dynamicPrefix + super.getField();
	}

}
