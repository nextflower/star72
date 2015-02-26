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
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.star72.cmsmain.common.web.RequestUtils;
import com.star72.cmsmain.core.entity.CmsSite;
import com.star72.cmsmain.core.web.util.CmsUtils;
import com.star72.cmsmain.core.web.util.FrontUtils;
import com.star72.cmsmain.core.web.util.URLHelper;
import com.star72.cmsmain.core.web.util.URLHelper.PageInfo;
import com.star72.common.utils.MapUtils;
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
	
	public static final Integer DEFAULT_PAGE_SIZE = 20;
	
	private EPSSolrServerForCommon server = null;
	private String defaultURL = null;
	
	public static final String SEARCH_CAT_RESULT = "tpl.searchCatResult";
	public static final String SEARCH_CHAODAI_RESULT = "tpl.searchChaodaiResult";
	public static final String SEARCH_ID_RESULT = "tpl.searchIdResult";
	public static final String SEARCH_KEYWORD_RESULT = "tpl.searchKeywordResult";
	public static final String SEARCH_AUTHOR_RESULT = "tpl.searchAuthorResult";
	public static final String SEARCH_SOURCE_RESULT = "tpl.searchSourceResult";
	public static final String NAVI_CAT = "tpl.naviCat";
	public static final String NAVI_CHAODAI = "tpl.naviChaodai";
	public static final String NAVI_AUTHOR = "tpl.naviAuthor";
	public static final String NAVI_SOURCE = "tpl.naviSource";
	public static final String THANK = "tpl.thank";
	
	@RequestMapping(value = "/wenxian/thank.jhtml")
	public String thank(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		CmsSite site = CmsUtils.getSite(request);
		
		model.putAll(RequestUtils.getQueryParams(request));
		FrontUtils.frontData(request, model, site);
		
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_SPECIAL, THANK);
	}
	
	@RequestMapping(value = "/wenxian/navi/cat.jhtml")
	public String navigationCat(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		CmsSite site = CmsUtils.getSite(request);
		
		model.putAll(RequestUtils.getQueryParams(request));
		FrontUtils.frontData(request, model, site);
		
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_SPECIAL, NAVI_CAT);
	}
	
	@RequestMapping(value = "/wenxian/navi/chaodai.jhtml")
	public String navigationChaodai(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		CmsSite site = CmsUtils.getSite(request);
		server.setSolrURL(site.getSolrPath());
		
		SolrItem item = new SolrStringItem("*:*");
		SolrSearchCondition condition = new SolrSearchCondition(item);
		condition.openFacet("CHAODAI");
		
		SolrResult result = server.query(condition);
		Map<String, Map<String, Long>> facetMap = result.getFacetMap();
		if(facetMap != null) {
			Map<String, Long> map = facetMap.get("CHAODAI");
			if(map != null) {
				map = MapUtils.sortMapByValue(map, true);
				model.put("chaodaiMap", map);
			}
		}
		
		model.putAll(RequestUtils.getQueryParams(request));
		FrontUtils.frontData(request, model, site);
		
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_SPECIAL, NAVI_CHAODAI);
	}
	
	@RequestMapping(value = "/wenxian/navi/source.jhtml")
	public String navigationSource(Integer count, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		int pageSize = 360;
		if(count == null || count < 1) {
			count = 1;
		}
		
		CmsSite site = CmsUtils.getSite(request);
		server.setSolrURL(site.getSolrPath());
		SolrItem item = new SolrStringItem("*:*");
		SolrSearchCondition condition = new SolrSearchCondition(item);
		condition.openFacet("SOURCE");
		condition.setFacetLimit(30000);
		SolrResult result = server.query(condition);
		Map<String, Map<String, Long>> facetMap = result.getFacetMap();
		Integer total = 0;
		if(facetMap != null) {
			Map<String, Long> map = facetMap.get("SOURCE");
			if(map != null) {
				total = map.size() / pageSize + 1;
				map = MapUtils.sortMapByValue(map, true);
				
				List<String> list = new ArrayList<String>();
				//分页
				int temp = 0;
				for(String key : map.keySet()) {
					if(temp >= (count - 1) * pageSize && temp < count * pageSize) {
						list.add(key);
					}
					temp++;
				}
				
				model.put("sourceList", list);
			}
		}
		
		model.put("total", total);
		model.put("count", count);
		
		model.putAll(RequestUtils.getQueryParams(request));
		FrontUtils.frontData(request, model, site);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_SPECIAL, NAVI_SOURCE);
	}
	
	@RequestMapping(value = "/wenxian/navi/author.jhtml")
	public String navigationAuthor(String index, Integer count, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		CmsSite site = CmsUtils.getSite(request);
		server.setSolrURL(site.getSolrPath());
		
		SolrItem item = null;
		if(StringUtils.isBlank(index)) {
			item = new SolrStringItem("*:*");
		} else {
			item = new SolrCommonItem("AUTHOR_PIN", index);
		}
		
		if(count == null) {
			count = 600;
		}
		
		SolrSearchCondition condition = new SolrSearchCondition(item);
		condition.openFacet("AUTHOR");
		condition.setFacetLimit(count);
		condition.setFacetMinCount(1);
		
		SolrResult result = server.query(condition);
		Map<String, Map<String, Long>> facetMap = result.getFacetMap();
		if(facetMap != null) {
			Map<String, Long> map = facetMap.get("AUTHOR");
			if(map != null) {
				map = MapUtils.sortMapByValue(map, true);
				model.put("authorMap", map);
			}
		}
		
		model.putAll(RequestUtils.getQueryParams(request));
		FrontUtils.frontData(request, model, site);
		
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_SPECIAL, NAVI_AUTHOR);
	}
	
	@RequestMapping(value = "/wenxian/search/keyword/search*.jspx")
	public String wenxianSearch(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		CmsSite site = CmsUtils.getSite(request);
		
		server.setSolrURL(site.getSolrPath());
		
		String keyword = RequestUtils.getQueryParam(request, "keyword");
		String cat = RequestUtils.getQueryParam(request, "cat");
		String chaodai = RequestUtils.getQueryParam(request, "chaodai");
		
		int pageNo = URLHelper.getPageNo(request);//从1开始
		
		SolrItem item = null;
		if(StringUtils.isNotBlank(keyword)) {
			item = new SolrCommonItem("TITLE", keyword);
			item.addSiblingItem(new SolrCommonItem("CONTENT", keyword), SolrItem.Relation.OR);
			item.addSiblingItem(new SolrCommonItem("SOURCE", keyword), SolrItem.Relation.OR);
			item.addSiblingItem(new SolrCommonItem("SOURCE_FEN", keyword), SolrItem.Relation.OR);
		}
		
		if(StringUtils.isNotBlank(cat)) {
			if(item == null) {
				item = new SolrCommonItem("CAT", cat);
			} else {
				SolrItem catItem = new SolrCommonItem("CAT", cat);
				catItem.addSiblingItem(item, SolrItem.Relation.AND);
				item = catItem;
//				item.addSiblingItem(new SolrCommonItem("CAT", cat), SolrItem.Relation.AND);
			}
		}
		
		SolrSearchCondition con2 = null;
		if(item == null) {
			con2 = new SolrSearchCondition(new SolrStringItem("*:*"));
		} else {
			con2 = new SolrSearchCondition(item);
		}
		con2.openFacet("CHAODAI");
		con2.setFacetMinCount(1);
		SolrResult result2 = server.query(con2);
		Map<String, Map<String, Long>> facetMap = result2.getFacetMap();
		if(facetMap != null) {
			Map<String, Long> map = facetMap.get("CHAODAI");
			if(map != null && map.size() > 0) {
				map = MapUtils.sortMapByValue(map, true);
				model.put("chaodaiMap", map);
			}
		}
		
		if(StringUtils.isNotBlank(chaodai)) {
			if(item == null) {
				item = new SolrCommonItem("CHAODAI", chaodai);
			} else {
				SolrItem temp = new SolrCommonItem("CHAODAI", chaodai);
				temp.addSiblingItem(item, SolrItem.Relation.AND);
				item = temp;
			}
		}
		
		if(item == null) {
			item = new SolrStringItem("*:*");
		}
		
		SolrSearchCondition condition = new SolrSearchCondition(item, null, pageNo-1, DEFAULT_PAGE_SIZE);//页码需要从0开始
		
		condition.openHighlight("CONTENT", "TITLE");
		
		
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
		server.setSolrURL(site.getSolrPath());
		String requestURI = request.getRequestURI();
		
		String id = FilenameUtils.getBaseName(requestURI);
		if(StringUtils.isBlank(id)) {
			
		}
		SolrItem item = new SolrCommonItem("ID", id);
		SolrSearchCondition condition = new SolrSearchCondition(item, null, 0, 10);
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
		
		server.setSolrURL(site.getSolrPath());
		
		int pageNo = URLHelper.getPageNo(request);//从1开始
		PageInfo info = URLHelper.getPageInfo(request);
		String hrefFormer = info.getHrefFormer();//易藏
		
		SolrItem item = new SolrCommonItem("CAT", hrefFormer);
		
		SolrSearchCondition condition = new SolrSearchCondition(item, null, pageNo-1, DEFAULT_PAGE_SIZE);//页码需要从0开始
		
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
	 * 针对朝代进行查询
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/wenxian/search/chaodai/*.jhtml")
	public String chaodaiSearch(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		CmsSite site = CmsUtils.getSite(request);
		
		server.setSolrURL(site.getSolrPath());
		
		int pageNo = URLHelper.getPageNo(request);//从1开始
		PageInfo info = URLHelper.getPageInfo(request);
		String hrefFormer = info.getHrefFormer();//易藏
		
		SolrItem item = new SolrCommonItem("CHAODAI", hrefFormer);
		
		SolrSearchCondition condition = new SolrSearchCondition(item, null, pageNo-1, DEFAULT_PAGE_SIZE);//页码需要从0开始
		
		SolrResult result = server.query(condition);
		
		
		List<WenxianBean> wenxianList = getResultList(result);
		
		model.put("result", result);
		model.put("list", wenxianList);
		model.put("chaodai", hrefFormer);
		
		model.putAll(RequestUtils.getQueryParams(request));
		FrontUtils.frontData(request, model, site);
		FrontUtils.frontPageData(request, model);
		
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_SPECIAL, SEARCH_CHAODAI_RESULT);
	}
	
	/**
	 * 针对作者进行查询
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/wenxian/search/author/*.jhtml")
	public String authorSearch(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		CmsSite site = CmsUtils.getSite(request);
		
		server.setSolrURL(site.getSolrPath());
		
		int pageNo = URLHelper.getPageNo(request);//从1开始
		PageInfo info = URLHelper.getPageInfo(request);
		String hrefFormer = info.getHrefFormer();//易藏
		
		SolrItem item = new SolrCommonItem("AUTHOR", hrefFormer);
		
		SolrSearchCondition condition = new SolrSearchCondition(item, null, pageNo-1, DEFAULT_PAGE_SIZE);//页码需要从0开始
		
		SolrResult result = server.query(condition);
		
		
		List<WenxianBean> wenxianList = getResultList(result);
		
		model.put("result", result);
		model.put("list", wenxianList);
		model.put("author", hrefFormer);
		
		model.putAll(RequestUtils.getQueryParams(request));
		FrontUtils.frontData(request, model, site);
		FrontUtils.frontPageData(request, model);
		
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_SPECIAL, SEARCH_AUTHOR_RESULT);
	}
	
	/**
	 * 针对书名进行查询
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/wenxian/search/source/*.jhtml")
	public String sourceSearch(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		
		CmsSite site = CmsUtils.getSite(request);
		
		server.setSolrURL(site.getSolrPath());
		
		int pageNo = URLHelper.getPageNo(request);//从1开始
		PageInfo info = URLHelper.getPageInfo(request);
		String hrefFormer = info.getHrefFormer();//易藏
		
		SolrItem item = new SolrCommonItem("SOURCE", hrefFormer);
		
		SolrSearchCondition condition = new SolrSearchCondition(item, null, pageNo-1, DEFAULT_PAGE_SIZE);//页码需要从0开始
		
		SolrResult result = server.query(condition);
		
		
		List<WenxianBean> wenxianList = getResultList(result);
		
		model.put("result", result);
		model.put("list", wenxianList);
		model.put("source", hrefFormer);
		
		model.putAll(RequestUtils.getQueryParams(request));
		FrontUtils.frontData(request, model, site);
		FrontUtils.frontPageData(request, model);
		
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_SPECIAL, SEARCH_SOURCE_RESULT);
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
		//System.out.println(hl.getClass());
		for(Map<String, Object> map : list) {
			List<String> cats = (List<String>) map.get("CAT");
			String id = (String) map.get("ID");
			String content = (String) map.get("CONTENT");
			String title = (String) map.get("TITLE");
			String source = (String) map.get("SOURCE");
			String author = (String) map.get("AUTHOR");
			String chaodai = (String) map.get("CHAODAI");
			Map<String, String> hl = (Map<String, String>) map.get(SolrResult.HIGH_LIGHT_KEY);
			String hlTitle = hl.get("TITLE");
			String hlContent = hl.get("CONTENT");
			WenxianBean bean = new WenxianBean();
			bean.setId(id);
			bean.setCats(cats);
			bean.setContent(content);
			bean.setTitle(title);
			bean.setSource(source);
			wenxianList.add(bean);
			bean.setHlContent(hlContent);
			bean.setHlTitle(hlTitle);
			
			bean.setAuthor(author);
			bean.setChaodai(chaodai);
			
		}
		return wenxianList;
	}

	public void setServer(EPSSolrServerForCommon server) {
		this.server = server;
	}

	public EPSSolrServerForCommon getServer() {
		return server;
	}

	public void setDefaultURL(String defaultURL) {
		this.defaultURL = defaultURL;
	}

	public String getDefaultURL() {
		return defaultURL;
	}

}
