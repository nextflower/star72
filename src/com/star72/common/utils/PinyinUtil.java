package com.star72.common.utils;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.pinyin4j.PinyinHelper;

/**
 * 汉字转拼音工具类,需要使用第三方jar包pinyin4j.jar
 * 
 * @author wz
 *
 */
public class PinyinUtil {
	
	public static List<String> hanzi2PinyinNoDiao(String str) {
		List<String> result = new ArrayList<String>();
		List<String> list = hanzi2Pinyin(str);
		for(String s : list) {
			String ss = s.replaceAll("\\d", "");
			result.add(ss);
		}
		return result;
	}
	public static List<String> hanzi2Pinyin(String str) {
		List<String> result = new ArrayList<String>();
		char[] charArray = str.toCharArray();
		for(char ch : charArray) {
			String[] arr = PinyinHelper.toHanyuPinyinStringArray(ch);
			if(arr != null && arr.length > 0) {
				result.add(arr[0]);
			}
		}
		return result;
	}

}
