package com.star72.test.caiji;

import java.io.File;

import org.junit.Test;

public class FileValidateTest {
	
	public static final String FLAG_STR = "�";
	@Test
	public void test() {
		String path = "F:\\文档\\gudian\\首页\\01易藏-0195部";
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

}
