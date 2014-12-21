package com.star72.cmsmain.cms.manager.assist;

import java.util.List;

import com.star72.cmsmain.cms.entity.assist.CmsGuestbookCtg;

public interface CmsGuestbookCtgMng {
	public List<CmsGuestbookCtg> getList(Integer siteId);

	public CmsGuestbookCtg findById(Integer id);

	public CmsGuestbookCtg save(CmsGuestbookCtg bean);

	public CmsGuestbookCtg update(CmsGuestbookCtg bean);

	public CmsGuestbookCtg deleteById(Integer id);

	public CmsGuestbookCtg[] deleteByIds(Integer[] ids);
}