package com.star72.cmsmain.cms.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.star72.cmsmain.cms.entity.main.Content;
import com.star72.cmsmain.cms.lucene.handler.ContentSolrHandler;
import com.star72.cmsmain.core.entity.CmsSite;

/**
 * ContentListener的抽象实现
 */
public class ContentListenerAbstract implements ContentListener {
	
	public void afterChange(Content content, Map<String, Object> map) {
	}

	public void afterDelete(Content content) {
	}

	public void afterSave(Content content) {
	}

	public Map<String, Object> preChange(Content content) {
		return null;
	}

	public void preDelete(Content content) {
	}

	public void preSave(Content content) {
	}
	
}
