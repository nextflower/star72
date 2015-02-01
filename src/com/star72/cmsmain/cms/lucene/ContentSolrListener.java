package com.star72.cmsmain.cms.lucene;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.queryParser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.star72.cmsmain.cms.entity.main.Content;
import com.star72.cmsmain.cms.lucene.handler.ContentSolrHandler;
import com.star72.cmsmain.cms.service.ContentListenerAbstract;
import com.star72.cmsmain.core.entity.CmsSite;

/**
 * 基于solr的监听器
 * 
 * @author larry
 *
 */
public class ContentSolrListener extends ContentListenerAbstract {

	private static final Logger log = LoggerFactory.getLogger(ContentSolrListener.class);
	
	@Autowired
	protected List<ContentSolrHandler> handlerList;
	
	/**
	 * 是否已审核
	 */
	private static final String IS_CHECKED = "isChecked";

	@Override
	public void afterSave(Content content) {
		ContentSolrHandler handler = getHandler(content);
		if(handler != null) {
			if (content.isChecked()) {
				handler.createIndex(content);
			}
		}
	}

	@Override
	public Map<String, Object> preChange(Content content) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(IS_CHECKED, content.isChecked());
		return map;
	}

	@Override
	public void afterChange(Content content, Map<String, Object> map) {
		ContentSolrHandler handler = getHandler(content);
		if(handler == null) {
			return;
		}
		boolean pre = (Boolean) map.get(IS_CHECKED);
		boolean curr = content.isChecked();
		if (pre && !curr) {
			handler.deleteIndex(content);
		} else if (!pre && curr) {
			handler.createIndex(content);
		} else if (pre && curr) {
			handler.updateIndex(content);
		}
	}

	@Override
	public void afterDelete(Content content) {
		ContentSolrHandler handler = getHandler(content);
		if(handler != null) {
			if (content.isChecked()) {
				handler.deleteIndex(content);
			}
		}
	}
	
	public ContentSolrHandler getHandler(Content content) {
		if(handlerList != null && content != null && content.getSite() != null) {
			CmsSite site = content.getSite();
			for(ContentSolrHandler handler : handlerList) {
				Integer siteId = handler.getSiteId();
				if(siteId != null && siteId.equals(site.getId())) {
					return handler;
				}
			}
		}
		return null;
	}
	
}
