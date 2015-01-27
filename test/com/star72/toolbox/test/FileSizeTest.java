package com.star72.toolbox.test;

import java.io.File;

import org.apache.commons.io.FilenameUtils;
import org.junit.Test;

import com.star72.common.utils.StarFileUtils;

public class FileSizeTest {
	
	@Test
	public void test() {
		String path = "G:\\文档\\gudian\\首页";
		File file = new File(path);
		list(file);
	}

	private void list(File file) {
		
		if(file == null) {
			return;
		}
		
		if(file.isDirectory()) {
			File[] files = file.listFiles();
			if(files != null) {
				for(File f : files) {
					list(f);
				}
			}
		} else {
			
			String ext = FilenameUtils.getExtension(file.getName());
			if("txt".equalsIgnoreCase(ext)) {
				double sizeMB = StarFileUtils.getFileSizeMB(file);
				if(sizeMB > 0.2) {
					System.out.println(file.getAbsolutePath());
				}
			}
			
		}
		
	}

}
