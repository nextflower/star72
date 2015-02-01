package com.star72.cmsmain.cms.lucene.handler;

import org.apache.commons.lang.StringUtils;

import com.star72.cmsmain.cms.entity.main.Channel;
import com.star72.cmsmain.cms.entity.main.Content;
import com.star72.cmsmain.core.entity.CmsSite;
import com.star72.common.utils.StarStringUtils;
import com.star72.search.solrmodule.write.EpsSolrDocument;

/**
 * 
 * @author larry
 *
 */
public class ContentSolrHandler4Wenxian extends ContentSolrHandlerAbstract{
	
	public static final String FIELD_ID = "ID";//id
	public static final String FIELD_CAT = "CAT";//common1
	public static final String FIELD_AUTHOR = "AUTHOR";//author
	public static final String FIELD_AUTHOR_PIN = "AUTHOR_PIN";//common2
	public static final String FIELD_CHAODAI = "CHAODAI";//common3
	public static final String FIELD_SOURCE = "SOURCE";//shortTitle
	public static final String FIELD_SOURCE_FEN = "SOURCE_FEN";//shortTitle
	public static final String FIELD_SOURCE_ID = "SOURCE_ID";//common4
	public static final String FIELD_ROOT_CAT = "ROOT_CAT";//channel.name
	public static final String FIELD_ROOT_CAT_ID = "ROOT_CAT_ID";//channel.id
	public static final String FIELD_CAT_STR = "CAT_STR";//
	public static final String FIELD_SITE = "SITE_ID";//site.id
	public static final String FIELD_TITLE = "TITLE";//title
	public static final String FIELD_CONTENT = "CONTENT";//title
	
	
	
	private Integer siteId = null;

	@Override
	public EpsSolrDocument createSolrDocument(Content content) {
		EpsSolrDocument doc = new EpsSolrDocument();
		Channel channel = content.getChannel();
		CmsSite site = content.getSite();
		String catStr = content.getCommon1();
		doc.addField(FIELD_ID, content.getId());//1
		doc.addField(FIELD_AUTHOR, content.getAuthor());//2
		doc.addField(FIELD_AUTHOR_PIN, content.getCommon2());//3
		doc.addField(FIELD_CHAODAI, content.getCommon3());//4
		doc.addField(FIELD_SOURCE, content.getShortTitle());//5
		doc.addField(FIELD_SOURCE_FEN, content.getShortTitle());//6
		doc.addField(FIELD_SOURCE_ID, content.getCommon4());//7
		doc.addField(FIELD_SITE, site.getId());//8
		doc.addField(FIELD_ROOT_CAT_ID, channel.getId());//9
		doc.addField(FIELD_TITLE, content.getTitle());//10
		doc.addField(FIELD_CONTENT, StarStringUtils.deleteAllHTMLTag(content.getTxt()));//11
		doc.addField(FIELD_ROOT_CAT, channel.getName());//12
		doc.addField(FIELD_CAT_STR, catStr);//13
		
		doc.addField(FIELD_CAT, channel.getName());//13
		if(StringUtils.isNotBlank(catStr)) {
			String[] arr = catStr.split(",");
			for(String s : arr) {
				doc.addField(FIELD_CAT, s);
			}
		}
		
		return doc;
	}

	@Override
	public Integer getSiteId() {
		return this.siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

}
