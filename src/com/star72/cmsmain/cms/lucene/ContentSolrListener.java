package com.star72.cmsmain.cms.lucene;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.star72.cmsmain.cms.entity.main.Content;
import com.star72.cmsmain.cms.lucene.handler.ContentSolrHandler;
import com.star72.cmsmain.cms.service.ContentListenerAbstract;

/**
 * 基于solr的监听器
 * 
 * @author larry
 *
 */
public class ContentSolrListener extends ContentListenerAbstract {

	private static final Logger log = LoggerFactory.getLogger(ContentSolrListener.class);
	
	/**
	 * 不同站点的处理类
	 */
	private Map<Integer, ContentSolrHandler> handleMap = new HashMap<Integer, ContentSolrHandler>();
	
	/**
	 * 是否已审核
	 */
	private static final String IS_CHECKED = "isChecked";

	@Override
	public void afterSave(Content content) {
	}

	@Override
	public Map<String, Object> preChange(Content content) {
		Map<String, Object> map = new HashMap<String, Object>();
		return map;
	}

	@Override
	public void afterChange(Content content, Map<String, Object> map) {
	}

	@Override
	public void afterDelete(Content content) {
	}
	
}
