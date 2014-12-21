package com.star72.cmsmain.core.manager;

import com.star72.cmsmain.core.entity.CmsUser;
import com.star72.cmsmain.core.entity.CmsUserExt;

public interface CmsUserExtMng {
	public CmsUserExt save(CmsUserExt ext, CmsUser user);

	public CmsUserExt update(CmsUserExt ext, CmsUser user);
}