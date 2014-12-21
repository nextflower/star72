package com.star72.cmsmain.core.dao;

import java.util.Date;

import com.star72.cmsmain.common.page.Pagination;
import com.star72.cmsmain.core.entity.CmsLog;

public interface CmsLogDao {
	public Pagination getPage(Integer category, Integer siteId, Integer userId,
			String title, String ip, int pageNo, int pageSize);

	public CmsLog findById(Integer id);

	public CmsLog save(CmsLog bean);

	public CmsLog deleteById(Integer id);

	public int deleteBatch(Integer category, Integer siteId, Date before);
}