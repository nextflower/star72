package com.star72.search.solrmodule.condition;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询条件适配器
 * 
 * @author wz
 *
 */
public abstract class AbstractSolrItem implements SolrItem {
	
	protected List<SolrItem> siblingItemList = new ArrayList<SolrItem>();
	
	protected SolrItem.Relation relation;
	
	public String getSiblingItemQueryString() {
		
		StringBuffer buf = new StringBuffer();
		
		//同级查询条件拼接
		for(SolrItem item : siblingItemList) {
			buf.append(" " + ((AbstractSolrItem)item).getRelation().name() + item.getQueryString());
		}
		
		return buf.toString().replaceAll(" +", " ");
	}

	public void addSiblingItem(SolrItem item, SolrItem.Relation relation){
		((AbstractSolrItem)item).setRelation(relation);
		this.siblingItemList.add(item);
	}

	public void setRelation(SolrItem.Relation relation) {
		this.relation = relation;
	}

	public SolrItem.Relation getRelation() {
		return relation;
	}
	
	public List<SolrItem> getSiblingItemList() {
		return siblingItemList;
	}

	public void setSiblingItemList(List<SolrItem> siblingItemList) {
		this.siblingItemList = siblingItemList;
	}

}
