package com.star72.cmsmain.core.manager;

import com.star72.cmsmain.core.entity.CmsSite;
import com.star72.cmsmain.core.entity.CmsSiteCompany;

public interface CmsSiteCompanyMng {
	public CmsSiteCompany save(CmsSite site,CmsSiteCompany bean);

	public CmsSiteCompany update(CmsSiteCompany bean);
}