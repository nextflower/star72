package com.star72.naming.create;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.star72.common.utils.StarStringUtils;
import com.star72.naming.dicreader.DicReader;
import com.star72.naming.entity.CharBean;
import com.star72.naming.entity.NameBean;
import com.star72.naming.utils.WugeSancaiUtils;

/**
 * 
 * 根据康熙字典生成
 * 
 * @author wz
 *
 */
public class KangXiNameCreator {
	
	public List<NameBean> create(String xing, String midChar, Boolean isTwoChar, Boolean needDiezi, String xishen, String shengxiao) {
		
		List<CharBean> cbList = generateCharBeanList();
		
		List<NameBean> list = initialList(xing, midChar, cbList, isTwoChar, needDiezi, xishen, shengxiao);
		
		return list;
	}

	public boolean validate(NameBean nb, String xishen, String shengxiao) {
		
		//validate sheng xiao
		if(!validateShengxiao(nb, shengxiao)) {
			return false;
		}
		
		//validate xi shen
		if(!validateXishen(nb, xishen)) {
			return false;
		}
		
		System.out.println(nb);
		
		//validate san cai wu ge
		if(!validateSancaiWuge(nb)) {
			return false;
		}
		
		System.out.println(nb);
		
		return true;
	}
	
	public boolean validateSancaiWuge(NameBean nb) {
		Map<String, Integer> kangxiBihua = DicReader.getKangxiBihua();
		
		Map<Integer, String> wugeShuli_ji = DicReader.getWugeShuli_ji();
		Map<String, String> sancai_ji = DicReader.getSancai_ji();
		//计算各种格
		WugeSancaiUtils.computeWuge(nb, kangxiBihua);
		if(nb.hasRightGe()) {
			//各种格过滤
			if(WugeSancaiUtils.validateWuge(nb, wugeShuli_ji)) {
				
				//计算三才
				String sancai = WugeSancaiUtils.computeSancai(nb);
				
				//三才过滤
				String description = sancai_ji.get(sancai);
				if(description != null) {
					return true;
				}
				
			}
		}
			
		return false;
	}

	private boolean validateXishen(NameBean nb, String xishen) {
		if(StringUtils.isBlank(xishen)) {
			return false;
		}
		Map<String, Set<String>> kangxiWuxingMap = DicReader.getKangxiWuxingMap();
		Set<String> set = kangxiWuxingMap.get(xishen);
		String ming = nb.getMing();
		List<String> mingList = StarStringUtils.parseStr2SingleStrList(ming);
		for(String m : mingList) {
			if(set.contains(m)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean validateShengxiao(NameBean nb, String shengxiao) {
		if(StringUtils.isBlank(shengxiao)) {
			return true;
		}
		Map<String, Set<String>> shengxiaoBushou_xi = DicReader.getShengxiaoBushou_xi();
		Set<String> xiBushouSet = shengxiaoBushou_xi.get(shengxiao);
		
		Map<String, Set<String>> shengxiaoBushou_ji = DicReader.getShengxiaoBushou_ji();
		Set<String> jiBushouSet = shengxiaoBushou_ji.get(shengxiao);
		
		//System.out.println("生肖【" + shengxiao + "】可用部首: " + xiBushouSet);
		//System.out.println("生肖【" + shengxiao + "】忌用部首: " + jiBushouSet);
		
		Set<String> xiHanziSet = new HashSet<String>();
		Map<String, Set<String>> kangxiBushouMap = DicReader.getKangxiBushouMap();
		if(kangxiBushouMap != null) {
			for(String bushou : xiBushouSet) {
				Set<String> set = kangxiBushouMap.get(bushou);
				if(set != null) {
					xiHanziSet.addAll(set);
				}
			}
		}
		
		Set<String> jiHanziSet = new HashSet<String>();
		if(kangxiBushouMap != null) {
			for(String bushou : jiBushouSet) {
				Set<String> set = kangxiBushouMap.get(bushou);
				if(set != null) {
					jiHanziSet.addAll(set);
				}
			}
		}
		
		boolean containsXi = false;
		boolean containsJi = false;
		String ming = nb.getMing();
		List<String> mingList = StarStringUtils.parseStr2SingleStrList(ming);
		for(String m : mingList) {
			if(xiHanziSet.contains(m)) {
				containsXi = true;
			}
			if(m.equals(nb.getMidChar())) {
				containsJi = true;
			}
		}
		
		if(containsXi && !containsJi) {
			return true;
		}
			
		return false;
	}
	
	private List<NameBean> initialList(String xing, String midChar, List<CharBean> cbList, Boolean isTwoChar, Boolean needDiezi, String xishen, String shengxiao) {
		
		Integer createCount = 2;//需要生成的总字数
		if(!StringUtils.isBlank(midChar)) {
			createCount = 1;
		} else if(isTwoChar != null && isTwoChar) {
			createCount = 1;
		}
		
		List<NameBean> result = new ArrayList<NameBean>();
		if(createCount == 1) {
			for(CharBean cb : cbList) {
				NameBean nb = new NameBean();
				nb.setXing(xing);
				if(!StringUtils.isBlank(midChar)) {
					//不需要叠字
					if(needDiezi == null || !needDiezi) {
						if(midChar.equals(cb.getName())) {
							continue;
						}
					}
					nb.setMing(midChar.concat(cb.getName()));
					nb.setMidChar(midChar);
				} else {
					nb.setMing(cb.getName());
				}
				
				if(validate(nb, xishen, shengxiao)) {
					result.add(nb);
				}
				
			}
		} else {
			for(CharBean cb1 : cbList) {
				for(CharBean cb2 : cbList) {
					//不需要叠字
					if(needDiezi == null || !needDiezi) {
						if(cb1.getName().equals(cb2.getName())) {
							continue;
						}
					}
					NameBean nb = new NameBean();
					nb.setXing(xing);
					nb.setMing(cb1.getName().concat(cb2.getName()));
					
					if(validate(nb, xishen, shengxiao)) {
						result.add(nb);
					}
				}
			}
		}
		return result;
	}


	private List<CharBean> generateCharBeanList() {
		List<CharBean> result = new ArrayList<CharBean>();
		Map<String, Set<String>> kangxiWuxingMap = DicReader.getKangxiWuxingMap();
		Map<String, Integer> kangxiBihua = DicReader.getKangxiBihua();
		for(String wuxing : kangxiWuxingMap.keySet()) {
			Set<String> set = kangxiWuxingMap.get(wuxing);
			for(String hanzi : set) {
				Integer bihua = kangxiBihua.get(hanzi);
				CharBean cb = new CharBean(hanzi, bihua, wuxing, "");
				result.add(cb);
			}
		}
		return result;
	}

}
