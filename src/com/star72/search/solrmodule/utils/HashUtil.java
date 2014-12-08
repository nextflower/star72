package com.star72.search.solrmodule.utils;
/**
 * �ַ�64λhash�����?
 * @author Jcrazy
 * @date 2012.08.20
 */
public class HashUtil {
	/**
	 * �Ľ��?2λFNV�㷨1
	 * 
	 * @param data
	 *            �ַ�
	 * @return intֵ
	 */
	public static int FNVHash1(String data) {
		final int p = 16777619;
		int hash = (int) 2166136261L;
		for (int i = 0; i < data.length(); i++)
			hash = (hash ^ data.charAt(i)) * p;
		hash += hash << 13;
		hash ^= hash >> 7;
		hash += hash << 3;
		hash ^= hash >> 17;
		hash += hash << 5;
		return hash;
	}

	/**
	 * ���hash�㷨�����?4λ��ֵ
	 */
	public static long mixHash(String str) {
		long hash = str.hashCode();
		hash <<= 32;
		hash |= FNVHash1(str);
		return hash;
	}
}
