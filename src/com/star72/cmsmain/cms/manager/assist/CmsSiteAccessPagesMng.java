package com.star72.cmsmain.cms.manager.assist;

import java.util.Date;

import com.star72.cmsmain.cms.entity.assist.CmsSiteAccessPages;
import com.star72.cmsmain.common.page.Pagination;

/**
 * @author Tom
 */
public interface CmsSiteAccessPagesMng {

	public CmsSiteAccessPages save(CmsSiteAccessPages access);
	
	public CmsSiteAccessPages update(CmsSiteAccessPages access);

	public CmsSiteAccessPages findAccessPage(String sessionId, Integer pageIndex);
	
	public Pagination findPages(Integer siteId,Integer orderBy,Integer pageNo,Integer pageSize);

	public void clearByDate(Date date);
}
