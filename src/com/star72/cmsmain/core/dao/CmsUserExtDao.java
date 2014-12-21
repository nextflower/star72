package com.star72.cmsmain.core.dao;

import com.star72.cmsmain.common.hibernate3.Updater;
import com.star72.cmsmain.core.entity.CmsUserExt;

public interface CmsUserExtDao {
	public CmsUserExt findById(Integer id);

	public CmsUserExt save(CmsUserExt bean);

	public CmsUserExt updateByUpdater(Updater<CmsUserExt> updater);
}