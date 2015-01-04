package com.star72.test.caiji;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.junit.Test;

import com.star72.common.utils.StarStringUtils;

public class CopyOfFileReNameTest {
	
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
	
	public static final String FLAG_STR = "�";
	
	public static boolean openRename = false;
	
	@Test
	public void test() {
		String rightPath = "F:\\文档\\gudaiwenxian";
		String errorPath = "F:\\文档\\gudian\\首页";
		
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> list = new ArrayList<String>();
		getNameMap(new File(rightPath), map);
		
		getErrorNameList(new File(errorPath), list);
		
		Set<String> aaSet = new HashSet<String>();
		
		for(String s : list) {
			System.out.println(s);
			String[] dirs = s.split("\\\\");
			String last = dirs[dirs.length - 1];
			String[] namesplit = last.split("-");
			String name = null;
			if(namesplit.length >= 2) {
				name = namesplit[1];
			}
			
			if(name != null && name.contains(FLAG_STR)) {
				for(String key : map.keySet()) {
					if(key.length() == name.length()) {
						List<String> keyList = StarStringUtils.parseStr2SingleStrList(name);
						boolean flag = true;
						for(String ks : keyList) {
							if(FLAG_STR.equals(ks) || key.contains(ks)) {
								
							} else {
								flag = false;
								break;
							}
						}
						
						if(flag) {
							List<String> ll = map.get(key);
							if(ll != null) {
								//System.out.println(s);
								String dest = s.replace(name, key);
								File file = new File(s);
								System.out.println();
								System.out.println();
								System.out.println(dest);
								System.out.println();
								System.out.println();
								if(openRename) {
									file.renameTo(new File(dest));
								}
							}
						}
						
					}
				}
			}
		}
		
	}
	
	
	
	private void getErrorNameList(File file, List<String> list) {
		if(file == null) {
			return;
		}
		
		String name = file.getName();
		if(name.contains("11诗词")) {
			return;
		}
		
		if(file.isDirectory()) {
			File[] listFiles = file.listFiles();
			if(listFiles != null) {
				for(File child : listFiles) {
					getErrorNameList(child, list);
				}
			}
			String absPath = file.getAbsolutePath();
			if(absPath.contains(FLAG_STR)) {
				list.add(file.getAbsolutePath());
			}
			
		} else {
			String n = file.getName();
			if(n.contains(FLAG_STR)) {
				list.add(file.getAbsolutePath());
			}
		}
	}



	/**
	 * 获取正确的文件名
	 * @param file
	 * @param map
	 */
	private void getNameMap(File file, Map<String, List<String>> map) {
		if(file == null) {
			return;
		}
		if(file.isDirectory()) {
			File[] listFiles = file.listFiles();
			if(listFiles != null) {
				for(File child : listFiles) {
					getNameMap(child, map);
				}
			}
		} else {
			String name = file.getName();
			String baseName = FilenameUtils.getBaseName(name);
			List<String> set = map.get(baseName);
			if(set == null) {
				set = new ArrayList<String>();
				map.put(baseName, set);
			}
			set.add(file.getAbsolutePath());
		}
	}
	

}
