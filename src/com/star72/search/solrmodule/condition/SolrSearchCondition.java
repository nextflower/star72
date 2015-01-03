package com.star72.search.solrmodule.condition;


import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.common.params.GroupParams;
import org.apache.solr.common.params.SolrParams;

import com.star72.search.solrmodule.core.GlobalConfig;
import com.star72.search.solrmodule.core.SolrSearchConstants;


/**
 * solr检索条件对象 
 * 
 * @author wz
 *
 */
public class SolrSearchCondition {
	
	//分页属性,可配置
	private Integer pageNo;
	private Integer pageSize;
	
	//高亮属性,可配置
	private Boolean openHighlight;//是否开启高亮
	private Boolean highlightRequireFieldMatch;//是否需要匹配字段
	private String highlightSimplePost;//高亮后缀
	private String highlightSimplePre;//高亮前缀
	private Integer highlightFragsize;//切片长度
	private Integer highlightSnippets;//切片数量
	private String[] highlightField;//高亮字段,对应solrhome下schema.xml中的字段名
	
	
	private SolrItem item;//封装了查询条件的item
	private SortedValue sortedValue;//排序规则
	
	//facet相关属性
	private Boolean useFacet;//是否开启facet查询
	private String[] facetField;//facet字段
	private Integer facetLimit;//限制Facet字段返回的结果条数
	private Integer facetMinCount;//限制了Facet字段值的最小count
	private Boolean facetMissing;//是否统计该Facet字段值为null的记录.
	private String facetSort;//排序facet
	
	//group相关属性
	private Boolean useGroup;
	private String[] groupField;//group字段
	private Integer groupLimit;//限制group字段返回的结果条数
	
	//其他可选属性
	private Boolean showDebugInfo;
	private String[] returnField;//查询返回的字段
	
	/**
	 * 构造器,仅传入查询条件,其余参数使用默认值
	 * @param item 查询条件
	 */
	public SolrSearchCondition(SolrItem item) {
		this(item, new SortedValue(), SolrSearchConstants.PAGE_NO, SolrSearchConstants.PAGE_SIZE);
	}
	
	/**
	 * 构造器,提供查询条件和排序规则
	 * @param item 查询条件
	 * @param sortedValue 排序规则
	 */
	public SolrSearchCondition(SolrItem item, SortedValue sortedValue) {
		this(item, sortedValue, SolrSearchConstants.PAGE_NO, SolrSearchConstants.PAGE_SIZE);
	}
	
	/**
	 * 构造器,提供查询条件、排序规则、分页信息
	 * @param item 查询条件
	 * @param sortedValue 排序规则,可以为null
	 * @param pageNo 页码
	 * @param pageSize 单页数量
	 */
	public SolrSearchCondition(SolrItem item, SortedValue sortedValue, Integer pageNo, Integer pageSize) {
		/*
		 * 初始化默认值,依然可以通过spring的bean-<property>进行属性配置。
		 */
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		//高亮属性的初始化
		this.openHighlight = SolrSearchConstants.HIGHLIGHT_OPEN;
		this.highlightRequireFieldMatch = SolrSearchConstants.HIGHLIGHT_REQUIREFIELDMATCH;
		this.highlightSimplePost = SolrSearchConstants.HIGHLIGHT_POST;
		this.highlightSimplePre = SolrSearchConstants.HIGHLIGHT_PRE;
		this.highlightFragsize = SolrSearchConstants.HIGHLIGHT_FRAGSIZE;
		this.highlightSnippets = SolrSearchConstants.HIGHLIGHT_SNIPPETS;
		//高亮字段不提供默认值,由用户开启高亮时设置
		//this.highlightField = new String[]{"content", "title"};
		
		this.item = item;
		this.sortedValue = sortedValue == null ? new SortedValue() : sortedValue;
		
		//facet属性的初始化
		this.useFacet = SolrSearchConstants.FACET_OPEN;
		this.facetLimit = SolrSearchConstants.FACET_LIMIT;
		this.facetMinCount = SolrSearchConstants.FACET_MIN_COUNT;
		this.facetMissing = SolrSearchConstants.FACET_MISSING;
		
		//group属性的初始化
		this.useGroup = SolrSearchConstants.GROUP_OPEN;
		this.groupLimit = SolrSearchConstants.GROUP_LIMIT;
		
		this.showDebugInfo = SolrSearchConstants.SHOW_DEBUG_INFO;
		
	}
	
	/**
	 * 生成查询语句
	 * @return
	 */
	public String getQueryString() {
		return item.getQueryString();
	}
	
