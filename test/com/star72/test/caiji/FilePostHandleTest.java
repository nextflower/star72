package com.star72.test.caiji;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

public class FilePostHandleTest {
	
	@Test
	public void test() throws IOException {
		String path = "D:\\temp\\元诗选";
		File f = new File(path);
		File[] files = f.listFiles();
		Map<Integer, String> map = new TreeMap<Integer, String>();
		for(File file : files) {
			String name = file.getName();
			String[] arr = name.split("-");
			Integer sort = Integer.parseInt(arr[0]);
			map.put(sort, file.getAbsolutePath());
		}
		
		Map<String, String> nameMap = new HashMap<String, String>();
		List<String> names = FileUtils.readLines(new File("D:\\temp\\元诗选-作者.txt"));
		for(String name : names) {
			if(StringUtils.isNotBlank(name)) {
				String[] arr = name.split("=");
				nameMap.put(arr[0], arr[1]);
			}
		}
		
		List<String> list = null;
		String author = null;
		for(Integer key : map.keySet()) {
			String absPath = map.get(key);
			String baseName = FilenameUtils.getBaseName(absPath);
			String[] arr = baseName.split("-");
			
			if(baseName.contains("◆")) {
				author = arr[1].replace("◆", "");
				list = FileUtils.readLines(new File(absPath));
				//System.out.println(author + "---" + nameMap.get(author));
				author = nameMap.get(author);
			} else {
				if(list != null) {
					String newName = baseName.concat("-元-" + author);
					String newPath = FilenameUtils.getFullPath(absPath) + newName;
					System.out.println(newPath);
					File file = new File(absPath);
					file.renameTo(new File(newPath));
				}
			}
		}
		
	}

}
