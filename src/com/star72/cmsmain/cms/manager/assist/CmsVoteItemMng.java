package com.star72.cmsmain.cms.manager.assist;

import java.util.Collection;

import com.star72.cmsmain.cms.entity.assist.CmsVoteItem;
import com.star72.cmsmain.cms.entity.assist.CmsVoteSubTopic;
import com.star72.cmsmain.common.page.Pagination;

public interface CmsVoteItemMng {
	public Pagination getPage(int pageNo, int pageSize);

	public CmsVoteItem findById(Integer id);

	public Collection<CmsVoteItem> save(Collection<CmsVoteItem> items,
			CmsVoteSubTopic topic);

	public Collection<CmsVoteItem> update(Collection<CmsVoteItem> items,
			CmsVoteSubTopic topic);

	public CmsVoteItem deleteById(Integer id);

	public CmsVoteItem[] deleteByIds(Integer[] ids);

}