package com.star72.search.solrmodule.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.Group;
import org.apache.solr.client.solrj.response.GroupCommand;
import org.apache.solr.client.solrj.response.GroupResponse;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.SpellCheckResponse;
import org.apache.solr.client.solrj.response.SpellCheckResponse.Suggestion;
import org.apache.solr.common.SolrDocumentList;

import com.star72.search.solrmodule.condition.SolrSearchCondition;
import com.star72.search.solrmodule.condition.SolrStringItem;
import com.star72.search.solrmodule.core.GlobalConfig;
import com.star72.search.solrmodule.core.SolrSearchConstants;
import com.star72.search.solrmodule.page.SolrResult;


/**
 * solr检索处理类适配器, 当前版本中已经将涉及索引增、删、改的API接口注释掉, 此类操作尽量使用自动生成的方式。
 * 
 * @author wz
 *
 */
public abstract class AbstractEPSSolrServer implements EPSSolrServer{
	
	private HttpSolrServer server = new HttpSolrServer("");
	
	//动态字段前缀列表
	private List<String> dynamicPrefixs = new ArrayList<String>();
	
	{
		//增加默认的动态字段
		dynamicPrefixs.add(SolrSearchConstants.DYNAMIC_FILED_PREFIX);
	}
	
	public AbstractEPSSolrServer() {
		try {
			//设置远程Solr地址
			ResourceBundle rb = ResourceBundle.getBundle(GlobalConfig.SOLR_CONFIG);
			setSolrURL(rb.getString(GlobalConfig.SOLR_URL));
		} catch (Exception e) {
			System.out.println("classpath下未找到solr.properties文件或者配置文件中不存在" + GlobalConfig.SOLR_URL + "属性." +
					"若当前classpath下不存在solr.properties文件,请将该jar包下的solr.demo.properties复制一份,修改名称即可.");
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取索引库中符合条件的总记录数
	 * @return
	 */
	public Integer getTotalCount(SolrSearchCondition condition) {
		try {
			SolrQuery params = new SolrQuery(condition.getQueryString());
			params.setRows(0);
			QueryResponse res = getServer().query(params);
			SolrDocumentList solrList = (SolrDocumentList) res.getResponse().get("response");
			return (int) solrList.getNumFound();
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取总数量
	 */
	public Integer getTotalCount() {
		SolrSearchCondition condition = new SolrSearchCondition(new SolrStringItem("*:*"));
		return getTotalCount(condition);
	}
	
	/**
	 * 自动补全,solr端需要额外的配置才能生效
	 * solr端配置方式：
	 * 		在solr_home/solr/节点名称/solrconfig.xml中定义
	 * 		<searchComponent name="suggest" class="solr.SpellCheckComponent">  
			  <lst name="spellchecker">  
			    <str name="name">suggest</str>  
			    <str name="classname">org.apache.solr.spelling.suggest.Suggester</str>  
			    <str name="lookupImpl">org.apache.solr.spelling.suggest.tst.TSTLookup</str>  
			    <str name="field">title</str>
			  </lst>
			  <!--lst可以定义多个-->
			</searchComponent> 
			
			
	 * @param token 提示输入词
	 * @return 提示词列表,顺序基于词频,和搜索热度无关
	 */
	public List<String> suggest(String token) {
		List<String> suggestList = new ArrayList<String>();
		SolrQuery params = new SolrQuery();
		params.set("qt", "/suggest");
		params.set("q", token);
		params.set("spellcheck.build", "true");
		QueryResponse response = null;
		try {
			response = server.query(params);
			SpellCheckResponse spellCheckResponse = response.getSpellCheckResponse();
			if (spellCheckResponse != null) {
				Suggestion su = spellCheckResponse.getSuggestion(token);
				if(su != null) {
					List<String> suggestedWordList = su.getAlternatives();
					if(suggestedWordList != null) {
						return suggestedWordList;
					}
				}
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
		
		return suggestList;
	}
	
	/**
	 * 查询并返回
	 * @param condition
	 * @return
	 */
	public SolrResult query(SolrSearchCondition condition) {
		
		//构建page对象
		SolrResult result = new SolrResult();
		
		try {
			
			QueryResponse response = getServer().query(condition.getSolrParams());
			
			if(condition.getUseGroup()) {
				//分组查询,结果封装
				result.setIsGroupQuery(true);
				Map<String, Map<String, SolrDocumentList>> groupMap = new HashMap<String, Map<String,SolrDocumentList>>();
				
				GroupResponse gr = response.getGroupResponse();
				List<GroupCommand> values = gr.getValues();
				for(GroupCommand cmd : values) {
					String name = cmd.getName();
					List<Group> vs = cmd.getValues();
					Map<String, SolrDocumentList> docMap = new HashMap<String, SolrDocumentList>();
					for(Group g : vs) {
						String value = g.getGroupValue();
						SolrDocumentList list = g.getResult();
						docMap.put(value, list);
					}
					groupMap.put(name, docMap);
					result.setTotalCount(vs.size());
				}
				
				result.setGroupMap(groupMap);
				
			} else if(condition.getUseFacet()) {
				//facet查询,结果封装
				result.setIsFacetQuery(true);
				Map<String, Map<String, Long>> facetMap = new HashMap<String, Map<String,Long>>();
				
				List<FacetField> facetFields = response.getFacetFields();
				for(FacetField f : facetFields) {
					String name = f.getName();
					Map<String, Long> countMap = new HashMap<String, Long>();
					List<Count> values = f.getValues();
					if(values == null) {
						continue;
					}
					long totalCount = 0;
					for(Count c : values) {
						String countName = c.getName();
						long count = c.getCount();
						totalCount += count;
						countMap.put(countName, count);
					}
					facetMap.put(name, countMap);
					result.setTotalCount((int)totalCount);
				}
				
				result.setFacetMap(facetMap);
			} else {
				//普通查询,结果封装
				SolrDocumentList solrList = response.getResults();
				//设置分页信息
				result.setPageSize(condition.getPageSize());
				result.setTotalCount((int) solrList.getNumFound());
				result.setPageNo(condition.getPageNo());
				result.setMaxScore(solrList.getMaxScore());
				/*
				 * 解析SolrDocumentList,封装数据到Map中
				 */
				result.setResultMap(generateResultMap(response));
			}
			
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * 生成高亮Map
	 * @param response
	 * @param id
	 * @return
	 */
	protected Map<String, String> generateHighlightingMap(QueryResponse response, String id) {
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
		return highlightingMap;
	}

	/**
	 * 解析response对象,封装为Map<String, Object>
	 * @param response
	 * @return
	 */
	public abstract Map<String, Object> generateResultMap(QueryResponse response);
	
	/**==========================================================================================================================*/
	
	/*
	 * 下面的多个方法用于配置HttpSolrServer
	 */
	public void setSolrURL(String solrURL) {
		this.server.setBaseURL(solrURL);
	}

	public void setSoTimeout(Integer soTimeout) {
		this.server.setSoTimeout(soTimeout);
	}

	public void setConnectionTimeout(Integer connectionTimeout) {
		this.server.setConnectionTimeout(connectionTimeout);
	}

	public void setDefaultMaxConnectionsPerHost(Integer defaultMaxConnectionsPerHost) {
		this.server.setDefaultMaxConnectionsPerHost(defaultMaxConnectionsPerHost);
	}


	public void setMaxTotalConnections(Integer maxTotalConnections) {
		this.server.setMaxTotalConnections(maxTotalConnections);
	}

	public void setFollowRedirects(Boolean followRedirects) {
		this.server.setFollowRedirects(followRedirects);
	}

	public void setAllowCompression(Boolean allowCompression) {
		this.server.setAllowCompression(allowCompression);
	}

	public void setMaxRetries(Integer maxRetries) {
		this.server.setMaxRetries(maxRetries);
	}
	
	/*
	 * 获取server对象,此处需要重新指定远程solr地址
	 */
	public HttpSolrServer getServer() {
		return this.server;
	}

	/*
	 * 设置动态字段的前缀,通过设置字符串数据的形式
	 */
	public void setDynamicPrefix(String...dynamicPrefixs) {
		if(dynamicPrefixs != null) {
			for(String dynamicPrefix : dynamicPrefixs) {
				this.dynamicPrefixs.add(dynamicPrefix);
			}
		}
	}

	public List<String> getDynamicPrefixs() {
		return this.dynamicPrefixs;
	}
	
	/**
	 * 去掉动态字段的前缀
	 * @param fieldName
	 * @return
	 */
	protected String deleteDynamicFieldPrefix(String fieldName) {
		
		if(fieldName == null) {
			return null;
		} else {
			for(String prefix : getDynamicPrefixs()) {
				if(fieldName.startsWith(prefix)) {
					return fieldName.substring(prefix.length());
				}
			}
		}
		return fieldName;
	}
	
	/**
	 * 批量添加document,不建议使用此种方式
	 * @param docs
	 * @return
	 */
	/*
	public Integer addDocument(Collection<SolrInputDocument> docs) {
		try {
			UpdateResponse response = getServer().add(docs);
			commit(response);
			return response.getStatus();
		} catch (SolrServerException e) {
			rollback();
			e.printStackTrace();
		} catch (IOException e) {
			rollback();
			e.printStackTrace();
		}
		return null;
	}
	*/

	/**
	 * 添加document到索引库中
	 * @param doc
	 * @return
	 */
	/*
	public Integer addDocument(SolrInputDocument doc) {
		try {
			UpdateResponse response = getServer().add(doc);
			commit(response);
			return response.getStatus();
		} catch (SolrServerException e) {
			rollback();
			e.printStackTrace();
		} catch (IOException e) {
			rollback();
			e.printStackTrace();
		}
		return null;
	}
	*/
	
	/**
	 * 根据SolrSearchCondition对象进行删除
	 * @param condition
	 * @return
	 */
	/*
	public Integer deleteDocument(SolrSearchCondition condition) {
		return deleteDocumentByQuery(condition.getQueryString());
	}
	*/
	
	/**
	 * 根据查询条件删除索引
	 * @param queryStr
	 * @return
	 */
	/*
	public Integer deleteDocumentByQuery(String queryStr) {
		try {
			UpdateResponse response = getServer().deleteByQuery(queryStr);
			commit(response);
			return response.getStatus();
		} catch (SolrServerException e) {
			rollback();
			e.printStackTrace();
		} catch (IOException e) {
			rollback();
			e.printStackTrace();
		}
		return null;
	}
	*/

	/**
	 * 根据document id删除
	 * @param id
	 * @return
	 */
	/*
	public Integer deleteDocumentById(String id) {
		try {
			UpdateResponse response = getServer().deleteById(id.toString());
			commit(response);
			return response.getStatus();
		} catch (SolrServerException e) {
			rollback();
			e.printStackTrace();
		} catch (IOException e) {
			rollback();
			e.printStackTrace();
		}
		return null;
	}
	*/
	
	/**
	 * 根据document id批量删除
	 * @param ids
	 * @return
	 */
	/*
	public Integer deleteDocumentByIds(List<String> ids) {
		try {
			UpdateResponse response = getServer().deleteById(ids);
			commit(response);
			return response.getStatus();
		} catch (SolrServerException e) {
			rollback();
			e.printStackTrace();
		} catch (IOException e) {
			rollback();
			e.printStackTrace();
		}
		return null;
	}
	*/
	
	
	/**
	 * 根据返回的状态码提交或回滚
	 * @param response
	 * @throws SolrServerException
	 * @throws IOException
	 */
	/*
	private void commit(UpdateResponse response) throws SolrServerException, IOException {
		if(response.getStatus() == 0) {
			getServer().commit();
		} else {
			getServer().rollback();
		}
	}
	*/
	
	/**
	 * 回滚
	 */
	/*
	private void rollback() {
		try {
			getServer().rollback();
		} catch (SolrServerException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	*/
	
}
