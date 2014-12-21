package com.star72.cmsmain.cms.manager.main;

import com.star72.cmsmain.cms.entity.main.Content;
import com.star72.cmsmain.cms.entity.main.ContentTxt;

public interface ContentTxtMng {
	public ContentTxt save(ContentTxt txt, Content content);

	public ContentTxt update(ContentTxt txt, Content content);
}