package com.star72.test.caiji;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.junit.Test;

import com.star72.common.utils.StarFileUtils;


public class FileCompareTest {
	
	@Test
	public void test() {
		
		String pathRaw = "G:\\文档\\gudaiwenxian\\易藏";//原始文件夹
		String pathMake = "G:\\文档\\gudian\\首页\\01易藏-0195部";//加工后的文件夹
		
		
		File rawFile = new File(pathRaw);
		File makeFile = new File(pathMake);
		
		Map<File, File> map = getCompareMap(rawFile, makeFile);
		
		
		
	}

	private Map<File, File> getCompareMap(File rawFile, File makeFile) {
		
		Map<File, File> map = new HashMap<File, File>();
		if(rawFile == null || makeFile == null) {
			return map;
		}
		
		//获取文件名
		Set<String> rawFileNames = StarFileUtils.getFileNames(rawFile, true, false);
		Set<String> makeFileNames = StarFileUtils.getFileNames(makeFile, true, true);
		
		System.out.println(rawFileNames.size());
		System.out.println(makeFileNames.size());
		
//		boolean flag = true;
//		if(flag) {
//			return null;
//		}
		
		int count = 0;
		for(String rawFileName : rawFileNames) {
			String rawBase = FilenameUtils.getBaseName(rawFileName);
			boolean f = false;
			for(String makeFileName : makeFileNames) {
				
				String makeBase = FilenameUtils.getBaseName(makeFileName);
				
				String[] arr = makeBase.split("-");
				if(arr.length == 4) {
					if(arr[1].equals(rawBase)) {
						System.out.println();
						System.out.println(rawFileName);
						System.out.println(makeFileName);
						System.out.println();
						count++;
						f = true;
						break;
					}
				}
				
			}
			
			if(!f) {
				//System.out.println(rawFileName);
			}
			
//			List<String> list = new ArrayList<String>();
//			for(String makeFileName : makeFileNames) {
//				if(isSame(rawFileName, makeFileName)) {
//					list.add(makeFileName);
//				}
//			}
//			if(list.size() == 1) {
//				//System.out.println(rawFileName);
//				map.put(new File(rawFileName), new File(list.get(0)));
//			} else if(list.size() == 0) {
//				System.out.println(rawFileName);
//				count++;
//			} else {
//				//过滤
//			}
		}
		
		System.out.println(count);
		System.out.println(rawFileNames.size());
		System.out.println(makeFileNames.size());
		
		return map;
	}

	private boolean isSame(String rawFileName, String makeFileName) {
		//  \道藏\藏外\云笈七签.txt   
		//  \03道藏-1689部\09藏外-186种\9-云笈七签-宋-张君房\59-卷一道德部.txt
		String[] rawArr = rawFileName.split("\\\\");
		String[] makeArr = makeFileName.split("\\\\");
		
		boolean flag = true;
		for(int i=rawArr.length - 1; i >= rawArr.length - 2; i--) {
			String raw = FilenameUtils.getBaseName(rawArr[i]);
			boolean f2 = false;
			for(int j=makeArr.length - 1; j >= 0; j--) {
				String make = FilenameUtils.getBaseName(makeArr[j]);
				if(make.contains(raw)) {
					f2 = true;
					break;
				}
			}
			if(!f2) {
				return false;
			}
		}
		
		return flag;
	}
	
	

}
