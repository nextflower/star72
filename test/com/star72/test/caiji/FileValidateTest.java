package com.star72.test.caiji;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.junit.Test;

import com.star72.common.utils.StarStringUtils;

public class FileValidateTest {
	
	/*
	 * gudian\\首页\\02儒藏-0370部
	 * gudian\\首页\\03道藏-1689部
	 * 04佛藏-5159部
	 * 05子藏-1155部
	 * 06史藏-1725部
	 * 07诗藏-0322部
	 * 08集藏-1467部
	 * 09医藏-0869部
	 * 10艺藏-0386部
	 * 11诗词
	 */
	
	public static final Pattern pattern = Pattern.compile("\\d+-.*-(.*�[^-]*)-");
	
	public static final Map<String, String> rightMap = new HashMap<String, String>();
	
	public static final String FLAG_STR = "�";
	@Test
	public void test() {
		
		getNameMap(new File("G:\\文档\\gudaiwenxian"));
		
		
		String path = "G:\\文档\\gudian\\首页\\04佛藏-5159部";
		File file = new File(path);
		list(file);
		
	}
	
	
	private void list(File file) {
		if(file == null) {
			return;
		}
		String name = file.getName();
		
		if(name.contains(FLAG_STR)) {
			System.out.println(file.getAbsolutePath());
			Matcher m = pattern.matcher(name); 
			if(m.find()) {
				String cuName = m.group(1);
				for(String right : rightMap.keySet()) {
					List<String> li1 = StarStringUtils.parseStr2SingleStrList(right);
					List<String> li2 = StarStringUtils.parseStr2SingleStrList(cuName.replace(FLAG_STR, ""));
					Set<String> set = new HashSet<String>(li1);
					boolean flag = true;
					for(String l2 : li2) {
						if(!set.contains(l2)) {
							flag = false;
						}
					}
					if(flag) {
						System.out.println(cuName + "---" + right + "---" + rightMap.get(right));
					}
				}
				System.out.println();
				System.out.println("===================================================================");
				System.out.println();
			}
		}
		if(file.isDirectory()) {
			File[] listFiles = file.listFiles();
			if(listFiles != null) {
				for(File child : listFiles) {
					list(child);
				}
			}
		} else {
			
		}
	}
	
	private void getNameMap(File file) {
		if(file == null) {
			return;
		}
		if(file.isDirectory()) {
			File[] listFiles = file.listFiles();
			if(listFiles != null) {
				for(File child : listFiles) {
					getNameMap(child);
				}
			}
		} else {
			String name = file.getName();
			rightMap.put(FilenameUtils.getBaseName(name), file.getAbsolutePath());
		}
	}

}
