package com.star72.cmsmain.core.dao;

import java.util.List;

import com.star72.cmsmain.common.hibernate3.Updater;
import com.star72.cmsmain.core.entity.CmsGroup;

public interface CmsGroupDao {
	public List<CmsGroup> getList();

	public CmsGroup getRegDef();

	public CmsGroup findById(Integer id);

	public CmsGroup save(CmsGroup bean);

	public CmsGroup updateByUpdater(Updater<CmsGroup> updater);

	public CmsGroup deleteById(Integer id);
}