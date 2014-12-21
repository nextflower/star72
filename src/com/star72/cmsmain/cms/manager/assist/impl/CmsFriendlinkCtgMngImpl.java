package com.star72.cmsmain.cms.manager.assist.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.star72.cmsmain.cms.dao.assist.CmsFriendlinkCtgDao;
import com.star72.cmsmain.cms.entity.assist.CmsFriendlinkCtg;
import com.star72.cmsmain.cms.manager.assist.CmsFriendlinkCtgMng;
import com.star72.cmsmain.common.hibernate3.Updater;

@Service
@Transactional
public class CmsFriendlinkCtgMngImpl implements CmsFriendlinkCtgMng {
	@Transactional(readOnly = true)
	public List<CmsFriendlinkCtg> getList(Integer siteId) {
		return dao.getList(siteId);
	}

	public int countBySiteId(Integer siteId) {
		return dao.countBySiteId(siteId);
	}

	@Transactional(readOnly = true)
	public CmsFriendlinkCtg findById(Integer id) {
		CmsFriendlinkCtg entity = dao.findById(id);
		return entity;
	}

	public CmsFriendlinkCtg save(CmsFriendlinkCtg bean) {
		dao.save(bean);
		return bean;
	}

	public CmsFriendlinkCtg update(CmsFriendlinkCtg bean) {
		Updater<CmsFriendlinkCtg> updater = new Updater<CmsFriendlinkCtg>(bean);
		bean = dao.updateByUpdater(updater);
		return bean;
	}

	public void updateFriendlinkCtgs(Integer[] ids, String[] names,
			Integer[] priorities) {
		if (ids == null || ids.length == 0) {
			return;
		}
		CmsFriendlinkCtg ctg;
		for (int i = 0; i < ids.length; i++) {
			ctg = dao.findById(ids[i]);
			ctg.setName(names[i]);
			ctg.setPriority(priorities[i]);
		}
	}

	public CmsFriendlinkCtg deleteById(Integer id) {
		CmsFriendlinkCtg bean = dao.deleteById(id);
		return bean;
	}

	public CmsFriendlinkCtg[] deleteByIds(Integer[] ids) {
		CmsFriendlinkCtg[] beans = new CmsFriendlinkCtg[ids.length];
		for (int i = 0, len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	private CmsFriendlinkCtgDao dao;

	@Autowired
	public void setDao(CmsFriendlinkCtgDao dao) {
		this.dao = dao;
	}
}