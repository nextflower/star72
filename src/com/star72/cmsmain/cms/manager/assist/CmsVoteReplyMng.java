package com.star72.cmsmain.cms.manager.assist;

import com.star72.cmsmain.cms.entity.assist.CmsVoteReply;
import com.star72.cmsmain.common.page.Pagination;

public interface CmsVoteReplyMng {

	public Pagination getPage(Integer  subTopicId, int pageNo, int pageSize);
	
	public CmsVoteReply findById(Integer id);

	public CmsVoteReply save(CmsVoteReply bean);

	public CmsVoteReply update(CmsVoteReply bean);

	public CmsVoteReply deleteById(Integer id);

	public CmsVoteReply[] deleteByIds(Integer[] ids);
}