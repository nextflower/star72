package com.star72.common.utils.math;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 排列组合计算工具类
 * 
 * @author wz
 *
 */
public class PLZUUtils {
	
	public static BigDecimal computePaiLie(int n, int m) {
		if(m > n || n < 0 || m < 0) {
			throw new IllegalArgumentException("n必须大于m!");
		}
		return computerJC(n).divide(computerJC(n - m), 1, RoundingMode.HALF_UP);
	}
	
	public static BigDecimal computeZuhe(int n, int m) {
		if(m > n || n < 0 || m < 0) {
			throw new IllegalArgumentException("n必须大于m!");
		}
		//=n!/m!(n-m)!
		
		return computerJC(n).divide((computerJC(m).multiply(computerJC(n - m)).setScale(1, RoundingMode.HALF_UP)), 1, RoundingMode.HALF_UP);
	}
	
	
	public static BigDecimal computerJC(int n) {
		if(n < 0) {
			throw new IllegalArgumentException("n不能为负数!");
		} else if(n == 0) {
			return new BigDecimal(1);
		}
		BigDecimal bd = new BigDecimal(1.0);
		for(int i=n; i>=1; i--) {
			bd = bd.multiply(new BigDecimal(i)).setScale(1, RoundingMode.HALF_UP);
		}
		return bd;
	}
	
	public static void main(String[] args) {
		BigDecimal zh = computeZuhe(462, 442);
		System.out.println(zh.doubleValue());
	}
	
}
