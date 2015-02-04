package com.star72.cmsmain.cms.lucene;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.star72.cmsmain.cms.entity.main.Content;

@Service
@Transactional
public class SolrContentSvcImpl implements SolrContentSvc {
	
	@Transactional(readOnly = true)
	public Integer createIndex(Integer siteId, Integer channelId,
			Date startDate, Date endDate, Integer startId, Integer endId){
		return solrContentDao.index(siteId, channelId, startDate, endDate, startId, endId);
	}



	public void updateIndex(Content content){
		solrContentDao.updateIndex(content);
	}


	@Transactional(readOnly = true)
	@Override
	public void createIndex(Content content) {
		solrContentDao.createIndex(content);
	}



	@Transactional(readOnly = true)
	@Override
	public void deleteIndex(Content content) {
		solrContentDao.deleteIndex(content);
	}
	
	@Autowired
	private SolrContentDao solrContentDao;

}
