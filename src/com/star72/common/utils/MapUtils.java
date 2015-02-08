package com.star72.common.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

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
	
	/** 
     * 使用 Map按key进行排序 
     * @param map 
     * @param desc 是否降序
     * @return 
     */  
    public static <K, V> Map<K, V> sortMapByKey(Map<K, V> map, boolean desc) {  
        if (map == null || map.isEmpty()) {  
            return null;  
        }  
        final int flag = desc ? -1 : 1;
        Map<K, V> sortMap = new TreeMap<K, V>(new Comparator<K>(){
        	public int compare(K k1, K k2) {  
        		if(k1 instanceof Comparable && k1 instanceof Comparable) {
        			return ((Comparable)k1).compareTo((Comparable)k1) * flag;
        		} else {
        			return 0;
        		}
            } 
        });  
        sortMap.putAll(map);  
        return sortMap;  
    } 
    
    /** 
     * 使用 Map按value进行排序 
     * @param map 
     * @param desc 是否降序 
     * @return 
     */  
    public static <K, V> Map<K, V> sortMapByValue(Map<K, V> map, boolean desc) {  
        if (map == null || map.isEmpty()) {  
            return null;  
        }  
        final int flag = desc ? -1 : 1;
        Map<K, V> sortedMap = new LinkedHashMap<K, V>();  
        List<Map.Entry<K, V>> entryList = new ArrayList<Map.Entry<K, V>>(map.entrySet());  
        Collections.sort(entryList, new Comparator<Map.Entry<K, V>>(){
			@Override
			public int compare(Entry<K, V> entry1, Entry<K, V> entry2) {
				V v1 = entry1.getValue();
				V v2 = entry2.getValue();
				if(v1 instanceof Comparable && v2 instanceof Comparable) {
					return ((Comparable)v1).compareTo((Comparable)v2) * flag;
				} else {
					return 0;
				}
			}
        	
        });  
        Iterator<Map.Entry<K, V>> iter = entryList.iterator();  
        Map.Entry<K, V> tmpEntry = null;  
        while (iter.hasNext()) {  
            tmpEntry = iter.next();  
            sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());  
        }  
        return sortedMap;  
    }  
    
}
