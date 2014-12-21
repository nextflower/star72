package com.star72.cmsmain.core.manager;

import java.util.Date;

import com.star72.cmsmain.core.entity.CmsConfig;
import com.star72.cmsmain.core.entity.CmsConfigAttr;
import com.star72.cmsmain.core.entity.MarkConfig;
import com.star72.cmsmain.core.entity.MemberConfig;

public interface CmsConfigMng {
	public CmsConfig get();

	public void updateCountCopyTime(Date d);

	public void updateCountClearTime(Date d);

	public CmsConfig update(CmsConfig bean);

	public MarkConfig updateMarkConfig(MarkConfig mark);

	public void updateMemberConfig(MemberConfig memberConfig);
	
	public void updateConfigAttr(CmsConfigAttr configAttr);
}