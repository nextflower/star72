package com.star72.search.solrmodule.condition;

/**
 * 查询条件接口
 * 
 * @author wz
 *
 */
public interface SolrItem {
	
	/*
	 * 关系类型：AND\OR\NOT
	 */
	public static enum Relation{
		AND, OR, NOT;
		
		/*
		public String getTransfer() {
			switch (this) {
				case AND:
					return "+";
				case OR:
					return "||";
				case NOT:
					return "-";
				default:
					return "";
			}
		}
		*/
	}

	/*
	 * 生成item查询语句
	 */
	String getQueryString();
	
	//添加同级别查询条件
	public void addSiblingItem(SolrItem item, SolrItem.Relation relation);
	
}
