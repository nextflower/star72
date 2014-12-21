package com.star72.cmsmain.cms.manager.main;

import com.star72.cmsmain.cms.entity.main.Content;
import com.star72.cmsmain.cms.entity.main.ContentExt;

public interface ContentExtMng {
	public ContentExt save(ContentExt ext, Content content);

	public ContentExt update(ContentExt ext);
}