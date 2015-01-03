package com.star72.cmsmain.cms.action.front;

import static com.star72.cmsmain.cms.Constants.TPLDIR_SPECIAL;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.star72.cmsmain.common.web.RequestUtils;
import com.star72.cmsmain.core.entity.CmsSite;
import com.star72.cmsmain.core.web.util.CmsUtils;
import com.star72.cmsmain.core.web.util.FrontUtils;
import com.star72.cmsmain.core.web.util.URLHelper;
import com.star72.cmsmain.core.web.util.URLHelper.PageInfo;
import com.star72.search.solrmodule.condition.SolrCommonItem;
import com.star72.search.solrmodule.condition.SolrItem;
import com.star72.search.solrmodule.condition.SolrSearchCondition;
import com.star72.search.solrmodule.condition.SolrStringItem;
import com.star72.search.solrmodule.page.SolrResult;
import com.star72.search.solrmodule.query.EPSSolrServerForCommon;
import com.star72.wenxian.entity.WenxianBean;
import com.sysc.remotesolr.service.SolrConstantFront;

/**
 * 专门用于solr的定制查询,和系统自带进行区分
 * 
 * @author larry
 *
 */
@Controller
public class StarSearchAct {
	
	public static final String SEARCH_CAT_RESULT = "tpl.searchCatResult";
	public static final String SEARCH_ID_RESULT = "tpl.searchIdResult";
	public static final String SEARCH_KEYWORD_RESULT = "tpl.searchKeywordResult";
	
	@RequestMapping(value = "/wenxian/search/keyword/search*.jspx")
	public String wenxianSearch(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		CmsSite site = CmsUtils.getSite(request);
		EPSSolrServerForCommon server = new EPSSolrServerForCommon();
		server.setSolrURL(site.getSolrPath());
		
		String keyword = RequestUtils.getQueryParam(request, "keyword");
		String cat = RequestUtils.getQueryParam(request, "cat");
		
		int pageNo = URLHelper.getPageNo(request);//从1开始
		
		SolrItem item = null;
		if(StringUtils.isBlank(keyword)) {
			item = new SolrStringItem("*:*");
		} else {
			item = new SolrCommonItem("TITLE", keyword);
			item.addSiblingItem(new SolrCommonItem("CONTENT", keyword), SolrItem.Relation.OR);
		}
		
		if(StringUtils.isNotBlank(cat)) {
			item.addSiblingItem(new SolrCommonItem("CAT", cat), SolrItem.Relation.AND);
		}
		
		SolrSearchCondition condition = new SolrSearchCondition(item, null, pageNo-1, 20);//页码需要从0开始
		
		SolrResult result = server.query(condition);
		
		List<WenxianBean> wenxianList = getResultList(result);
		
		model.put("result", result);
		model.put("list", wenxianList);
		
		model.putAll(RequestUtils.getQueryParams(request));
		FrontUtils.frontData(request, model, site);
		FrontUtils.frontPageData(request, model);
		
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_SPECIAL, SEARCH_KEYWORD_RESULT);
	}
	
	@RequestMapping(value = "/wenxian/search/id/*.jhtml")
	public String idSearch(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		EPSSolrServerForCommon server = new EPSSolrServerForCommon();
		server.setSolrURL(site.getSolrPath());
		String requestURI = request.getRequestURI();
		
		String id = FilenameUtils.getBaseName(requestURI);
		
		SolrItem item = new SolrCommonItem("ID", id);
		SolrSearchCondition condition = new SolrSearchCondition(item, null, 0, 20);
		SolrResult result = server.query(condition);
		List<WenxianBean> wenxianList = getResultList(result);
		
		model.put("result", result);
		if(wenxianList.size() == 1) {
			model.put("bean", wenxianList.get(0));
		}
		
		model.putAll(RequestUtils.getQueryParams(request));
		FrontUtils.frontData(request, model, site);
		
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_SPECIAL, SEARCH_ID_RESULT);
	}
	
	/**
	 * 针对分类进行查询
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/wenxian/search/cat/*.jhtml")
	public String catSearch(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		CmsSite site = CmsUtils.getSite(request);
		
		EPSSolrServerForCommon server = new EPSSolrServerForCommon();
		server.setSolrURL(site.getSolrPath());
		
		int pageNo = URLHelper.getPageNo(request);//从1开始
		PageInfo info = URLHelper.getPageInfo(request);
		String hrefFormer = info.getHrefFormer();//易藏
		
		SolrItem item = new SolrCommonItem("CAT", hrefFormer);
		
		SolrSearchCondition condition = new SolrSearchCondition(item, null, pageNo-1, 20);//页码需要从0开始
		
		SolrResult result = server.query(condition);
		
		
		List<WenxianBean> wenxianList = getResultList(result);
		
		model.put("result", result);
		model.put("list", wenxianList);
		model.put("cat", hrefFormer);
		
		model.putAll(RequestUtils.getQueryParams(request));
		FrontUtils.frontData(request, model, site);
		FrontUtils.frontPageData(request, model);
		
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_SPECIAL, SEARCH_CAT_RESULT);
	}

	/**
	 * 将查询结果封装为实体对象
	 * 
	 * @param result
	 * @return
	 */
	private List<WenxianBean> getResultList(SolrResult result) {
		Map<String, Object> resultMap = result.getResultMap();
		List<Map<String, Object>> list = (List<Map<String, Object>>) resultMap.get(SolrResult.RESULT_LIST);
		List<WenxianBean> wenxianList = new ArrayList<WenxianBean>();
		if(list == null) {
			return wenxianList;
		}
		for(Map<String, Object> map : list) {
			List<String> cats = (List<String>) map.get("CAT");
			String id = (String) map.get("ID");
			String content = (String) map.get("CONTENT");
			String title = (String) map.get("TITLE");
			String source = (String) map.get("SOURCE");
			WenxianBean bean = new WenxianBean();
			bean.setId(id);
			bean.setCats(cats);
			bean.setContent(content);
			bean.setTitle(title);
			bean.setSource(source);
			wenxianList.add(bean);
		}
		return wenxianList;
	}

}
