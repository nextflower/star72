package com.star72.cmsmain.core.dao.impl;

import org.springframework.stereotype.Repository;

import com.star72.cmsmain.common.hibernate3.HibernateBaseDao;
import com.star72.cmsmain.core.dao.CmsUserExtDao;
import com.star72.cmsmain.core.entity.CmsUserExt;

@Repository
public class CmsUserExtDaoImpl extends HibernateBaseDao<CmsUserExt, Integer> implements CmsUserExtDao {
	public CmsUserExt findById(Integer id) {
		CmsUserExt entity = get(id);
		return entity;
	}

	public CmsUserExt save(CmsUserExt bean) {
		getSession().save(bean);
		return bean;
	}
	
	@Override
	protected Class<CmsUserExt> getEntityClass() {
		return CmsUserExt.class;
	}
}