package com.star72.common.utils.http;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.cookie.Cookie;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.star72.common.utils.MapUtils;
import com.star72.common.utils.RandomUtil;


/**
 * @author larry
 * 
 */
public class EmulateLoginBaidu {

	static crifanLib crl;
	
	static String CONTENT = null;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		crl = new crifanLib();
		
		EmulateLoginBaiduUsingJava("于卌", "kk4282864");
		
	}

	/**
	 * 
	 * @param username 用户名 
	 * @param password 密码 
	 */
	public static void EmulateLoginBaiduUsingJava(String username, String password) {

		//step 1
		boolean bGotCookieBaiduid = getCookidBaiduid();
		if(!bGotCookieBaiduid) {
			return;
		}
		
		
		//step 2
		String strTokenValue = getLoginToken();
		if(StringUtils.isBlank(strTokenValue)) {
			return;
		}
		
		//3 设置登陆信息
		List<NameValuePair> postDict = setPostParameters(username, password, strTokenValue);
		
		//4 设置头信息
		List<NameValuePair> headerDict = getHeaders();
		
		String baiduMainLoginUrl = "https://passport.baidu.com/v2/api/?login";
		crl.getUrlRespHtml(baiduMainLoginUrl, headerDict, postDict);
		
		//5 验证登陆是否成功
		boolean flag = validateLoginSuccess();
        
		if(flag) {
			
			//从http://tieba.baidu.com/f/like/mylike读取列表
			Map<String, String> map = getTiebaListMap();
	        
			for(int i=0; i<3; i++) {
				Set<String> set = new HashSet<String>();
				while(set.size() < map.size()) {
					String randomKey = MapUtils.getRandomKey(map);
					if(!set.contains(randomKey)) {
						set.add(randomKey);
						enterToTieba(randomKey, map.get(randomKey));
			        	try {
			        		//休眠时间
							Thread.sleep((RandomUtil.randomInt(88, 100)) * 1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
			
		}
        
		return ;
	}

	private static Map<String, String> getTiebaListMap() {
		Map<String, String> map = new HashMap<String, String>();
		
		String html = crl.getUrlRespHtml("http://tieba.baidu.com/f/like/mylike");
		Document doc = Jsoup.parse(html);
		
		//j_pagebar
		Element pageE = doc.getElementById("j_pagebar");
		
		Set<String> pageSet = new HashSet<String>();
		pageSet.add("http://tieba.baidu.com/f/like/mylike");
		
		Elements pageAEs = pageE.getElementsByTag("a");
		if(pageAEs != null && pageAEs.size() > 0) {
			for(Element e : pageAEs) {
				pageSet.add("http://tieba.baidu.com" + e.attr("href"));
			}
		}
		
		for(String pageUrl : pageSet) {
			String pageHtml = crl.getUrlRespHtml(pageUrl);
			Document pageDoc = Jsoup.parse(pageHtml);
			Elements tableEs = pageDoc.getElementsByTag("table");
		    if(tableEs != null && tableEs.size() > 0) {
		    	Element tableE = tableEs.get(0);
		    	Elements trEs = tableE.getElementsByTag("tr");
		    	for(int i=0; i<trEs.size(); i++) {
		    		if(i > 0) {
		    			Element trE = trEs.get(i);
		    			Elements tdEs = trE.getElementsByTag("td");
		    			if(tdEs != null && tdEs.size() == 4) {
		    				Element tdE = tdEs.get(0);
		    				Elements aEs = tdE.getElementsByTag("a");
		    				if(aEs != null && aEs.size() == 1) {
		    					Element aE = aEs.get(0);
		    					String title = aE.attr("title");
		    					String href = aE.attr("href");
		    					map.put(title, href);
		    				}
		    			}
		    		}
		    	}
		    }
		}
		return map;
	}
	
	private static void enterToTiezi(String url, String biebaTitle, String tieziTitle) {
		
		String kw = biebaTitle;
		String content = CONTENT == null ? getRandomContent() : CONTENT;
		String fid = null;
		String tbs = null;
		String tid = FilenameUtils.getBaseName(url);
		
		System.out.println(biebaTitle + "---" + tieziTitle + "---" + content + "___" + new Date());
		
		Pattern tbsP = Pattern.compile("\"tbs\"  : \"(\\w{10,40}+)\"");
		Pattern fidP = Pattern.compile("fid: '(\\d{1,20})'");
		
		String html = crl.getUrlRespHtml(url);
		
		Matcher tbsMatcher = tbsP.matcher(html);
		Matcher fidMatcher = fidP.matcher(html);
		
		
		//"tbs"  : "a88d9a0dd913895e1419421210"
		if(tbsMatcher.find()) {
			if(tbsMatcher.groupCount()>0) {
				tbs = tbsMatcher.group(1);
			}
		}
		
		//fid: '173802',
		if(fidMatcher.find()) {
			if(fidMatcher.groupCount()>0) {
				fid = fidMatcher.group(1);
			}
		}
		
		if(StringUtils.isNotBlank(kw) && StringUtils.isNotBlank(fid) && StringUtils.isNotBlank(tbs) && StringUtils.isNotBlank(tid)) {
			
			List<NameValuePair> postDict = new ArrayList<NameValuePair>();
			postDict.add(new BasicNameValuePair("__type__", "reply"));
			postDict.add(new BasicNameValuePair("content", content));
			postDict.add(new BasicNameValuePair("ie", "utf-8"));
			postDict.add(new BasicNameValuePair("kw", kw));
			postDict.add(new BasicNameValuePair("fid", fid));
			postDict.add(new BasicNameValuePair("tbs", tbs));
			postDict.add(new BasicNameValuePair("tid", tid));
			
//			List<NameValuePair> headerDict = new ArrayList<NameValuePair>();
//			headerDict.add(new BasicNameValuePair("Accept", "application/json, text/javascript, */*; q=0.01"));
//			headerDict.add(new BasicNameValuePair("Accept-Encoding", "gzip, deflate"));
//			headerDict.add(new BasicNameValuePair("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3"));
//			headerDict.add(new BasicNameValuePair("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.72 Safari/537.36"));
//			headerDict.add(new BasicNameValuePair("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8"));
//			headerDict.add(new BasicNameValuePair("Connection", "	keep-alive"));
//			headerDict.add(new BasicNameValuePair("Referer", url));
			
			String info = crl.getUrlRespHtml("http://tieba.baidu.com/f/commit/post/add", getHeaders(), postDict);
			
		}
		
	}

	private static void enterToTieba(String title, String href) {
		
		String url = "http://tieba.baidu.com";
		String html = crl.getUrlRespHtml(url + href);
		Document doc = Jsoup.parse(html);
		Element thread_list = doc.getElementById("thread_list");
		if(thread_list == null) {
			return;
		}
		
		Elements liEs = thread_list.getElementsByClass("j_thread_list");
		
		if(liEs == null) {
			return;
		}
		
		Pattern numberP = Pattern.compile("\\d+");
		
		//threadlist_li_right
		Elements threadlist_li_leftEs = thread_list.getElementsByClass("threadlist_li_left");
		if(threadlist_li_leftEs != null) {
			for(Element threadlist_li_leftE : threadlist_li_leftEs) {
				String text = threadlist_li_leftE.text();
				Matcher numberMatcher = numberP.matcher(text);
				if(numberMatcher.find()) {
					int repCount = Integer.parseInt(text);
					//TODO 仅针对前排
					if(validateFloorNum(repCount)) {
						Elements threadlist_li_rightEs = threadlist_li_leftE.parent().getElementsByClass("threadlist_li_right");
						if(threadlist_li_rightEs != null) {
							for(Element threadlist_li_rightE : threadlist_li_rightEs) {
								//j_th_tit
								Elements biaotiAEs = threadlist_li_rightE.getElementsByClass("j_th_tit");
								if(biaotiAEs != null) {
									for(Element e : biaotiAEs) {
										String tagName = e.tagName();
										if("a".equalsIgnoreCase(tagName)) {
											String tieziHref = e.attr("href");
											enterToTiezi(url + tieziHref, title, e.attr("title"));
											return;
										}
											
									}
								}
							}
						}
					}
				}
				
			}
		}
		
	}

	/**
	 * 验证floor层数
	 * @param repCount
	 * @return
	 */
	private static boolean validateFloorNum(Integer floorNum) {
		if(floorNum == null || floorNum < 0) {
			return false;
		}
		
		if(floorNum > 1 && floorNum < 10) {
			return true;
		}
		
		return false;
	}

	/**
	 * 设置登陆信息
	 * @param username
	 * @param password
	 * @param strTokenValue
	 * @return
	 */
	private static List<NameValuePair> setPostParameters(String username,
			String password, String strTokenValue) {
		/*
		 * postData = {
		    'username' : username,
		    'password' : password,
		    'u' : 'https://passport.baidu.com/',
		    'tpl' : 'pp',
		    'token' : tokenVal,
		    'staticpage' : 'https://passport.baidu.com/static/passpc-account/html/v3Jump.html',
		    'isPhone' : 'false',
		    'charset' : 'UTF-8',
		    'callback' : 'parent.bd__pcbs__ra48vi'
		    };
		    
		    headerData = {
		    	'Accept' : 'text/html,application/xhtml+xml,application/xml;q=0.9,*//*;q=0.8',
		    	'Accept-Encoding' : 'gzip,deflate,sdch',
		    	'Accept-Language' : 'zh-CN,zh;q=0.8',
		    	'User-Agent' : 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.72 Safari/537.36',
		    	'Content-Type' : 'application/x-www-form-urlencoded',
		    };
		    
		 */
		List<NameValuePair> postDict = new ArrayList<NameValuePair>();
		
		
		postDict.add(new BasicNameValuePair("username", username));
		postDict.add(new BasicNameValuePair("password", password));
		postDict.add(new BasicNameValuePair("u", "https://passport.baidu.com/"));
		postDict.add(new BasicNameValuePair("tpl", "mn"));
		postDict.add(new BasicNameValuePair("token", strTokenValue));
		postDict.add(new BasicNameValuePair("staticpage", "https://passport.baidu.com/static/passpc-account/html/v3Jump.html"));
		postDict.add(new BasicNameValuePair("isPhone", "false"));
		postDict.add(new BasicNameValuePair("charset", "UTF-8"));
		postDict.add(new BasicNameValuePair("callback", "parent.bd__pcbs__ra48vi"));
		return postDict;
	}

	private static boolean validateLoginSuccess() {
		boolean flag = false;
		HashMap<String, Boolean> cookieNameDict = new HashMap<String, Boolean>();     
        cookieNameDict.put("BDUSS", false);
        cookieNameDict.put("PTOKEN", false);
        cookieNameDict.put("STOKEN", false);
        
        List<Cookie> curCookieList = crl.getCurCookieList();
        for(String objCookieName : cookieNameDict.keySet()) {
            for(Cookie ck: curCookieList) {
            	if(objCookieName.equalsIgnoreCase(ck.getName())) {
            		cookieNameDict.put(objCookieName, true);
            	}
            }
        }
        boolean bAllCookiesFound = true;
        for (Boolean  objFoundCurCookie : cookieNameDict.values()) {
        	bAllCookiesFound = bAllCookiesFound && objFoundCurCookie; 
        }

        if (bAllCookiesFound) {
        	//System.out.println("成功模拟登陆百度首页！" );
        	return true;
        } else {
        	System.out.println("登陆失败！" );
        }
        
        return flag;
	}

	/**
	 * 设置头信息
	 * */
	private static List<NameValuePair> getHeaders() {
		List<NameValuePair> headerDict = new ArrayList<NameValuePair>();
		headerDict.add(new BasicNameValuePair("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"));
		headerDict.add(new BasicNameValuePair("Accept-Encoding", "gzip,deflate,sdch"));
		headerDict.add(new BasicNameValuePair("Accept-Language", "zh-CN,zh;q=0.8"));
		headerDict.add(new BasicNameValuePair("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.72 Safari/537.36"));
		headerDict.add(new BasicNameValuePair("Content-Type", "application/x-www-form-urlencoded"));
		headerDict.add(new BasicNameValuePair("Connection", "	keep-alive"));
		return headerDict;
	}

	/**
	 * 判断是否可以获取cookie BAIDUID
	 * @return
	 */
	private static boolean getCookidBaiduid() {
		List<Cookie> curCookieList;
		boolean bGotCookieBaiduid = false;
		String strBaiduUrl = "http://www.baidu.com/";
		
		//HttpResponse baiduResp = crl.getUrlResponse(strBaiduUrl);
		crl.getUrlResponse(strBaiduUrl);

		curCookieList = crl.getCurCookieStore().getCookies();
		
		//crl.dbgPrintCookies(curCookieList, strBaiduUrl);
		
		for (Cookie ck : curCookieList) {
			String cookieName = ck.getName();
			if (cookieName.equals("BAIDUID")) {
				bGotCookieBaiduid = true;
			}
		}
		
		if (bGotCookieBaiduid) {
			//System.out.println("正确：已找到cookie BAIDUID");
		} else {
			System.out.println("错误：没有找到cookie BAIDUID ！");
		}
		
		return bGotCookieBaiduid;
	}

	
	/**
	 * 步骤2：提取login_token
	 * @param bGotCookieBaiduid
	 * @return
	 */
	private static String getLoginToken() {
		String strTokenValue = null;
		List<Cookie> curCookieList;
		// https://passport.baidu.com/v2/api/?getapi&class=login&tpl=mn&tangram=true
		String getapiUrl = "https://passport.baidu.com/v2/api/?getapi&class=login&tpl=mn&tangram=true";
		String getApiRespHtml = crl.getUrlRespHtml(getapiUrl);

		curCookieList = crl.getCurCookieStore().getCookies();
		
		//crl.dbgPrintCookies(curCookieList, getapiUrl);
		
		// bdPass.api.params.login_token='3cf421493884e0fe9080593d05f4744f';
		Pattern tokenValP = Pattern
				.compile("bdPass\\.api\\.params\\.login_token='(\\w{0,200}+)';");
		Matcher tokenValMatcher = tokenValP.matcher(getApiRespHtml);
		
		boolean foundTokenValue = tokenValMatcher.find();
		if (foundTokenValue) {
			if (tokenValMatcher.groupCount() > 0) {
				strTokenValue = tokenValMatcher.group(1);// 3cf421493884e0fe9080593d05f4744f
				//System.out.println("正确：找到 bdPass.api.params.login_token=" + strTokenValue);
			} else {
				System.out.println("错误：没找到bdPass.api.params.login_token !");
			}

		} else {
			System.out.println("错误：没找到bdPass.api.params.login_token !");
		}
		return strTokenValue;
	}
	
	
	public static String getRandomContent() {
		List<String> list = new ArrayList<String>();
		list.add("你有没有见过这么整齐的十五字啊。");
		list.add("我轻轻地来，正如我轻轻地走。");
		list.add("水帖美如花，养护靠大家！！");
		list.add("我只是来水点经验。");
		list.add("百善回帖为先。");
		list.add("算是前排么？");
		list.add("如果帖子沉了，不要怪我。");
		list.add("如果帖子火了，记得@我。");
		list.add("我唯一能做的就是让楼主的帖子在首页多停一秒。");
		list.add("长得帅（漂亮）的人都会在此楼留名。");
		list.add("混个脸熟。。。");
		list.add("楼主你好");
		list.add("回帖也是很有乐趣的");
		return list.get(RandomUtil.randomInt(0, list.size()));
	}
}
