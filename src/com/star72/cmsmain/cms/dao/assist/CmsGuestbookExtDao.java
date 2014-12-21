package com.star72.cmsmain.cms.dao.assist;

import com.star72.cmsmain.cms.entity.assist.CmsGuestbookExt;
import com.star72.cmsmain.common.hibernate3.Updater;

public interface CmsGuestbookExtDao {
	public CmsGuestbookExt findById(Integer id);

	public CmsGuestbookExt save(CmsGuestbookExt bean);

	public CmsGuestbookExt updateByUpdater(Updater<CmsGuestbookExt> updater);

	public CmsGuestbookExt deleteById(Integer id);
}