package com.star72.test.caiji;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class FileCountTest {

	static int fileCount = 0;
	static int dirCount = 0;
	
	public void test2() {
		List<String> list = new ArrayList<String>();
		list.add("3-元诗选-清-顾嗣立");
		list.add("8-全唐诗");
		list.add("9-全宋词--唐圭璋");
		list.add("10-全宋诗-卷二");
		list.add("10-全宋诗-卷三");
		list.add("10-全宋诗-卷四");
		list.add("10-全宋诗-卷一");
		list.add("12-列朝诗集-清-钱谦益");
		list.add("20-晚晴簃诗汇");
	}
	
	@Test
	public void test() {
		String path = "D:\\gudian\\首页\\11诗词\\16-宋诗钞-清-吴之振";
		
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
