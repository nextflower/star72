package com.star72.cmsmain.cms.dao.assist;

import java.util.Date;
import java.util.List;

import com.star72.cmsmain.cms.entity.assist.CmsSiteAccessCount;
import com.star72.cmsmain.common.hibernate3.Updater;

/**
 * @author Tom
 */
public interface CmsSiteAccessCountDao {

	public List<Object[]> statisticVisitorCountByDate(Integer siteId,Date begin,Date end);
	
	public List<Object[]> statisticVisitorCountByYear(Integer siteId,Integer year);

	public CmsSiteAccessCount save(CmsSiteAccessCount count);

	public CmsSiteAccessCount updateByUpdater(Updater<CmsSiteAccessCount> updater);

}
