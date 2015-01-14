package com.star72.test.caiji;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import com.star72.common.utils.StarStringUtils;

public class WenxianPartTest {

	@Test
	public void test() throws IOException {
		String path = "D:/TEST/star/全唐五代词.txt";
		String destPath = FilenameUtils.getFullPath(path) + FilenameUtils.getBaseName(path);
		
		List<String> lines = FileUtils.readLines(new File(path));
		
		//稿件容器
		List<String> contentList = new ArrayList<String>();
		//标题变量
		String preTitle = null;
		String title = null;
		String author = null;
		
		int count = 0;
		for(String line : lines) {
			
			if(isAuthor(line)) {
				author = line;
			}
			
			if(isTitle(line)) {
				
				if(title != null) {
						
				}
				
				if(author == null) {
					author = "佚名";
				}
				
				if(line.contains("【又】") || line.contains("【其")) {
					
				}
				
				title = ++count + "-" + line + "-唐-" + author;
			} else {
				contentList.add(line);
			}
			
			
		}
		
		if(title != null) {
			//FileUtils.writeLines(new File(destPath + File.separator + title + ".txt"), contentList);
		}
		
		System.out.println(count);
	}

	private boolean isTitle(String line) {
		if(StringUtils.isBlank(line)) {
			return false;
		}
		
		if(!line.startsWith("　") && !line.startsWith(" ")) {
			return true;
		}
		
		if(line.contains("【")) {
			return true;
		}
		
		return false;
	}
	
	private boolean isAuthor(String line) {
		if(StringUtils.isBlank(line)) {
			return false;
		}
		
		if(!line.startsWith("　") && !line.startsWith(" ")) {
			return true;
		}
		
		return false;
	}

}
