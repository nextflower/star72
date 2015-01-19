package com.star72.test.caiji;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import com.star72.common.utils.StarFileUtils;

public class FileReNameTest {
	
	private Map<String, String> map = new HashMap<String, String>();
	
	public void test() {
		String path = "D:\\gudaiwenxian";
		Set<String> fileNames = StarFileUtils.getFileNames(new File(path), true, false);
		
		int count = 0;
		Map<String, String> map = new HashMap<String, String>();
		for(String fileName : fileNames) {
			String baseName = FilenameUtils.getBaseName(fileName);
			if(map.get(baseName) != null) {
				System.out.println(map.get(baseName));
				System.out.println(fileName);
				System.out.println();
				File f = new File(fileName);
				if(f.exists()) {
					f.delete();
				}
				count++;
			} else {
				map.put(baseName, fileName);
			}
		}
		
		System.out.println(count);
		
	}
	
	@Test
	public void test2() {
		String path = "D:\\gudian\\首页\\03道藏-1689部\\08正统道藏续道藏-58部";
		String sou = "续道藏-";
		String dest = "";
		File f = new File(path);
		File[] files = f.listFiles();
		for(File file : files) {
			String parent = file.getParent();
			String name = file.getName();
			if(name.contains(sou)) {
				System.out.println(name);
				String newName = parent + File.separator + name.replace(sou, dest);
				System.out.println(newName);
				System.out.println();
				file.renameTo(new File(newName));
			}
		}
	}
	
}
