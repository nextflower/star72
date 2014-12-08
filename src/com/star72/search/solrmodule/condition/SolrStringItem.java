package com.star72.search.solrmodule.condition;

/**
 * 字符串查询item：例如  *:*
 * 
 * @author wz
 *
 */
public class SolrStringItem extends AbstractSolrItem {
	
	private String queryString;
	
	public SolrStringItem(String queryString) {
		this.queryString = queryString;
	}
	
	public String getQueryString() {
		return this.queryString;
	}

}
