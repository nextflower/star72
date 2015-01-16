package com.star72.test.caiji;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FilenameUtils;
import org.junit.Test;

import com.star72.common.utils.StarFileUtils;


public class FileCompareTest {
	
	@Test
	public void test() {
		
		String pathRaw = "G:\\文档\\gudaiwenxian";//原始文件夹
		String pathMake = "G:\\文档\\gudian\\首页";//加工后的文件夹
		
		
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
		Set<String> rawFileNames = StarFileUtils.getFileNames(rawFile, true, true);
		Set<String> makeFileNames_temp = StarFileUtils.getFileNames(makeFile, true, true);
		Set<String> makeFileNames = new HashSet<String>();
		for(String name : makeFileNames_temp) {
			String[] arr = name.split("\\\\");
			String last = arr[arr.length - 1];
			String last_2 = arr[arr.length - 2];
			if(last.split("-").length == 4 && last_2.split("-").length != 4) {
				makeFileNames.add(name);
			}
		}
		
		for(String rawFileName : rawFileNames) {
			List<String> list = new ArrayList<String>();
			for(String makeFileName : makeFileNames) {
				if(isSame(rawFileName, makeFileName)) {
					list.add(makeFileName);
				}
			}
			System.out.println(list.size());
			if(list.size() == 1) {
				System.out.println(rawFileName);
				map.put(new File(rawFileName), new File(list.get(0)));
			} else if(list.size() == 0) {
//				System.out.println(rawFileName);
			} else {
				//过滤
			}
		}
		
		return map;
	}

	private boolean isSame(String rawFileName, String makeFileName) {
		//  \道藏\藏外\云笈七签.txt   
		//  \03道藏-1689部\09藏外-186种\9-云笈七签-宋-张君房\59-卷一道德部.txt
		String[] rawArr = rawFileName.split("\\\\");
		String[] makeArr = makeFileName.split("\\\\");
		
		boolean flag = true;
		for(int i=rawArr.length - 1; i >= 0; i--) {
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
