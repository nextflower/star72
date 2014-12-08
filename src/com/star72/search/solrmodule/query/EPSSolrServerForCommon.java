package com.star72.search.solrmodule.query;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import com.star72.search.solrmodule.condition.SolrItem;
import com.star72.search.solrmodule.page.SolrResult;
import com.star72.search.solrmodule.write.EpsSolrDocument;


/**
 * 通用检索处理类,可以用于对检索结果没有特殊封装要求的场合
 * 注：该类也提供用户自定义写索引的方法
 * 
 * @author wz
 *
 */
public class EPSSolrServerForCommon extends AbstractEPSSolrServer {
	
	/**
	 * 根据查询条件进行删除
	 * @param query
	 */
	public void deleteByQuery(String query) {
		try {
			getServer().deleteByQuery(query);
			getServer().commit();
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据查询条件进行删除
	 * @param item
	 */
	public void deleteByQuery(SolrItem item) {
		deleteByQuery(item.getQueryString());
	}
	
	/**
	 * 根据id批量删除
	 * @param ids
	 */
	public void deleteByIds(List<String> ids) {
		try {
			getServer().deleteById(ids);
			getServer().commit();
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除一条索引
	 * @param id
	 */
	public void deleteById(String id) {
		try {
			getServer().deleteById(id);
			getServer().commit();
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 批量添加document对象
	 * @param docs
	 * @param commit 是否直接提交
	 */
	public void addDocuments(List<EpsSolrDocument> docs, boolean commit) {
		try {
			if(docs != null) {
				
				List<SolrInputDocument> solrDocs = new ArrayList<SolrInputDocument>();
				
				for(EpsSolrDocument doc : docs) {
					solrDocs.add(doc);
				}
				
				if(solrDocs.size() > 0) {
					getServer().add(solrDocs);
				}
				
				if(commit) {
					getServer().commit();
				}
				
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 添加一条索引
	 * @param doc
	 * @param commit 是否立即提交
	 */
	public void addDocument(EpsSolrDocument doc, boolean commit) {
		try {
			getServer().add(doc);
			if(commit) {
				getServer().commit();
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 提交
	 * @return
	 */
	public int commit() {
		try {
			UpdateResponse response = getServer().commit(false, false);
			return response.getStatus();
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	@Override
	public Map<String, Object> generateResultMap(QueryResponse response) {
		
//		System.out.println("EPSSolrServerForCommon...generateResultMap");
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		/*
		 * 获取本次查询的总匹配数量
		 */
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

		SolrDocumentList list = response.getResults();
		
		for (SolrDocument sd : list) {
			Collection<String> fieldNames = sd.getFieldNames();
			Map<String, Object> map = new HashMap<String, Object>();
			
			String id = (String) sd.getFieldValue(getIDKey());
			
			for (String field : fieldNames) {
				//加入动态字段的处理
				map.put(deleteDynamicFieldPrefix(field), sd.getFieldValue(field));
			}
			
			//高亮文本拼接
			
			Map<String, String> highlightingMap = new HashMap<String,String>();
			Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
			if(highlighting != null) {
				Map<String, List<String>> hlMap = response.getHighlighting().get(id);
				if(hlMap != null) {
					for(String key : hlMap.keySet()) {
						List<String> hlList = hlMap.get(key);
						StringBuffer sb = new StringBuffer();
						if(hlList != null) {
							String post = "";
							if(hlList.size() > 1) {
								post = "...";
							} 
							for(String hl : hlList) {
								sb.append(hl + post);
							}
						}
						highlightingMap.put(deleteDynamicFieldPrefix(key), sb.toString());
					}
				}
			}
			
			//highlighting存放在固定的字段内
			map.put(SolrResult.HIGH_LIGHT_KEY, highlightingMap);
			
			resultList.add(map);
		}
		
		result.put(SolrResult.RESULT_LIST, resultList);
		
		return result;
	}
	
	/**
	 * 获取索引库中作为主键的字段,可以在子类中覆盖此方法,默认值是ID
	 * @return
	 */
	public String getIDKey() {
		return "ID";
	}

}
