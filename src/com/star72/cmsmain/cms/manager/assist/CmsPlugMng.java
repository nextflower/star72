package com.star72.cmsmain.cms.manager.assist;

import java.util.List;

import com.star72.cmsmain.common.page.Pagination;
import com.star72.cmsmain.cms.entity.assist.CmsPlug;

public interface CmsPlugMng {
	public Pagination getPage(int pageNo, int pageSize);
	
	public List<CmsPlug> getList(String author,Boolean used);

	public CmsPlug findById(Integer id);
	
	public CmsPlug findByPath(String plugPath);

	public CmsPlug save(CmsPlug bean);

	public CmsPlug update(CmsPlug bean);

	public CmsPlug deleteById(Integer id);
	
	public CmsPlug[] deleteByIds(Integer[] ids);
}