	/**
	 * 生成查询对象
	 * @return
	 */
	public SolrParams getSolrParams() {
		
		//是否打印查询语句
		if(GlobalConfig.showQuery()) {
			System.out.println(getQueryString());
		}
		
		SolrQuery q = new SolrQuery(getQueryString());
		
		//设置分页
		q.setStart(pageNo * pageSize);
		q.setRows(pageSize);
		
		//facet设置
		if (this.useFacet != null && this.useFacet) {
			
			//开启facet查询
			q.setFacet(true);
			q.addFacetField(this.facetField);
			q.setFacetLimit(this.facetLimit);
			q.setFacetMinCount(this.facetMinCount);
			q.setFacetMissing(this.facetMissing);
			q.setFacetSort(this.facetSort);
			
		} if (this.useGroup != null && this.useGroup) {
			
			//开启group查询,设置相关属性
			q.setParam(GroupParams.GROUP, true);//开启分组
			q.setParam(GroupParams.GROUP_FIELD, this.groupField);//分组字段
			q.setParam(GroupParams.GROUP_LIMIT, this.groupLimit + "");//限制返回的数量 
			
		} else {
			
			//高亮设置
			if(openHighlight) {
				q.setHighlight(openHighlight);
				q.setHighlightFragsize(highlightFragsize);
				q.setHighlightRequireFieldMatch(highlightRequireFieldMatch);
				q.setHighlightSnippets(highlightSnippets);
				q.setHighlightSimplePost(highlightSimplePost);
				q.setHighlightSimplePre(highlightSimplePre);
				//高亮字段设置
				for(String field : highlightField) {
					q.addHighlightField(field);
				}
			}
			
			//设置排序规则
			for(String field : this.sortedValue.getValues().keySet()) {
				q.addSortField(field, this.sortedValue.getValues().get(field));
			}
			
			//设置返回的字段,默认		*,score
			if(this.returnField != null && this.returnField.length != 0) {
				StringBuffer buf = new StringBuffer();
				for(String field : this.returnField) {
					buf.append("," + field);
				}
				String flStr = buf.toString().replaceFirst(",", "");
				q.set("fl", flStr);
			} else {
				q.set("fl", "*,score");
			}
			
		}
		
		//是否显示调试信息
		q.setShowDebugInfo(this.showDebugInfo);
		
		return q;
	}
	
	/**
	 * 开启高亮,必须设置需要高亮的字段
	 * @param highlightField 需要高亮的字段
	 */
	public SolrSearchCondition openHighlight(String... highlightField) {
		this.openHighlight = true;
		this.highlightField = highlightField;
		return this;
	}
	
	/**
	 * 开启facet查询,必须设置facet字段
	 * @param fields
	 * @return
	 */
	public SolrSearchCondition openFacet(String... fields) {
		this.useFacet = true;
		this.facetField = fields;
		return this;
	}
	
	/**
	 * 开启group查询,必须设置group字段
	 * @param fields
	 * @return
	 */
	public SolrSearchCondition openGroup(String... fields) {
		this.useGroup = true;
		this.groupField = fields;
		return this;
	}


	public Integer getPageNo() {
		return pageNo;
	}


	public SolrSearchCondition setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
		return this;
	}


	public Integer getPageSize() {
		return pageSize;
	}


	public SolrSearchCondition setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
		return this;
	}


	public Boolean getOpenHighlight() {
		return openHighlight;
	}


	/* 开启高亮使用：openHighlight方法
	public void setOpenHighlight(Boolean openHighlight) {
		this.openHighlight = openHighlight;
	}
	 */

	public Boolean getHighlightRequireFieldMatch() {
		return highlightRequireFieldMatch;
	}

	public SolrSearchCondition setHighlightRequireFieldMatch(Boolean highlightRequireFieldMatch) {
		this.highlightRequireFieldMatch = highlightRequireFieldMatch;
		return this;
	}

	public String getHighlightSimplePost() {
		return highlightSimplePost;
	}

	public SolrSearchCondition setHighlightSimplePost(String highlightSimplePost) {
		this.highlightSimplePost = highlightSimplePost;
		return this;
	}

	public String getHighlightSimplePre() {
		return highlightSimplePre;
	}

	public SolrSearchCondition setHighlightSimplePre(String highlightSimplePre) {
		this.highlightSimplePre = highlightSimplePre;
		return this;
	}

	public Integer getHighlightFragsize() {
		return highlightFragsize;
	}

	public SolrSearchCondition setHighlightFragsize(Integer highlightFragsize) {
		this.highlightFragsize = highlightFragsize;
		return this;
	}

	public Integer getHighlightSnippets() {
		return highlightSnippets;
	}

	public SolrSearchCondition setHighlightSnippets(Integer highlightSnippets) {
		this.highlightSnippets = highlightSnippets;
		return this;
	}

	public void setShowDebugInfo(Boolean showDebugInfo) {
		this.showDebugInfo = showDebugInfo;
	}

	public Boolean getShowDebugInfo() {
		return showDebugInfo;
	}
	
	public String[] getReturnField() {
		return returnField;
	}

	public void setReturnField(String...returnField) {
		this.returnField = returnField;
	}

	public void setUseGroup(Boolean useGroup) {
		this.useGroup = useGroup;
	}

	public Boolean getUseGroup() {
		return useGroup;
	}

	public void setFacetLimit(Integer facetLimit) {
		this.facetLimit = facetLimit;
	}

	public Integer getFacetLimit() {
		return facetLimit;
	}

	public void setFacetMinCount(Integer facetMinCount) {
		this.facetMinCount = facetMinCount;
	}

	public Integer getFacetMinCount() {
		return facetMinCount;
	}

	public void setFacetMissing(Boolean facetMissing) {
		this.facetMissing = facetMissing;
	}

	public Boolean getFacetMissing() {
		return facetMissing;
	}

	public void setFacetSort(String facetSort) {
		this.facetSort = facetSort;
	}

	public String getFacetSort() {
		return facetSort;
	}
/*
	public void setGroupField(String[] groupField) {
		this.groupField = groupField;
	}
*/
	public String[] getGroupField() {
		return groupField;
	}

	public void setGroupLimit(Integer groupLimit) {
		this.groupLimit = groupLimit;
	}

	public Integer getGroupLimit() {
		return groupLimit;
	}
	
	public String[] getHighlightField() {
		return highlightField;
	}
/*
	public void setHighlightField(String[] highlightField) {
		this.highlightField = highlightField;
	}
*/
	public SortedValue getSortedValue() {
		return sortedValue;
	}

	public Boolean getUseFacet() {
		return useFacet;
	}

}
