package com.star72.naming.utils;

import java.util.List;
import java.util.Map;

import com.star72.common.utils.StarStringUtils;
import com.star72.naming.constants.CharLibConstants;
import com.star72.naming.entity.NameBean;

public class WugeSancaiUtils {
	
	public static String getWuxing(Integer num) {
		if(num == null || num <= 0) {
			return null;
		}
		//1、2mu,3、4huo,5、6tu,7、8jin,9、0shui
		Integer yu = num % 10;
		if(yu == 1 || yu ==2) {
			return CharLibConstants.WUXING_MU;
		} else if(yu == 3 || yu ==4) {
			return CharLibConstants.WUXING_HUO;
		} else if(yu == 5 || yu ==6) {
			return CharLibConstants.WUXING_TU;
		} else if(yu == 7 || yu ==8) {
			return CharLibConstants.WUXING_JIN;
		} else if(yu == 9 || yu ==0) {
			return CharLibConstants.WUXING_SHUI;
		}
		return null;
	}
	
	public static String computeSancai(NameBean nb) {
		Integer tian = nb.getGe_tian();
		Integer ren = nb.getGe_ren();
		Integer di = nb.getGe_di();
		if(tian == null || ren == null || di == null) {
			return null;
		}
		String wuxing_tian = getWuxing(tian);
		String wuxing_ren = getWuxing(ren);
		String wuxing_di = getWuxing(di);
		
		if(wuxing_tian == null || wuxing_ren == null || wuxing_di == null) {
			return null;
		}
		
		String sancai = wuxing_tian.concat(wuxing_ren).concat(wuxing_di);
		nb.setSancai(sancai);
		return sancai;
	}

	public static void computeWuge(NameBean nb, Map<String, Integer> bihuaMap) {
		//格计算方式
		List<String> mingArr = StarStringUtils.parseStr2SingleStrList(nb.getMing().trim());
		List<String> xingArr = StarStringUtils.parseStr2SingleStrList(nb.getXing().trim());
		if(mingArr.size() == 0 || xingArr.size() == 0) {
			return;
		}
		
		boolean isDanXing = nb.getXing().trim().length() == 1;
		boolean isDanMing = mingArr.size() == 1;
		
		//tian
		Integer tian = null;
		if(isDanXing) {
			//dan xing
			Integer bihua = bihuaMap.get(nb.getXing().trim());
			if(bihua != null) {
				tian = bihua + 1;
			} else {
				return;
			}
		} else {
			//fu xing = 
			List<String> strList = StarStringUtils.parseStr2SingleStrList(nb.getXing().trim());
			int sum = 0;
			for(String s : strList) {
				Integer bihua = bihuaMap.get(s);
				if(bihua != null) {
					sum += bihua;
				} else {
					return;
				}
			}
			tian = sum;
		}
		
		//ren
		Integer ren = null;
		if(isDanXing) {
			//dan xing = bihua_xing + bihua_ming_first
			Integer bihua_xing = bihuaMap.get(nb.getXing().trim());
			Integer bihua_ming_first = bihuaMap.get(mingArr.get(0));
			if(bihua_xing != null && bihua_ming_first != null) {
				ren = bihua_xing + bihua_ming_first;
			} else {
				return;
			}
		} else {
			//fu xing
			Integer bihua_xing_last = bihuaMap.get(xingArr.get(xingArr.size() - 1));
			Integer bihua_ming_first = bihuaMap.get(mingArr.get(0));
			if(bihua_xing_last != null && bihua_ming_first != null) {
				ren = bihua_xing_last + bihua_ming_first;
			} else {
				return;
			}
		}
		
		//di
		Integer di = null;
		if(isDanMing) {
			Integer bihua_ming = bihuaMap.get(nb.getXing().trim());
			if(bihua_ming != null) {
				di = bihua_ming + 1;
			} else {
				return;
			}
		} else {
			int sum = 0;
			for(String s : mingArr) {
				Integer bihua = bihuaMap.get(s);
				if(bihua != null) {
					sum += bihua;
				} else {
					return;
				}
			}
			di = sum;
		}
		
		//zong
		Integer zong = 0;
		{
			for(String s : xingArr) {
				Integer bihua = bihuaMap.get(s);
				if(bihua != null) {
					zong += bihua;
				} else {
					return;
				}
			}
			for(String s : mingArr) {
				Integer bihua = bihuaMap.get(s);
				if(bihua != null) {
					zong += bihua;
				} else {
					return;
				}
			}
		}
		
		//wai
		Integer wai = null;
		if(isDanXing && isDanMing) {//dan xing + dan ming
			wai = 2;
		} else if(isDanXing && !isDanMing) {//dan xing + fu ming
			wai = zong - ren + 1;
		} else if(!isDanXing && isDanMing) {//fu xing + dan ming
			wai = zong - ren + 1;
		} else if(!isDanXing && isDanMing) {//fu xing + fu ming
			wai = zong - ren;
		}
		
		nb.setGe_tian(tian);
		nb.setGe_ren(ren);
		nb.setGe_di(di);
		nb.setGe_wai(wai);
		nb.setGe_zong(zong);
		
		nb.setHasRightGe(true);
		
	}
	
	
	public static boolean validateWuge(NameBean nb, Map<Integer, String> wugeshuli_ji) {
		
		if(!nb.hasRightGe()) {
			return false;
		}
		
		/*
		if(wugeshuli_ji.get(nb.getGe_tian()) == null) {
			return false;
		}
		*/
		
		if(wugeshuli_ji.get(nb.getGe_ren()) == null) {
			return false;
		}
		
		if(wugeshuli_ji.get(nb.getGe_di()) == null) {
			return false;
		}
		
		/*
		if(wugeshuli_ji.get(nb.getGe_wai()) == null) {
			return false;
		}
		*/
		
		if(wugeshuli_ji.get(nb.getGe_zong()) == null) {
			return false;
		}
		
		return true;
	}
	
}
