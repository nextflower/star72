package com.star72.cmsmain.cms.lucene.handler;

import org.apache.commons.lang.StringUtils;

import com.star72.cmsmain.cms.entity.main.Content;
import com.star72.search.solrmodule.query.AbstractEPSSolrServer;
import com.star72.search.solrmodule.query.EPSSolrServer;
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
		setSolrURL(content);
		getServer().addDocument(createSolrDocument(content), false);
	}

	@Override
	public void deleteIndex(Content content) {
		setSolrURL(content);
		getServer().deleteById(String.valueOf(content.getId()));
	}

	@Override
	public void updateIndex(Content content) {
		deleteIndex(content);
		createIndex(content);
	}
	
	private void setSolrURL(Content content) {
		String solrPath = content.getSite().getSolrPath();
		if(StringUtils.isNotBlank(solrPath)) {
			getServer().setSolrURL(solrPath);
		} else if(StringUtils.isNotBlank(getDefaultURL())){
			getServer().setSolrURL(getDefaultURL());
		} else {
			throw new RuntimeException("SolrURL初始化失败：star-context 或者 db site未设置");
		}
	}
	
	protected abstract EPSSolrServerForCommon getServer();
	
	protected abstract String getDefaultURL();

}
