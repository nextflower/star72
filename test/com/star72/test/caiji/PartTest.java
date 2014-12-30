package com.star72.test.caiji;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class PartTest {

	static int fileCount = 0;
	static int dirCount = 0;
	static int validated = 0;
	
	static String[] start_flag = new String[]{"卷一","卷二","卷三","卷四","卷五","卷六","卷七","卷八","卷九","卷十"};
	
	@Test
	public void test() {
		String path = "D:\\gudaiwenxian\\道藏";
		
		//15696
		//216
		
		File file = new File(path);
		list(file);
		
		
		
		System.out.println("fileCount:" + fileCount);
		System.out.println("dirCount:" + dirCount);
		System.out.println("validated:" + validated);
		
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
			if(validate(file)) {
				validated++;
			}
			fileCount++;
		}
	}

	

	private boolean validate(File file) {
		boolean flag = false;
		try {
			List<String> readLines = FileUtils.readLines(file);
			
			for(String line : readLines) {
				for(String biao : start_flag) {
					if(line.trim().startsWith(biao)) {
						flag = true;
						return flag;
					}
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return flag;
	}
	
}
