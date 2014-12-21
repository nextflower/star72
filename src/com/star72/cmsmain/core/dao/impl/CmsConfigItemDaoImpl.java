package com.star72.cmsmain.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.star72.cmsmain.common.hibernate3.HibernateBaseDao;
import com.star72.cmsmain.core.dao.CmsConfigItemDao;
import com.star72.cmsmain.core.entity.CmsConfigItem;

@Repository
public class CmsConfigItemDaoImpl extends
		HibernateBaseDao<CmsConfigItem, Integer> implements CmsConfigItemDao {
	@SuppressWarnings("unchecked")
	public List<CmsConfigItem> getList(Integer configId,Integer category) {
		StringBuilder sb = new StringBuilder(
				"from CmsConfigItem bean where bean.config.id=? and bean.category=?");
		sb.append(" order by bean.priority asc,bean.id asc");
		return find(sb.toString(),configId,category);
	}

	public CmsConfigItem findById(Integer id) {
		CmsConfigItem entity = get(id);
		return entity;
	}

	public CmsConfigItem save(CmsConfigItem bean) {
		getSession().save(bean);
		return bean;
	}

	public CmsConfigItem deleteById(Integer id) {
		CmsConfigItem entity = super.get(id);
		if (entity != null) {
			getSession().delete(entity);
		}
		return entity;
	}

	@Override
	protected Class<CmsConfigItem> getEntityClass() {
		return CmsConfigItem.class;
	}
}