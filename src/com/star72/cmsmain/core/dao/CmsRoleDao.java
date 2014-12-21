package com.star72.cmsmain.core.dao;

import java.util.List;

import com.star72.cmsmain.common.hibernate3.Updater;
import com.star72.cmsmain.core.entity.CmsRole;

public interface CmsRoleDao {
	public List<CmsRole> getList();

	public CmsRole findById(Integer id);

	public CmsRole save(CmsRole bean);

	public CmsRole updateByUpdater(Updater<CmsRole> updater);

	public CmsRole deleteById(Integer id);
}