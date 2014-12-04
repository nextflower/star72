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
import com.star72.naming.entity.CharLib;
import com.star72.naming.entity.NameBean;

/**
 * 名字创建器
 * 
 * @author wz
 *
 */
public class PersonNameCreator {
	
	public PersonNameCreator(){}
	
	/**
	 * 
	 * @param lib 字库
	 * @param xing 姓
	 * @param midChar 辈分字
	 * @param isThreeChar 是否为2个字
	 * @param needDiezi 是否允许叠字
	 * @param xishen 喜神
	 * @param shengxiao 生肖
	 * @return
	 */
	public List<NameBean> create(CharLib lib, String xing, String midChar, Boolean isTwoChar, Boolean needDiezi, String xishen, String shengxiao) {
		
		
		List<CharBean> cbList = lib.getCharBeanList();
		
		
		List<NameBean> result = initialList(xing, midChar, cbList, isTwoChar, needDiezi);//根据字库、长度生成结果
		System.out.println("初始list：" + result.size());
		
		result = filterByShengxiao(result, shengxiao);
		System.out.println("filterByShengxiao：" + result.size());
		
		result = filterByXishen(result, xishen);
		System.out.println("filterByXishen：" + result.size());
		
		return result;
	}

	
	/**
	 * 通过xishen筛选
	 * @param cbList
	 * @param xishen
	 * @return
	 */
	private List<NameBean> filterByXishen(List<NameBean> list, String xishen) {
		if(StringUtils.isBlank(xishen)) {
			return list;
		}
		List<NameBean> result = new ArrayList<NameBean>();
		
		Map<String, Set<String>> wuxingHanzi = DicReader.getWuxingHanzi();
		
		Set<String> set = wuxingHanzi.get(xishen);
		
		for(NameBean cb : list) {
			String ming = cb.getMing();
			List<String> mingList = StarStringUtils.parseStr2SingleStrList(ming);
			for(String m : mingList) {
				if(set.contains(m)) {
					result.add(cb);
					break;
				}
			}
		}
		
		return result;
	}

	/**
	 * 通过生肖筛选
	 * 
	 * @param result
	 * @return
	 */
	private List<NameBean> filterByShengxiao(List<NameBean> list, String shengxiao) {
		if(StringUtils.isBlank(shengxiao)) {
			return list;
		}
		List<NameBean> result = new ArrayList<NameBean>();
		Map<String, Set<String>> shengxiaoBushou_xi = DicReader.getShengxiaoBushou_xi();
		Set<String> xiBushouSet = shengxiaoBushou_xi.get(shengxiao);
		
		Map<String, Set<String>> shengxiaoBushou_ji = DicReader.getShengxiaoBushou_ji();
		Set<String> jiBushouSet = shengxiaoBushou_ji.get(shengxiao);
		
		System.out.println("生肖【" + shengxiao + "】可用部首: " + xiBushouSet);
		System.out.println("生肖【" + shengxiao + "】忌用部首: " + jiBushouSet);
		
		Set<String> xiHanziSet = new HashSet<String>();
		Map<String, Set<String>> bushouHanzi = DicReader.getBushouHanzi();
		if(bushouHanzi != null) {
			for(String bushou : xiBushouSet) {
				Set<String> set = bushouHanzi.get(bushou);
				if(set != null) {
					xiHanziSet.addAll(set);
				}
			}
		}
		
		Set<String> jiHanziSet = new HashSet<String>();
		if(bushouHanzi != null) {
			for(String bushou : jiBushouSet) {
				Set<String> set = bushouHanzi.get(bushou);
				if(set != null) {
					jiHanziSet.addAll(set);
				}
			}
		}
		
		for(NameBean cb : list) {
			boolean containsXi = false;
			boolean containsJi = false;
			String ming = cb.getMing();
			List<String> mingList = StarStringUtils.parseStr2SingleStrList(ming);
			for(String m : mingList) {
				if(xiHanziSet.contains(m)) {
					containsXi = true;
				}
				if(m.equals(cb.getMidChar())) {
					containsJi = true;
				}
			}
			
			if(containsXi && !containsJi) {
				result.add(cb);
			}
			
		}
		
		return result;
	}

	/**
	 * 初始化列表
	 * @param xing
	 * @param midChar
	 * @param cbList
	 * @param createCount
	 * @return
	 */
	private List<NameBean> initialList(String xing, String midChar, List<CharBean> cbList, Boolean isTwoChar, Boolean needDiezi) {
		
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
				
				
				result.add(nb);
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
					result.add(nb);
				}
			}
		}
		return result;
	}
	
}
