package com.star72.cmsmain.cms.dao.assist;

import java.util.List;

import com.star72.cmsmain.cms.entity.assist.CmsAcquisitionHistory;
import com.star72.cmsmain.common.hibernate3.Updater;
import com.star72.cmsmain.common.page.Pagination;

public interface CmsAcquisitionHistoryDao {
	public List<CmsAcquisitionHistory> getList(Integer siteId,Integer acquId);

	public Pagination getPage(Integer siteId,Integer acquId, Integer pageNo, Integer pageSize);

	public CmsAcquisitionHistory findById(Integer id);

	public CmsAcquisitionHistory save(CmsAcquisitionHistory bean);

	public CmsAcquisitionHistory updateByUpdater(Updater<CmsAcquisitionHistory> updater);

	public CmsAcquisitionHistory deleteById(Integer id);
	
	public void deleteByAcquisition(Integer acquId);

	public Boolean checkExistByProperties(Boolean title, String value);
}