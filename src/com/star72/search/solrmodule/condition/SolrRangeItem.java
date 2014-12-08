package com.star72.search.solrmodule.condition;

/**
 * 范围检索条件item:针对数值型、日期型等
 * 	data:[200112 TO 200212]---包含范围检索,包含首尾
 * 	count:{50 TO 100}---不包含范围检索,不包含首尾
 * @author wz
 *
 */
public class SolrRangeItem extends AbstractSolrItem {
	
	private String[][] flagArray = new String[][]{new String[]{"[", "]"}, new String[]{"{","}"}};
	
	private String field;
	private String start;
	private String end;
	private Boolean include;
	
	/**
	 * 范围查询对象
	 * @param field 字段
	 * @param start 范围起始
	 * @param end 范围结束
	 * @param include true：包含该范围;false：不包含该范围
	 */
	public SolrRangeItem(String field, String start, String end, Boolean include) {
		this.field = field;
		this.start = start;
		this.end = end;
		this.include = include;
	}

	public String getQueryString() {
		String[] flag = null;
		if(include) {
			flag = flagArray[0];
		} else {
			flag = flagArray[1];
		}
		return "(" + field + ":" + flag[0] + start + " TO " + end + flag[1] + ")";
	}

}
