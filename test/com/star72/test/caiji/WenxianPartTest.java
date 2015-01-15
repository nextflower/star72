package com.star72.test.caiji;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import com.star72.common.utils.StarStringUtils;

public class WenxianPartTest {

	@Test
	public void test() throws IOException {
		String path = "D:/temp/綴白裘.txt";
		String muluPath = "D:/temp/目录.txt";
		String destPath = FilenameUtils.getFullPath(path) + FilenameUtils.getBaseName(path);
		
		List<String> lines = FileUtils.readLines(new File(path));
		List<String> muluList = FileUtils.readLines(new File(muluPath));
		
		//初始化目录
		Map<String, Set<String>> map = new HashMap<String, Set<String>>();
		for(String line : muluList) {
			String[] arr = line.split("：");
			if(arr.length == 2) {
				String key = arr[0];
				String value = arr[1];
				Set<String> set = map.get(key);
				if(set == null) {
					set = new HashSet<String>();
					map.put(key, set);
				}
				String[] split = value.split("、");
				for(String s : split) {
					set.add(s);
				}
			}
		}
		
		//稿件容器
		List<String> contentList = new ArrayList<String>();
		//标题变量
		String title = null;
		String author = "";
		String t1 = null;
		
		int count = 0;
		for(String line : lines) {
			
			if(StringUtils.isNotBlank(line) && line.length() < 4) {
				for(String key : map.keySet()) {
					if(line.equals(key)) {
						t1 = key;
						break;
					}
				}
			}
			
			if(line.equals(t1)) {
				continue;
			}
			
			Set<String> set = map.get(t1);
			if(set != null && isTitle(line, set)) {
				
				if(title != null) {
					FileUtils.writeLines(new File(destPath + File.separator + title + ".txt"), contentList);
				}
				
				title = ++count + "-" + t1 + " " + line;
				contentList.clear();
			} else {
				contentList.add(line);
			}
			
		}
		
		if(title != null) {
			FileUtils.writeLines(new File(destPath + File.separator + title + ".txt"), contentList);
		}
		
		System.out.println(count);
	}

	private boolean isTitle(String line, Set<String> set) {
		if(StringUtils.isBlank(line) || line.length() > 3) {
			return false;
		}
		for(String s : set) {
			if(s.equals(line)) {
				return true;
			}
		}
		return false;
	}

}
