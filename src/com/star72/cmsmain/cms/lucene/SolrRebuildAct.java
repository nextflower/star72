package com.star72.cmsmain.cms.lucene;



import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.star72.cmsmain.core.entity.CmsSite;
import com.star72.cmsmain.core.web.util.CmsUtils;


/**
 * solr索引重建处理类
 * 
 * @author larry
 *
 */
@Controller
public class SolrRebuildAct {

	private static final Logger log = LoggerFactory.getLogger(SolrRebuildAct.class);
	
	@RequiresPermissions("solr:rebuild")
	@RequestMapping(value = "/solr/rebuild.do")
	public void index(Integer channelId, Date startDate, Date endDate, Integer start, HttpServletRequest request, ModelMap model) {
		
		CmsSite site = CmsUtils.getSite(request);
		LuceneRebuildTask task = LuceneRebuildTask.getRebuildTask();
		
		if(task.isOnUse()) {
			return;
		} else {
			task.start();
			solrContentSvc.createIndex(site.getId(), channelId, startDate, endDate, start);
			task.clear();
		}
		
	}
	
	@Autowired
	private SolrContentSvc solrContentSvc;
	
}
