package com.star72.cmsmain.cms.lucene.handler;

import com.star72.cmsmain.cms.entity.main.Content;
import com.star72.search.solrmodule.write.EpsSolrDocument;

/**
 * 通用索引生成类
 * 
 * @author larry
 *
 */
public class ContentSolrHandler4Common extends ContentSolrHandlerAbstract {


	@Override
	public EpsSolrDocument createSolrDocument(Content content) {
		EpsSolrDocument doc = new EpsSolrDocument();
		doc.addField("ID", content.getId());
		return doc;
	}

}