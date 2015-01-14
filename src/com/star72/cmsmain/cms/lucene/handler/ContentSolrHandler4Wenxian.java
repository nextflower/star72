package com.star72.cmsmain.cms.lucene.handler;

import com.star72.cmsmain.cms.entity.main.Content;
import com.star72.search.solrmodule.write.EpsSolrDocument;

/**
 * 
 * @author larry
 *
 */
public class ContentSolrHandler4Wenxian extends ContentSolrHandlerAbstract{
	
	public static final String FIELD_ID = "id";
	public static final String FIELD_CAT = "cat";
	public static final String FIELD_AUTHOR = "author";
	public static final String FIELD_CHAODAI = "chaodai";
	public static final String FIELD_SOURCE = "source";

	@Override
	public EpsSolrDocument createSolrDocument(Content content) {
		EpsSolrDocument doc = new EpsSolrDocument();
		doc.addField(FIELD_ID, content.getId());
		return doc;
	}

}
