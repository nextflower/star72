package com.star72.cmsmain.cms.dao.assist;

import java.util.List;

import com.star72.cmsmain.cms.entity.assist.CmsVoteTopic;
import com.star72.cmsmain.common.hibernate3.Updater;
import com.star72.cmsmain.common.page.Pagination;

public interface CmsVoteTopicDao {
	public Pagination getPage(Integer siteId, int pageNo, int pageSize);
	
	public List<CmsVoteTopic> getList(Boolean def,Integer siteId, int count);

	public CmsVoteTopic getDefTopic(Integer siteId);

	public CmsVoteTopic findById(Integer id);

	public CmsVoteTopic save(CmsVoteTopic bean);

	public CmsVoteTopic updateByUpdater(Updater<CmsVoteTopic> updater);

	public CmsVoteTopic deleteById(Integer id);
}