package com.star72.cmsmain.cms.dao.main;

import java.util.List;

import com.star72.cmsmain.cms.entity.main.CmsModel;
import com.star72.cmsmain.common.hibernate3.Updater;

public interface CmsModelDao {
	public List<CmsModel> getList(boolean containDisabled,Boolean hasContent);

	public CmsModel getDefModel();

	public CmsModel findById(Integer id);
	
	public CmsModel findByPath(String path);

	public CmsModel save(CmsModel bean);

	public CmsModel updateByUpdater(Updater<CmsModel> updater);

	public CmsModel deleteById(Integer id);
}