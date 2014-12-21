package com.star72.cmsmain.cms.manager.assist;

import com.star72.cmsmain.cms.entity.assist.CmsComment;
import com.star72.cmsmain.cms.entity.assist.CmsCommentExt;

public interface CmsCommentExtMng {
	public CmsCommentExt save(String ip, String text, CmsComment comment);

	public CmsCommentExt update(CmsCommentExt bean);

	public int deleteByContentId(Integer contentId);
}