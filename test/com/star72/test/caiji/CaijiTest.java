package com.star72.test.caiji;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

public class CaijiTest {

	private String base = "http://wenxian.fanren8.com";
	private String destPath = "D:/gudian_gbk";

	static int totalCount = 0;

	@Test
	public void caijiTest() throws MalformedURLException, IOException {
		// "04","05","06","07","08","09","10","11"
		String[] indexes = new String[] { "04","05","06","07","08","09","10" };
		// http://wenxian.fanren8.com/01/index.htm
		for (String index : indexes) {
			String url = "http://wenxian.fanren8.com/" + index + "/index.htm";
			// Document doc = Jsoup.parse(new URL(url), 20000);
			Document doc =Jsoup.parse(getURLContent(url));
			//Document doc = Jsoup.parse(new URL(url).openStream(), "utf8", url);
			parse(doc, index + "/index.htm");
			// break;
		}
	}

	private void parse(Document doc, String preHref) throws IOException {

		if (doc == null) {
			return;
		}

		Element container = doc.getElementById("container");
		Element cont = doc.getElementById("cont");
		if (cont == null && container != null) {
			// 目录
			Elements uls = container.getElementsByTag("ul");
			if (uls != null) {
				for (Element ul : uls) {
					Elements as = ul.getElementsByTag("a");
					if (as != null) {
						for (Element aE : as) {
							String href = aE.attr("href");
							Document child = null;
							child =Jsoup.parse(getURLContent(base + href));
//							try {
//								// child = Jsoup.parse(new URL(base + href),
//								// 20000);
//								
//							} catch (MalformedURLException e) {
//								e.printStackTrace();
//							} catch (IOException e) {
//								e.printStackTrace();
//							}

							if (child == null) {
								continue;
							}

							if (preHref.contains(href)) {
								continue;
							}
							parse(child, href);
							// break;
						}
					}
				}
			}
		} else if (cont != null) {
			totalCount++;
			// 内容
			Element positionE = doc.getElementById("position");
			Elements h2E = container.getElementsByTag("h2");
			List<String> pathList = new ArrayList<String>();
			if (positionE != null) {
				Elements asE = positionE.getElementsByTag("a");
				for (Element aE : asE) {
					pathList.add(aE.text());
				}
			}
			if (h2E != null && h2E.size() > 0) {
				pathList.add(h2E.text());
			}
			System.out.println(totalCount + "---" + pathList);

			String path = destPath + generatePath(pathList);

			// System.out.println(path);

			File destFile = new File(path);

			if (!destFile.exists()) {
				FileUtils.writeStringToFile(destFile, cont.toString());
			}

		} else {
			System.out.println("未找到");
		}

	}

	private String generatePath(List<String> pathList) {
		if (pathList == null || pathList.size() == 0) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (String path : pathList) {
			sb.append("/" + path);
		}
		return sb.length() > 0 ? sb.toString() + ".txt" : sb.toString();
	}

	public static String getURLContent(String urlStr) {
		/** 网络的url地址 */
		URL url = null;
		/** http连接 */
		HttpURLConnection httpConn = null;
		/**//** 输入流 */
		BufferedReader in = null;
		StringBuffer sb = new StringBuffer();
		try {
			url = new URL(urlStr);
			in = new BufferedReader(new InputStreamReader(url.openStream(),
					"GBK"));
			String str = null;
			while ((str = in.readLine()) != null) {
				sb.append(str);
			}
		} catch (Exception ex) {

		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
			}
		}
		String result = sb.toString();
		//System.out.println(result);
		return result;
	}

}
