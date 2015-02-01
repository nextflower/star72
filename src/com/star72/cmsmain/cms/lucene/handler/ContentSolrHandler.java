package com.star72.cmsmain.cms.lucene.handler;

import com.star72.cmsmain.cms.entity.main.Content;
import com.star72.search.solrmodule.write.EpsSolrDocument;

/**
 * 处理接口
 * 
 * @author larry
 *
 */
public interface ContentSolrHandler {

	public void createIndex(Content content);
	
	public void deleteIndex(Content content);
	
	public void updateIndex(Content content);
	
	public EpsSolrDocument createSolrDocument(Content content);
	
	public Integer getSiteId();
	
}
