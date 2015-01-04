package com.star72.toolbox.util;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.star72.common.utils.StarStringUtils;
import com.star72.naming.dicreader.DicReader;

/**
 * 
 * 
 * @author larry
 *
 */
public class ToolBoxUtils {
	
	
	/**
	 * 简体转繁体
	 * @param source
	 * @return
	 */
	public static String convertJian2Fan(String source) {
		if(StringUtils.isBlank(source)) {
			return source;
		}
		Map<String, String> jianFanMap = DicReader.getJianFanMap();
		List<String> list = StarStringUtils.parseStr2SingleStrList(source);
		StringBuffer sb = new StringBuffer();
		for(String s : list) {
			String fan = jianFanMap.get(s);
			if(fan == null) {
				sb.append(s);
			} else {
				sb.append(fan);
			}
		}
		return sb.toString();
	}
	
	/**
	 * 繁体转简体
	 * @param source
	 * @return
	 */
	public static String convertFan2Jian(String source) {
		if(StringUtils.isBlank(source)) {
			return source;
		}
		Map<String, String> fanJianMap = DicReader.getFanJianMap();
		List<String> list = StarStringUtils.parseStr2SingleStrList(source);
		StringBuffer sb = new StringBuffer();
		for(String s : list) {
			String fan = fanJianMap.get(s);
			if(fan == null) {
				sb.append(s);
			} else {
				sb.append(fan);
			}
		}
		return sb.toString();
	}

}
