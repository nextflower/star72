package com.star72.test.caiji;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class FileReNameTest {
	
	private Map<String, String> map = new HashMap<String, String>();
	
	@Test
	public void test() {
		String path = "F:\\文档\\gudian\\首页";
		String oldS = "勅修百丈清规-元-德�";
		String newS = "勅修百丈清规-元-德煇";
		list(new File(path), oldS, newS);
		
		for(String key : map.keySet()) {
			System.out.println(key);
			System.out.println(map.get(key));
			File f = new File(key);
			f.renameTo(new File(map.get(key)));
		}
	}
	
	
	private void list(File file, String oldS, String newS) {
		if(file == null) {
			return;
		}
		String name = file.getName();
		
		if(name.contains(oldS)) {
			String newName = name.replace(oldS, newS);
			String parent = file.getParent();
			map.put(file.getAbsolutePath(), parent + "\\" + newName);
		}
		
		if(file.isDirectory()) {
			File[] listFiles = file.listFiles();
			if(listFiles != null) {
				for(File child : listFiles) {
					list(child, oldS, newS);
				}
			}
		}
	}

}
