package com.star72.common.utils.http;

/**
 * [File]
 * EmulateLoginBaidu.java
 * 
 * [Function]
 * Use Java code to emulate login baidu
 * 
 * 【教程】模拟登陆百度之Java代码版
 * http://www.crifan.com/emulate_login_baidu_use_java_code
 * 
 * [Version]
 * v1.0, 2013-09-17
 * 
 * [Note]
 * 1. need add apache http lib:
 * 【已解决】Eclipse的java代码出错：The import org.apache cannot be resolved
 * http://www.crifan.com/java_eclipse_the_import_org_apache_cannot_be_resolved/
 * 2.need crifanLib.java
 * http://code.google.com/p/crifanlib/source/browse/trunk/java/crifanLib.java
 * 
 * [History]
 * [v1.0]
 * 1. initial version, finally successfully emulate login baidu using java code.
 */

//import java.io.IOException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.GregorianCalendar;
import java.util.HashMap;
//import java.util.Hashtable;
import java.util.List;
//import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.cookie.Cookie;
//import org.apache.http.impl.cookie.BasicClientCookie;
//import org.apache.http.impl.cookie.BasicClientCookie2;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

//import crifanLib;

/**
 * @author CLi
 * 
 */
public class EmulateLoginBaidu {

	static crifanLib crl;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		crl = new crifanLib();
		
		EmulateLoginBaiduUsingJava("", "");
		
		
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
	        String html = crl.getUrlRespHtml("http://tieba.baidu.com/f/like/mylike");
	        Document doc = Jsoup.parse(html);
	        Elements tableEs = doc.getElementsByTag("table");
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
	        					enterToTieba(title, href);
	        					
	        					//TODO 后续打开
	        					break;
	        				}
	        			}
	        		}
	        	}
	        }
	        
	        //page 
		}
        
		return ;
	}
	
	private static void enterToTiezi(String url) {
		
		List<NameValuePair> postDict = new ArrayList<NameValuePair>();
		postDict.add(new BasicNameValuePair("u", "https://passport.baidu.com/"));
		postDict.add(new BasicNameValuePair("tpl", "mn"));
		postDict.add(new BasicNameValuePair("staticpage", "https://passport.baidu.com/static/passpc-account/html/v3Jump.html"));
		postDict.add(new BasicNameValuePair("isPhone", "false"));
		postDict.add(new BasicNameValuePair("charset", "UTF-8"));
		postDict.add(new BasicNameValuePair("callback", "parent.bd__pcbs__ra48vi"));
		
		String html = crl.getUrlRespHtml(url);
		
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
					if(repCount == 0) {
						Elements threadlist_li_rightEs = threadlist_li_leftE.parent().getElementsByClass("threadlist_li_right");
						if(threadlist_li_rightEs != null) {
							boolean isOver = false;
							for(Element threadlist_li_rightE : threadlist_li_rightEs) {
								//j_th_tit
								Elements biaotiAEs = threadlist_li_rightE.getElementsByClass("j_th_tit");
								if(biaotiAEs != null) {
									for(Element e : biaotiAEs) {
										String tagName = e.tagName();
										if("a".equalsIgnoreCase(tagName)) {
											String tieziHref = e.attr("href");
											enterToTiezi(url + tieziHref);
											System.out.println(url + tieziHref);
											//TODO 
											isOver = true;
											break;
										}
											
									}
								}
								
								if(isOver) {
									break;
								}
							}
						}
					}
				}
			}
		}
		
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
}
