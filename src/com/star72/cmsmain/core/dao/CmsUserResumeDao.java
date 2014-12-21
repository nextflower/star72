package com.star72.cmsmain.core.dao;

import com.star72.cmsmain.common.hibernate3.Updater;
import com.star72.cmsmain.core.entity.CmsUserResume;

public interface CmsUserResumeDao {
	public CmsUserResume findById(Integer id);

	public CmsUserResume save(CmsUserResume bean);

	public CmsUserResume updateByUpdater(Updater<CmsUserResume> updater);
}