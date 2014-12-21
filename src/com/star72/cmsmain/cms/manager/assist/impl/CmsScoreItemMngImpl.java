package com.star72.cmsmain.cms.manager.assist.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.star72.cmsmain.common.hibernate3.Updater;
import com.star72.cmsmain.common.page.Pagination;
import com.star72.cmsmain.cms.dao.assist.CmsScoreItemDao;
import com.star72.cmsmain.cms.entity.assist.CmsScoreItem;
import com.star72.cmsmain.cms.manager.assist.CmsScoreItemMng;

@Service
@Transactional
public class CmsScoreItemMngImpl implements CmsScoreItemMng {
	@Transactional(readOnly = true)
	public Pagination getPage(Integer groupId,int pageNo, int pageSize) {
		Pagination page = dao.getPage(groupId,pageNo, pageSize);
		return page;
	}

	@Transactional(readOnly = true)
	public CmsScoreItem findById(Integer id) {
		CmsScoreItem entity = dao.findById(id);
		return entity;
	}

	public CmsScoreItem save(CmsScoreItem bean) {
		dao.save(bean);
		return bean;
	}

	public CmsScoreItem update(CmsScoreItem bean) {
		Updater<CmsScoreItem> updater = new Updater<CmsScoreItem>(bean);
		bean = dao.updateByUpdater(updater);
		return bean;
	}

	public CmsScoreItem deleteById(Integer id) {
		CmsScoreItem bean = dao.deleteById(id);
		return bean;
	}
	
	public CmsScoreItem[] deleteByIds(Integer[] ids) {
		CmsScoreItem[] beans = new CmsScoreItem[ids.length];
		for (int i = 0,len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	private CmsScoreItemDao dao;

	@Autowired
	public void setDao(CmsScoreItemDao dao) {
		this.dao = dao;
	}
}