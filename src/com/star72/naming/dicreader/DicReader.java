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
	
	private static Map<String, String> KangxiBushou = null;
	private static Map<String, Set<String>> KangxiWuxingMap = null;
	private static Map<String, Integer> KangxiBihua = null;
	private static Map<String, Set<String>> KangxiBushouMap = null;
	private static Map<String, Set<String>> ShengxiaoBushou_xi = null;
	private static Map<String, Set<String>> ShengxiaoBushou_ji = null;
	private static Set<String> Xingshi_set = null;
	
	private DicReader() {}
	
	public static Set<String> getXingShi() {
		if(Xingshi_set != null) {
			return Xingshi_set;
		}
		String dicPath = "com/star72/naming/dic/xingshidaquanpaiming.txt";
		Set<String> result = new HashSet<String>();
		List<String> lines = readLines(dicPath);
		for(String line : lines) {
			List<String> list = StarStringUtils.parseStr2SingleStrList(line);
			for(String s : list) {
//				if(StringUtils)
			}
		}
		return result;
	}
	
	public static Map<String, Set<String>> getKangxiBushouMap() {
		if(KangxiBushouMap != null) {
			return KangxiBushouMap;
		}
		String dicPath = "com/star72/naming/dic/kangxi_changyong.txt";
		Map<String, Set<String>> result = new HashMap<String, Set<String>>();
		List<String> lines = readLines(dicPath);
		for(String line : lines) {
			String[] split = line.split("_");
			String bushou = split[3];
			Set<String> set = result.get(bushou);
			if(set == null) {
				set = new HashSet<String>();
				result.put(bushou, set);
			}
			set.add(split[1]);
		}
		KangxiBushouMap = result;
		return result;
	}
	
	public static Map<String, String> getKangxiBushou() {
		if(KangxiBushou != null) {
			return KangxiBushou;
		}
		String dicPath = "com/star72/naming/dic/kangxi_changyong.txt";
		Map<String, String> result = new HashMap<String, String>();
		List<String> lines = readLines(dicPath);
		for(String line : lines) {
			String[] split = line.split("_");
			result.put(split[1], split[3]);
		}
		KangxiBushou = result;
		return result;
	}
	
	public static Map<String, Set<String>> getKangxiWuxingMap() {
		if(KangxiWuxingMap != null) {
			return KangxiWuxingMap;
		}
		String dicPath = "com/star72/naming/dic/kangxi_changyong.txt";
		Map<String, Set<String>> result = new HashMap<String, Set<String>>();
		List<String> lines = readLines(dicPath);
		for(String line : lines) {
			String[] split = line.split("_");
			String wuxing = split[6];
			Set<String> set = result.get(wuxing);
			if(set == null) {
				set = new HashSet<String>();
				result.put(wuxing, set);
			}
			set.add(split[1]);
		}
		KangxiWuxingMap = result;
		return result;
	}
	
	public static Map<String, Integer> getKangxiBihua() {
		if(KangxiBihua != null) {
			return KangxiBihua;
		}
		String dicPath = "com/star72/naming/dic/kangxi_changyong.txt";
		Map<String, Integer> result = new HashMap<String, Integer>();
		List<String> lines = readLines(dicPath);
		for(String line : lines) {
			String[] split = line.split("_");
			result.put(split[1], Integer.parseInt(split[0]));
		}
		KangxiBihua = result;
		return result;
	}
	
	private static Map<String, Integer> WuxingBihua_shiyi = null;
	public static Map<String, Integer> getWuxingBihua_shiyi() {
		if(WuxingBihua_shiyi != null) {
			return WuxingBihua_shiyi;
		}
		String dicPath = "com/star72/naming/dic/wuxingbihua_shiyi.txt";
		Map<String, Integer> result = new HashMap<String, Integer>();
		List<String> lines = readLines(dicPath);
		for(String line : lines) {
			String[] split = line.split("：");
			if(split.length == 2) {
				String shuxingStr = split[0];
				String[] shuxingArr = shuxingStr.split("_");
				String hanzi = shuxingArr[1];
				Integer bihua = Integer.parseInt(shuxingArr[0]);
				result.put(hanzi, bihua);
			}
		}
		WuxingBihua_shiyi = result;
		return result;
	}
	
	private static Map<String, String> WuxingDecsription_shiyi = null;
	public static Map<String, String> getWuxingDecsription_shiyi() {
		if(WuxingDecsription_shiyi != null) {
			return WuxingDecsription_shiyi;
		}
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
		WuxingDecsription_shiyi = result;
		return result;
	}
	
	private static Map<String, String> Sancai_ji = null;
	public static Map<String, String> getSancai_ji() {
		if(Sancai_ji != null) {
			return Sancai_ji;
		}
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
		Sancai_ji = result;
		return result;
	}
	
	private static Map<Integer, String> WugeShuli_ji = null;
	public static Map<Integer, String> getWugeShuli_ji() {
		if(WugeShuli_ji != null) {
			return WugeShuli_ji;
		}
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
		WugeShuli_ji = result;
		return result;
	}
	
	private static Map<String, Integer> HanziBihua_quan = null;
	public static Map<String, Integer> getHanziBihua_quan() {
		if(HanziBihua_quan != null) {
			return HanziBihua_quan;
		}
		String dicPath = "com/star72/naming/dic/kangxi.txt";
		Map<String, Integer> result = new HashMap<String, Integer>();
		List<String> lines = readLines(dicPath);
		for(String line : lines) {
			String[] split = line.split("_");
			result.put(split[1], Integer.parseInt(split[0]));
		}
		HanziBihua_quan = result;
		return result;
	}
	
	private static Map<String, Set<String>> WuxingHanzi = null;
	public static Map<String, Set<String>> getWuxingHanzi() {
		if(WuxingHanzi != null) {
			return WuxingHanzi;
		}
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
		WuxingHanzi = result;
		return result;
	}
	
	private static Map<Integer, Set<String>> BushouBihua = null;
	public static Map<Integer, Set<String>> getBushouBihua() {
		if(BushouBihua != null) {
			return BushouBihua;
		}
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
		BushouBihua = result;
		return result;
	}
	
	private static Map<String, Set<String>> BushouHanzi = null;
	public static Map<String, Set<String>> getBushouHanzi() {
		if(BushouHanzi != null) {
			return BushouHanzi;
		}
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
		BushouHanzi = result;
		return result;
	}
	
	public static Map<String, Set<String>> getShengxiaoBushou_xi() {
		if(ShengxiaoBushou_xi != null) {
			return ShengxiaoBushou_xi;
		}
		String dicPath = "com/star72/naming/dic/shengxiaobushou_xi.txt";
		ShengxiaoBushou_xi = getShengxiaoBushou(dicPath);
		return ShengxiaoBushou_xi;
	}
	
	public static Map<String, Set<String>> getShengxiaoBushou_ji() {
		if(ShengxiaoBushou_ji != null) {
			return ShengxiaoBushou_ji;
		}
		String dicPath = "com/star72/naming/dic/shengxiaobushou_ji.txt";
		ShengxiaoBushou_ji = getShengxiaoBushou(dicPath);
		return ShengxiaoBushou_ji;
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
