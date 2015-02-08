package com.star72.naming.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import com.star72.common.utils.StarStringUtils;

public class NameTest {
	
	@Test
	public void test() throws IOException {
		String path = "D:/wuxing.txt";
		File file = new File(path);
		List<String> lines = FileUtils.readLines(file);
		
		Map<Integer, Set<String>> jinMap = new HashMap<Integer, Set<String>>();
		Map<Integer, Set<String>> huoMap = new HashMap<Integer, Set<String>>();
		for(String line : lines) {
			if(StringUtils.isNotBlank(line)) {
				String[] arr = line.split("-");
				String wuxing = arr[0];
				String bihua = arr[1];
				String zi = arr[2];
				if(wuxing.equals("金")) {
					List<String> list = StarStringUtils.parseStr2SingleStrList(zi);
					jinMap.put(Integer.parseInt(bihua), new HashSet<String>(list));
				} else if(wuxing.equals("火")){
					List<String> list = StarStringUtils.parseStr2SingleStrList(zi);
					huoMap.put(Integer.parseInt(bihua), new HashSet<String>(list));
				}
			}
		}
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("9-12", "土火木  100");
		map.put("19-12", "土火木 100");
		map.put("9-4", "土火火  93");
		map.put("19-14", "土火火  93");
		map.put("9-16", "土火土 90");
		map.put("13-12", "土金土 85");
		map.put("11-14", "土土土 85");
		map.put("13-4", "土金金  85");
		
		List<String> list = new ArrayList<String>();
		list.add("9-12");
		list.add("19-12");
		list.add("9-4");
		list.add("19-14");
		list.add("9-16");
		list.add("13-12");
		list.add("11-14");
		list.add("13-4");
		
		for(String str : list) {
			String[] arr = str.split("-");
			int huo = Integer.parseInt(arr[1]);
			int jin = Integer.parseInt(arr[0]);
			System.out.println();
			System.out.println();
			System.out.println(4 + "-" + jin + "(金)" + huo + "(火)-" + "\t" + map.get(str));
			Set<String> jinSet = jinMap.get(jin);
			Set<String> huoSet = jinMap.get(huo);
			
			int count = 0;
			for(String hu : huoSet) {
				for(String ji : jinSet) {
					count++;
					System.out.print("王" + ji + hu + "  ");
					if(count % 10 == 0) {
						System.out.println();
					}
				}
			}
		}
		
	}

}
