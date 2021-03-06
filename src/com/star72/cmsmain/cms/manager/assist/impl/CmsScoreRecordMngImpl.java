package com.star72.cmsmain.cms.manager.assist.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.star72.cmsmain.common.hibernate3.Updater;
import com.star72.cmsmain.common.page.Pagination;
import com.star72.cmsmain.cms.dao.assist.CmsScoreRecordDao;
import com.star72.cmsmain.cms.entity.assist.CmsScoreItem;
import com.star72.cmsmain.cms.entity.assist.CmsScoreRecord;
import com.star72.cmsmain.cms.entity.main.Content;
import com.star72.cmsmain.cms.manager.assist.CmsScoreItemMng;
import com.star72.cmsmain.cms.manager.assist.CmsScoreRecordMng;
import com.star72.cmsmain.cms.manager.main.ContentMng;

@Service
@Transactional
public class CmsScoreRecordMngImpl implements CmsScoreRecordMng {
	@Transactional(readOnly = true)
	public Pagination getPage(int pageNo, int pageSize) {
		Pagination page = dao.getPage(pageNo, pageSize);
		return page;
	}
	
	@Transactional(readOnly = true)
	public Map<String,String> viewContent(Integer contentId){
		Map<String,String>itemCountMap=new HashMap<String, String>();
		List<CmsScoreRecord>records=dao.findListByContent(contentId);
		for(CmsScoreRecord r:records){
			itemCountMap.put(r.getItem().getId().toString(), r.getCount().toString());
		}
		return itemCountMap;
	}
	
	public int contentScore(Integer contentId,Integer itemId){
		Content c = contentMng.findById(contentId);
		CmsScoreItem item=scoreItemMng.findById(itemId);
		CmsScoreRecord scoreRecord=findByScoreItemContent(itemId, contentId);
		if (c == null||item==null) {
			return 0;
		}
		int count=1;
		if(scoreRecord!=null){
			count= scoreRecord.getCount() + 1;
			scoreRecord.setCount(count);
		}else{
			scoreRecord=new CmsScoreRecord();
			scoreRecord.setContent(c);
			scoreRecord.setCount(count);
			scoreRecord.setItem(item);
			save(scoreRecord);
		}
		c.setScore(c.getScore()+item.getScore());
		return count;
	}
	
	@Transactional(readOnly = true)
	public CmsScoreRecord findByScoreItemContent(Integer itemId,Integer contentId){
		return dao.findByScoreItemContent(itemId, contentId);
	}

	@Transactional(readOnly = true)
	public CmsScoreRecord findById(Integer id) {
		CmsScoreRecord entity = dao.findById(id);
		return entity;
	}

	public CmsScoreRecord save(CmsScoreRecord bean) {
		dao.save(bean);
		return bean;
	}

	public CmsScoreRecord update(CmsScoreRecord bean) {
		Updater<CmsScoreRecord> updater = new Updater<CmsScoreRecord>(bean);
		bean = dao.updateByUpdater(updater);
		return bean;
	}

	public CmsScoreRecord deleteById(Integer id) {
		CmsScoreRecord bean = dao.deleteById(id);
		return bean;
	}
	
	public CmsScoreRecord[] deleteByIds(Integer[] ids) {
		CmsScoreRecord[] beans = new CmsScoreRecord[ids.length];
		for (int i = 0,len = ids.length; i < len; i++) {
			beans[i] = deleteById(ids[i]);
		}
		return beans;
	}

	private CmsScoreRecordDao dao;
	@Autowired
	private ContentMng contentMng;
	@Autowired
	private CmsScoreItemMng scoreItemMng;

	@Autowired
	public void setDao(CmsScoreRecordDao dao) {
		this.dao = dao;
	}
}