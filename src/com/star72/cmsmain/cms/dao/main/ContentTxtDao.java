package com.star72.cmsmain.cms.dao.main;

import com.star72.cmsmain.cms.entity.main.ContentTxt;
import com.star72.cmsmain.common.hibernate3.Updater;

public interface ContentTxtDao {
	public ContentTxt findById(Integer id);

	public ContentTxt save(ContentTxt bean);

	public ContentTxt updateByUpdater(Updater<ContentTxt> updater);
}