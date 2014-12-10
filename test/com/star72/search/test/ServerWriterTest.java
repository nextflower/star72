package com.star72.search.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

public class ServerWriterTest {
	
	//private EPSSolrServerForCommon server = new EPSSolrServerForCommon();
	private String rootPath = "d:\\test"; 

	@Test
	public void test() {
		File rootFile = new File(rootPath);
		if(!rootFile.isDirectory()) {
			return ;
		}
		File[] files = rootFile.listFiles();
		if(files != null) {
			for(File f : files) {
				writeToIndex(f);
			}
		}
	}

	private void writeToIndex(File f) {
		if(f.isFile()) {
			String absolutePath = f.getAbsolutePath();
			String relativePath = absolutePath.replace(rootPath, "");
			List<String> catList = new ArrayList<String>();
			String[] dirArr = relativePath.split("\\\\");
			for(String s : dirArr) {
				if(StringUtils.isNotBlank(s)) {
					catList.add(s);
				}
			}
			catList.remove(catList.size() - 1);
			
			
			
		} else {
			File[] files = f.listFiles();
			if(files != null) {
				for(File child : files) {
					writeToIndex(child);
				}
			}
		}
	}
	
}
