package com.star72.cmsmain.cms.dao.main;

import java.util.List;

import com.star72.cmsmain.cms.entity.main.ContentTag;
import com.star72.cmsmain.common.hibernate3.Updater;
import com.star72.cmsmain.common.page.Pagination;

public interface ContentTagDao {
	public List<ContentTag> getList(Integer count, boolean cacheable);

	public Pagination getPage(String name, int pageNo, int pageSize,
			boolean cacheable);

	public ContentTag findById(Integer id);

	public ContentTag findByName(String name, boolean cacheable);

	public ContentTag save(ContentTag bean);

	public ContentTag updateByUpdater(Updater<ContentTag> updater);

	public ContentTag deleteById(Integer id);

	public int deleteContentRef(Integer id);

	public int countContentRef(Integer id);
}