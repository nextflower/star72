package com.star72.cmsmain.cms.dao.main.impl;

import org.springframework.stereotype.Repository;

import com.star72.cmsmain.cms.dao.main.ContentExtDao;
import com.star72.cmsmain.cms.entity.main.ContentExt;
import com.star72.cmsmain.common.hibernate3.HibernateBaseDao;

@Repository
public class ContentExtDaoImpl extends HibernateBaseDao<ContentExt, Integer>
		implements ContentExtDao {
	public ContentExt findById(Integer id) {
		ContentExt entity = get(id);
		return entity;
	}

	public ContentExt save(ContentExt bean) {
		getSession().save(bean);
		return bean;
	}

	@Override
	protected Class<ContentExt> getEntityClass() {
		return ContentExt.class;
	}
}