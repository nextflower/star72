package com.star72.common.utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 域名查询工具类
 * 
 * @author wz
 *
 */
public class DomainQueryUtil {
	
	/**
	 * 
	 * @param domain
	 * @return
	 */
	public static boolean queryByWanWang(String domain, boolean showFail) {
		/*
		 * 返回 XML 结果说明：
			returncode=200 表示接口返回成功
			key=***.com表示当前check的域名
			original=210 : Domain name is available     表示域名可以注册
			original=211 : Domain name is not available 表示域名已经注册
			original=212 : Domain name is invalid   表示域名参数传输错误
			original=213 : Time out 查询超时
		 */
		try {
			//http://panda.www.net.cn/cgi-bin/check.cgi?area_domain=teapic.com
			String url = "http://panda.www.net.cn/cgi-bin/check.cgi?area_domain=" + domain;
			Document doc = Jsoup.parse(new URL(url), 20000);
			Elements propE = doc.getElementsByTag("property");
			if(propE != null && propE.size() > 0) {
				Element prop = propE.get(0);
				String text = prop.text();
				if(text.contains("200") && text.contains("210 : Domain name is available")) {
					System.out.println(text);
				} else if(showFail){
					System.out.println(text);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static void main(String[] args) {
		String domain = "tiandixuanhuang.com";
		queryByWanWang(domain, true);
	}

}
