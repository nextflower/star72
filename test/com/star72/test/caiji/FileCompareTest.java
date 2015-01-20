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


public class FileCompareTest {
	
	@Test
	public void test() {
		
		//String pathRaw = "G:\\文档\\gudaiwenxian\\易藏";//原始文件夹
		String pathRaw = "G:\\文档\\gudaiwenxian";//原始文件夹
		String pathMake = "G:\\文档\\gudian\\首页";//加工后的文件夹
		
		
		File rawFile = new File(pathRaw);
		File makeFile = new File(pathMake);
		
		//生成原始文件和加工后文件的映射map,若无对应,则为null
		Map<File, File> map = getCompareMap(rawFile, makeFile);
		
		for(File file : map.keySet()) {
			File value = map.get(file);
			String author = null;
			String chaodai = null;
			if(value != null && value.isDirectory()) {//已经深度加工的文件
				//System.out.println(value.getAbsolutePath());
				String baseName = FilenameUtils.getBaseName(value.getAbsolutePath());
				String[] arr = baseName.split("-");
				if(arr.length == 4) {
					author = arr[3];
					chaodai = arr[2];
				}
			} else {//按照原来的文件进行统一处理
				
			}
			
		}
		
	}

	private Map<File, File> getCompareMap(File rawFile, File makeFile) {
		
		Map<File, File> map = new HashMap<File, File>();
		if(rawFile == null || makeFile == null) {
			return map;
		}
		
		//获取文件名
		Set<String> rawFileNames = StarFileUtils.getFileNames(rawFile, true, false);
		Set<String> makeFileNames = StarFileUtils.getFileNames(makeFile, true, true);
		
		//判断是否文件名全部符合要求
		boolean isAllRight = isAllRight(makeFileNames);
		if(!isAllRight) {
			return map;
		}
		//包含作者的文件名全部符合条件
		
		//筛选包含作者的标准结果
		Map<String, String> makeFileNameMap = new HashMap<String, String>();
		for(String makeFileName : makeFileNames) {
			String[] arr = makeFileName.split("\\\\");
			int count = 0;
			for(String s : arr) {
				if(s.split("-").length == 4) {
					count++;
				}
			}
			if(count == 1 && arr[arr.length-1].split("-").length == 4) {
				String baseName = FilenameUtils.getBaseName(makeFileName);
				makeFileNameMap.put(baseName.split("-")[1], makeFileName);
			}
		}
		
		int mapCount = 0;
		for(String rawFileName : rawFileNames) {
			String baseName = FilenameUtils.getBaseName(rawFileName);
			String value = makeFileNameMap.get(baseName);
			if(value != null) {
				mapCount++;
				map.put(new File(rawFileName), new File(value));
			} else {
				map.put(new File(rawFileName), null);
			}
		}
		
		System.out.println("原始文件数量：" + rawFileNames.size());
		System.out.println("作者文件数量：" + makeFileNameMap.size());
		System.out.println(mapCount);
		
		return map;
	}

	private boolean isAllRight(Set<String> makeFileNames) {
		boolean isAllRight = true;
		for(String makeFileName : makeFileNames) {
			boolean isRight = false;
			boolean isFile = false;
			String[] arr = makeFileName.split("\\\\");
			for(int i=arr.length - 1; i >= 0; i--) {
				String temp = arr[i];
				if(temp.endsWith(".txt")) {
					isFile = true;
				}
				if(StringUtils.countMatches(temp, "-") == 3 || StringUtils.countMatches(temp, "-") == 4) {
					isRight = true;
					break;
				}
			}
			if(!isRight && isFile) {
				System.out.println(makeFileName);
				isAllRight = false;
			}
		}
		return isAllRight;
	}

}
