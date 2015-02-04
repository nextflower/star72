package com.star72.cmsmain.cms.lucene;

import java.util.Date;
import java.util.List;

import org.hibernate.CacheMode;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.star72.cmsmain.cms.entity.main.Content;
import com.star72.cmsmain.cms.entity.main.ContentCheck;
import com.star72.cmsmain.cms.lucene.handler.ContentSolrHandler;
import com.star72.cmsmain.common.hibernate3.Finder;
import com.star72.cmsmain.common.hibernate3.HibernateBaseDao;
import com.star72.cmsmain.core.entity.CmsSite;

@Repository
public class SolrContentDaoImpl extends HibernateBaseDao<Content, Integer>
		implements SolrContentDao {
	
	@Autowired
	protected List<ContentSolrHandler> handlerList;

	@Override
	protected Class<Content> getEntityClass() {
		return Content.class;
	}

	@Override
	public Integer index(Integer siteId, Integer channelId, Date startDate, Date endDate, Integer startId, Integer endId) {
		Finder f = Finder.create("select bean from Content bean");
		if (channelId != null) {
			f.append(" join bean.channel channel, Channel parent");
			f.append(" where channel.lft between parent.lft and parent.rgt");
			f.append(" and channel.site.id=parent.site.id");
			f.append(" and parent.id=:parentId");
			f.setParam("parentId", channelId);
		} else if (siteId != null) {
			f.append(" where bean.site.id=:siteId");
			f.setParam("siteId", siteId);
		} else {
			f.append(" where 1=1");
		}
		
		if (startDate != null) {
			f.append(" and bean.contentExt.releaseDate >= :startDate");
			f.setParam("startDate", startDate);
		}
		if (endDate != null) {
			f.append(" and bean.contentExt.releaseDate <= :endDate");
			f.setParam("endDate", endDate);
		}
		
		if(startId != null) {
			f.append(" and bean.id >= :startId");
			f.setParam("startId", startId);
		}
		
		if(endId != null) {
			f.append(" and bean.id <= :endId");
			f.setParam("endId", endId);
		}
		
		f.append(" and bean.status=" + ContentCheck.CHECKED);
		f.append(" order by bean.id asc");
		Session session = getSession();
		ScrollableResults contents = f.createQuery(getSession()).setCacheMode(
				CacheMode.IGNORE).scroll(ScrollMode.FORWARD_ONLY);
		int count = 0;
		Content content = null;
		while (contents.next()) {
			content = (Content) contents.get(0);
			ContentSolrHandler handler = getHandler(content);
			if(handler != null) {
				handler.createIndex(content);
			}
			if (++count % 200 == 0) {
				session.clear();
			}
		}
		return null;
	}
	
	public ContentSolrHandler getHandler(Content content) {
		if(handlerList != null && content != null && content.getSite() != null) {
			CmsSite site = content.getSite();
			for(ContentSolrHandler handler : handlerList) {
				Integer siteId = handler.getSiteId();
				if(siteId != null && siteId.equals(site.getId())) {
					return handler;
				}
			}
		}
		return null;
	}

	@Override
	public void deleteIndex(Content content) {
		ContentSolrHandler handler = getHandler(content);
		if(handler != null) {
			handler.deleteIndex(content);
		}
	}

	@Override
	public void createIndex(Content content) {
		ContentSolrHandler handler = getHandler(content);
		if(handler != null) {
			handler.createIndex(content);
		}
	}

	@Override
	public void updateIndex(Content content) {
		ContentSolrHandler handler = getHandler(content);
		if(handler != null) {
			handler.updateIndex(content);
		}
	}
}
