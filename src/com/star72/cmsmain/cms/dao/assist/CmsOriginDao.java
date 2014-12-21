package com.star72.cmsmain.cms.dao.assist;

import java.util.List;

import com.star72.cmsmain.common.hibernate3.Updater;
import com.star72.cmsmain.common.page.Pagination;
import com.star72.cmsmain.cms.entity.assist.CmsOrigin;

public interface CmsOriginDao {
	public Pagination getPage(int pageNo, int pageSize);

	public List<CmsOrigin> getList(String name);

	public CmsOrigin findById(Integer id);

	public CmsOrigin save(CmsOrigin bean);

	public CmsOrigin updateByUpdater(Updater<CmsOrigin> updater);

	public CmsOrigin deleteById(Integer id);
}