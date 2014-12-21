package com.star72.cmsmain.core.dao;

import com.star72.cmsmain.common.hibernate3.Updater;
import com.star72.cmsmain.core.entity.CmsConfig;

public interface CmsConfigDao {
	public CmsConfig findById(Integer id);

	public CmsConfig updateByUpdater(Updater<CmsConfig> updater);
}