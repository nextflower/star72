package com.star72.search.solrmodule.page;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.solr.common.SolrDocumentList;

import com.star72.search.solrmodule.core.SolrSearchConstants;


/**
 * 检索结果PO
 * 
 * @author wz
 *
 */
public class SolrResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8439428767301072787L;
	
	public static final String HIGH_LIGHT_KEY = "highlighting";
	public static final String RESULT_LIST = "resultList";
	
	//分页信息
	private int pageSize;
	private int pageNo;
	private int totalCount;
	
	//最高得分,只有在查询参数中设置了返回score才会有值
	private Float maxScore;
	//是否为facet查询
	private Boolean isFacetQuery;
	//存放facet结果
	private Map<String, Map<String, Long>> facetMap = new HashMap<String, Map<String,Long>>();
	//是否为group查询
	private Boolean isGroupQuery;
	//存放group结果
	private Map<String, Map<String, SolrDocumentList>> groupMap = new HashMap<String, Map<String,SolrDocumentList>>();
	
	//总量相关信息
	
	//结果封装
	private Map<String, Object> resultMap = new HashMap<String, Object>();
	
	public SolrResult() {
		init();
	}
	
	private void init() {
		this.pageNo = SolrSearchConstants.PAGE_NO;
		this.pageSize = SolrSearchConstants.PAGE_SIZE;
		this.totalCount = SolrSearchConstants.PAGE_TOTAL_COUNT;
		this.isFacetQuery = false;
		this.isGroupQuery = false;
	}
	
	
	/**
	 * 第一条数据位置
	 * 
	 * @return
	 */
	public int getFirstResult() {
		return this.pageNo * this.pageSize;
	}
	
	/**
	 * 总共几页
	 */
	public int getTotalPage() {
		int totalPage = this.totalCount / this.pageSize;
		if (totalPage == 0 || this.totalCount % this.pageSize != 0) {
			totalPage++;
		}
		return totalPage;
	}
	
	/**
	 * 是否第一页
	 */
	public boolean isFirstPage() {
		return this.pageNo <= 0;
	}

	/**
	 * 是否最后一页
	 */
	public boolean isLastPage() {
		return this.pageNo >= getTotalPage();
	}

	/**
	 * 下一页页码
	 */
	public int getNextPage() {
		if (isLastPage()) {
			return this.pageNo;
		} else {
			return this.pageNo + 1;
		}
	}

	/**
	 * 上一页页码
	 */
	public int getPrePage() {
		if (isFirstPage()) {
			return this.pageNo;
		} else {
			return this.pageNo - 1;
		}
	}
	

	public void setMaxScore(Float maxScore) {
		this.maxScore = maxScore;
	}
	public Float getMaxScore() {
		return maxScore;
	}
	public void setIsFacetQuery(Boolean isFacetQuery) {
		this.isFacetQuery = isFacetQuery;
	}
	public Boolean getIsFacetQuery() {
		return isFacetQuery;
	}
	public void setFacetMap(Map<String, Map<String, Long>> facetMap) {
		this.facetMap = facetMap;
	}
	public Map<String, Map<String, Long>> getFacetMap() {
		return facetMap;
	}
	public void setIsGroupQuery(Boolean isGroupQuery) {
		this.isGroupQuery = isGroupQuery;
	}
	public Boolean getIsGroupQuery() {
		return isGroupQuery;
	}
	public void setGroupMap(Map<String, Map<String, SolrDocumentList>> groupMap) {
		this.groupMap = groupMap;
	}
	public Map<String, Map<String, SolrDocumentList>> getGroupMap() {
		return groupMap;
	}
	public void setPageSize(int pageSize) {
		if (pageSize < 1) {
			this.pageSize = SolrSearchConstants.PAGE_SIZE;
		} else {
			this.pageSize = pageSize;
		}
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageNo(int pageNo) {
		if (pageNo < 0) {
			this.pageNo = SolrSearchConstants.PAGE_NO;
		} else {
			this.pageNo = pageNo;
		}
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getTotalCount() {
		return totalCount;
	}

	public void setResultMap(Map<String, Object> resultMap) {
		this.resultMap = resultMap;
	}

	public Map<String, Object> getResultMap() {
		return resultMap;
	}

	@Override
	public String toString() {
		return "SolrResult [pageSize=" + pageSize + ", pageNo=" + pageNo
				+ ", totalCount=" + totalCount + ", maxScore=" + maxScore
				+ ", isFacetQuery=" + isFacetQuery + ", facetMap=" + facetMap
				+ ", isGroupQuery=" + isGroupQuery + ", groupMap=" + groupMap
				+ ", resultMap=" + resultMap + "]";
	}
	
	
	

}
