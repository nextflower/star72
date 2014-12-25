package com.star72.common.utils;

import java.util.Random;

/**
 * 随机工具类
 * 
 * @author larry
 *
 */
public class RandomUtil {
	
	/**
	 * 例：randomInt(5, 10) ==> {5, 14}
	 * @param start 起始数值
	 * @param range 变化范围
	 * @return
	 */
	public static Integer randomInt(int start, int range) {
		Random rand = new Random();
		return rand.nextInt(range) + start;
	}
	
}
