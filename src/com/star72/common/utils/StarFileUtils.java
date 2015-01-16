package com.star72.common.utils;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * 文件相关操作工具类
 * 
 * @author larry
 *
 */
public class StarFileUtils {

	/**
	 * 文件统计方法(文件夹不做统计)
	 * @param file 需要统计的文件
	 * @param digui 是否递归
	 * @return
	 */
	public static Integer getFileCount(File file, boolean digui) {
		
		if(file == null || !file.exists()) {
			return 0;
		}
		
		Integer count = 0;
		if(file.isFile()) {
			count++;
		} else {
			File[] childs = file.listFiles();
			if(childs != null) {
				if(digui) {
					for(File child : childs) {
						count += getFileCount(child, digui);
					}
				} else {
					for(File child : childs) {
						if(child.isFile()) {
							count++;
						}
					}
				}
			}
		}
		
		return count;
	}
	
	/**
	 * 获取文件夹下所有文件的文件名
	 * @param file
	 * @param digui 是否递归
	 * @param includeDir 是否包含文件夹名
	 * @return
	 */
	public static Set<String> getFileNames(File file, boolean digui, boolean includeDir) {
		Set<String> set = new HashSet<String>();
		if(file == null || !file.exists()) {
			return set;
		}
		
		if(file.isFile()) {
			set.add(file.getAbsolutePath());
		} else {
			if(includeDir) {
				set.add(file.getAbsolutePath());
			}
			File[] childs = file.listFiles();
			if(childs != null) {
				if(digui) {
					for(File child : childs) {
						set.addAll(getFileNames(child, digui, includeDir));
					}
				} else {
					for(File child : childs) {
						if(child.isFile()) {
							set.add(child.getAbsolutePath());
						} else if(includeDir) {
							set.add(child.getAbsolutePath());
						}
					}
				}
			}
		}
		return set;
	}
	
}
