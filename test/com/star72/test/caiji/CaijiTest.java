package com.star72.test.caiji;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

public class CaijiTest {
	
	private String base = "http://wenxian.fanren8.com";

	@Test
	public void caijiTest() throws MalformedURLException, IOException {
		
		String[] indexes = new String[]{"01","02","03","04","05","06","07","08","09","10","11"};
		//http://wenxian.fanren8.com/01/index.htm
		for(String index : indexes) {
			String url = "http://wenxian.fanren8.com/" + index + "/index.htm";
			Document doc = Jsoup.parse(new URL(url), 5000);
			parse(doc);
			
			break;
		}
	}

	private void parse(Document doc) throws MalformedURLException, IOException {
		Element container = doc.getElementById("container");
		Element cont = doc.getElementById("cont");
		if(cont == null && container != null) {
			//目录
			Elements uls = container.getElementsByTag("ul");
			if(uls != null) {
				for(Element ul : uls) {
					Elements as = ul.getElementsByTag("a");
					if(as != null) {
						for(Element aE : as) {
							String href = aE.attr("href");
							Document child = Jsoup.parse(new URL(base + href), 5000);
							System.out.println(base + href);
							parse(child);
							break;
						}
					}
				}
			}
		} else if(cont != null){
			//内容
			Element positionE = doc.getElementById("position");
			Elements h2E = container.getElementsByTag("h2");
			List<String> pathList = new ArrayList<String>();
			if(positionE != null) {
				Elements asE = positionE.getElementsByTag("a");
				for(Element aE : asE) {
					pathList.add(aE.text());
				}
			}
			if(h2E != null && h2E.size() > 0) {
				pathList.add(h2E.text());
			}
			System.out.println(pathList);
			System.out.println(cont.toString());
			
		} else {
			System.out.println("未找到");
		}
		
	}
	
}
