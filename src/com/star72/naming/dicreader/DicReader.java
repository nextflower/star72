package com.star72.naming.dicreader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.star72.common.utils.StarStringUtils;
import com.star72.naming.entity.CharBean;

/**
 * 配置文件读取类
 * 
 * @author wz
 *
 */
public class DicReader {
	
	private DicReader() {}
	
	public static Map<String, String> getWuxingBihua_shiyi() {
		String dicPath = "com/star72/naming/dic/wuxingbihua_shiyi.txt";
		Map<String, String> result = new HashMap<String, String>();
		List<String> lines = readLines(dicPath);
		for(String line : lines) {
			String[] split = line.split("：");
			if(split.length == 2) {
				String shuxingStr = split[0];
				String description = split[1];
				String[] shuxingArr = shuxingStr.split("_");
				String hanzi = shuxingArr[1];
				result.put(hanzi, description);
			}
		}
		return result;
	}
	
	public static Map<String, String> getSancai_ji() {
		String dicPath = "com/star72/naming/dic/sancai.txt";
		Map<String, String> result = new HashMap<String, String>();
		List<String> lines = readLines(dicPath);
		for(String line : lines) {
			String[] split = line.split(":");
			if(split.length == 2) {
				String shuxingStr = split[0];
				String description = split[1];
				String[] shuxingArr = shuxingStr.split("_");
				String sancai = shuxingArr[0];
				result.put(sancai, description);
			}
		}
		return result;
	}
	
	public static Map<Integer, String> getWugeShuli_ji() {
		String dicPath = "com/star72/naming/dic/shuli81.txt";
		Map<Integer, String> result = new HashMap<Integer, String>();
		List<String> lines = readLines(dicPath);
		for(String line : lines) {
			String[] split = line.split(":");
			if(split.length == 2) {
				Integer shuzi = Integer.parseInt(split[0]);
				String description = split[1];
				result.put(shuzi, description);
			}
		}
		return result;
	}
	
	public static Map<String, Integer> getHanziBihua() {
		String dicPath = "com/star72/naming/dic/wuxingbihua_quan.txt";
		Map<String, Integer> result = new HashMap<String, Integer>();
		List<String> lines = readLines(dicPath);
		for(String line : lines) {
			String[] split = line.split(":");
			if(split.length == 2) {
				//木_3:巾 久 
				String wuxingbihua = split[0];
				String hansiStr = split[1];
				
				String[] split2 = wuxingbihua.split("_");
				Integer bihua = null;
				if(split2.length == 2) {
					bihua = Integer.parseInt(split2[1]);
				}
				List<String> hanziList = StarStringUtils.parseStr2SingleStrList(hansiStr);
				for(String hanzi : hanziList) {
					result.put(hanzi, bihua);
				}
			}
		}
		return result;
	}
	
	public static Map<String, Set<String>> getWuxingHanzi() {
		String dicPath = "com/star72/naming/dic/wuxingbihua_shiyi_ji.txt";
		Map<String, Set<String>> result = new HashMap<String, Set<String>>();
		List<String> lines = readLines(dicPath);
		for(String line : lines) {
			String[] split = line.split("_");
			if(split.length == 3) {
				String charName = split[1];
				String wuxingStr = split[2];
				String[] wuxingArr = wuxingStr.split("：");
				String wuxing = null;
				if(wuxingArr.length == 2) {
					wuxing = wuxingArr[0];
				}
				Set<String> set = result.get(wuxing);
				if(set == null) {
					set = new HashSet<String>();
					result.put(wuxing, set);
				}
				set.add(charName);
			}
		}
		return result;
	}
	
	public static Map<Integer, Set<String>> getBushouBihua() {
		String dicPath = "com/star72/naming/dic/bushoubihua.txt";
		Map<Integer, Set<String>> result = new HashMap<Integer, Set<String>>();
		List<String> lines = readLines(dicPath);
		for(String line : lines) {
			Set<String> set = new HashSet<String>();
			String[] split = line.split(":");
			if(split.length == 2) {
				Integer bihua = Integer.parseInt(split[0]);
				String hanziStr = split[1];
				List<String> hanziList = StarStringUtils.parseStr2SingleStrList(hanziStr);
				for(String hanzi : hanziList) {
					if(StringUtils.isNotBlank(hanzi)) {
						set.add(hanzi);
					}
				}
				result.put(bihua, set);
			}
		}
		return result;
	}
	
	public static Map<String, Set<String>> getBushouHanzi() {
		String dicPath = "com/star72/naming/dic/bushouhanzi.txt";
		Map<String, Set<String>> result = new HashMap<String, Set<String>>();
		List<String> lines = readLines(dicPath);
		for(String line : lines) {
			Set<String> set = new HashSet<String>();
			String[] split = line.split(":");
			if(split.length == 2) {
				String bushou = split[0];
				String hanziStr = split[1];
				List<String> hanziList = StarStringUtils.parseStr2SingleStrList(hanziStr);
				for(String hanzi : hanziList) {
					if(StringUtils.isNotBlank(hanzi)) {
						set.add(hanzi);
					}
				}
				result.put(bushou, set);
			}
		}
		return result;
	}
	
	public static Map<String, Set<String>> getShengxiaoBushou_xi() {
		String dicPath = "com/star72/naming/dic/shengxiaobushou_xi.txt";
		return getShengxiaoBushou(dicPath);
	}
	
	public static Map<String, Set<String>> getShengxiaoBushou_ji() {
		String dicPath = "com/star72/naming/dic/shengxiaobushou_ji.txt";
		return getShengxiaoBushou(dicPath);
	}
	
	private static Map<String, Set<String>> getShengxiaoBushou(String dicPath) {
		Map<String, Set<String>> result = new HashMap<String, Set<String>>();
		List<String> lines = readLines(dicPath);
		for(String line : lines) {
			if(StringUtils.isBlank(line)) {
				continue;
			}
			String[] split = line.split(":");
			if(split.length == 2) {
				String shengxiao = split[0];
				String bushous = split[1];
				String[] bushouArr = bushous.split("、");
				Set<String> set = new HashSet<String>();
				for(String bushou : bushouArr) {
					set.add(bushou);
				}
				result.put(shengxiao, set);
			}
		}
		
		return result;
	}
	
	public static List<String> readLines(String filePath) {
		try {
			Resource res = new ClassPathResource(filePath);
			File file = res.getFile();
			return FileUtils.readLines(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ArrayList<String>();
	}

}
