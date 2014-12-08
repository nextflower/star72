package com.star72.search.solrmodule.query;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import com.star72.search.solrmodule.page.SolrResult;


/**
 * 作文库检索处理类
 * 
 * @author wz
 *
 */
public class EPSSolrServerForCIPG extends EPSSolrServerForCommon {
	

	@Override
	public Map<String, Object> generateResultMap(QueryResponse response) {
		
		Map<String, Object> result = new HashMap<String, Object>();

		/*
		 * 获取本次查询的总匹配数量
		 */
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

		SolrDocumentList list = response.getResults();
		
		for (SolrDocument sd : list) {
			Collection<String> fieldNames = sd.getFieldNames();
			Map<String, Object> map = new HashMap<String, Object>();
			
			String id = (String) sd.getFieldValue("ID");
			
			for (String field : fieldNames) {
				//加入动态字段的处理
				map.put(deleteDynamicFieldPrefix(field), sd.getFieldValue(field));
			}
			
			Map<String, String> highlightingMap = generateHighlightingMap(response, id);
			
			//highlighting存放在固定的字段内
			map.put(SolrResult.HIGH_LIGHT_KEY, highlightingMap);
			
			resultList.add(map);
		}
		
		result.put(SolrResult.RESULT_LIST, resultList);
		
		return result;
	}

}
