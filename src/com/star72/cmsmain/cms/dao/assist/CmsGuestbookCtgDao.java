package com.star72.cmsmain.cms.dao.assist;

import java.util.List;

import com.star72.cmsmain.cms.entity.assist.CmsGuestbookCtg;
import com.star72.cmsmain.common.hibernate3.Updater;

public interface CmsGuestbookCtgDao {
	public List<CmsGuestbookCtg> getList(Integer siteId);

	public CmsGuestbookCtg findById(Integer id);

	public CmsGuestbookCtg save(CmsGuestbookCtg bean);

	public CmsGuestbookCtg updateByUpdater(Updater<CmsGuestbookCtg> updater);

	public CmsGuestbookCtg deleteById(Integer id);
}