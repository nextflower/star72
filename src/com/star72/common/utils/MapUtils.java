package com.star72.common.utils;

import java.util.Map;
import java.util.Random;

/**
 * map的工具类
 * 
 * @author larry
 *
 */
public class MapUtils {
	
	@SuppressWarnings("unchecked")
	public static <K, V> K getRandomKey(Map<K,V> map) {
		if(map == null || map.size() == 0) {
			return null;
		}
		Object[] array = map.keySet().toArray();
		return (K) array[RandomUtil.randomInt(0, array.length)];
	}

}
