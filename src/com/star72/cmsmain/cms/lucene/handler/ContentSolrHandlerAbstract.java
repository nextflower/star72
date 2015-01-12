package com.star72.cmsmain.cms.lucene.handler;

import com.star72.cmsmain.cms.entity.main.Content;
import com.star72.search.solrmodule.query.EPSSolrServerForCommon;

/**
 * content 通过 solr 进行索引操作的通用处理类
 * 
 * @author larry
 *
 */
public abstract class ContentSolrHandlerAbstract implements ContentSolrHandler {

	@Override
	public void createIndex(Content content) {
		EPSSolrServerForCommon server = new EPSSolrServerForCommon();
		server.setSolrURL(content.getSite().getSolrPath());
		server.addDocument(createSolrDocument(content), false);
	}

	@Override
	public void deleteIndex(Content content) {
		EPSSolrServerForCommon server = new EPSSolrServerForCommon();
		server.setSolrURL(content.getSite().getSolrPath());
		server.deleteById(String.valueOf(content.getId()));
	}

	@Override
	public void updateIndex(Content content) {
		deleteIndex(content);
		createIndex(content);
	}

}
