package com.star72.cmsmain.cms.dao.main;

import com.star72.cmsmain.cms.entity.main.ContentExt;
import com.star72.cmsmain.common.hibernate3.Updater;

public interface ContentExtDao {
	public ContentExt findById(Integer id);

	public ContentExt save(ContentExt bean);

	public ContentExt updateByUpdater(Updater<ContentExt> updater);
}