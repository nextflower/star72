package com.star72.cmsmain.cms.lucene;

import java.util.Date;

import com.star72.cmsmain.cms.entity.main.Content;

public interface SolrContentDao {

	public Integer index(Integer siteId, Integer channelId, Date startDate, Date endDate);
	
	public void deleteIndex(Content content);
	
	public void createIndex(Content content);
	
	public void updateIndex(Content content);
	
}
