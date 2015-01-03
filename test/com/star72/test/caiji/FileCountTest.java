package com.star72.test.caiji;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class FileCountTest {

	static int fileCount = 0;
	static int dirCount = 0;
	
	@Test
	public void test() {
		String path = "G:\\文档\\gudaiwenxian";
		
		//15696
		//216
		
		File file = new File(path);
		list(file);
		
		
		
		System.out.println("fileCount:" + fileCount);
		System.out.println("dirCount:" + dirCount);
		
	}
	
	
	private void list(File file) {
		if(file.isDirectory()) {
			dirCount++;
			File[] childs = file.listFiles();
			if(childs != null) {
				for(File f : childs) {
					list(f);
				}
			}
		} else {
			fileCount++;
		}
	}
	
}
