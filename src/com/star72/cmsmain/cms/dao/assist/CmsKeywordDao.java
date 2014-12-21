package com.star72.cmsmain.cms.dao.assist;

import java.util.List;

import com.star72.cmsmain.cms.entity.assist.CmsKeyword;
import com.star72.cmsmain.common.hibernate3.Updater;

public interface CmsKeywordDao {
	public List<CmsKeyword> getList(Integer siteId, boolean onlyEnabled,
			boolean cacheable);

	public List<CmsKeyword> getListGlobal(boolean onlyEnabled, boolean cacheable);

	public CmsKeyword findById(Integer id);

	public CmsKeyword save(CmsKeyword bean);

	public CmsKeyword updateByUpdater(Updater<CmsKeyword> updater);

	public CmsKeyword deleteById(Integer id);
}