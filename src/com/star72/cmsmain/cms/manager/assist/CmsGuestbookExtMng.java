package com.star72.cmsmain.cms.manager.assist;

import com.star72.cmsmain.cms.entity.assist.CmsGuestbook;
import com.star72.cmsmain.cms.entity.assist.CmsGuestbookExt;

public interface CmsGuestbookExtMng {
	public CmsGuestbookExt save(CmsGuestbookExt ext, CmsGuestbook guestbook);

	public CmsGuestbookExt update(CmsGuestbookExt ext);
}