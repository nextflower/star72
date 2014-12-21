package com.star72.cmsmain.core.manager;

import com.star72.cmsmain.core.entity.CmsUser;
import com.star72.cmsmain.core.entity.CmsUserResume;

public interface CmsUserResumeMng {
	public CmsUserResume save(CmsUserResume ext, CmsUser user);

	public CmsUserResume update(CmsUserResume ext, CmsUser user);
}