package com.star72.cmsmain.cms.lucene;

import java.util.Date;

import com.star72.cmsmain.cms.entity.main.Content;

public interface SolrContentSvc {
	
	public Integer createIndex(Integer siteId, Integer channelId,
			Date startDate, Date endDate, Integer startId, Integer endId);

	public void createIndex(Content content);

	public void deleteIndex(Content content);

	public void updateIndex(Content content);

}
