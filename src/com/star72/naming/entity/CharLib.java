package com.star72.naming.entity;

import java.util.ArrayList;
import java.util.List;


/**
 * 字库实体类
 * 
 * @author wz
 *
 */
public class CharLib {
	
	private final List<CharBean> list = new ArrayList<CharBean>();
	
	public CharLib() {
		
	}
	
	public void addCharBean(CharBean cb) {
		list.add(cb);
	}
	
	public List<CharBean> getCharBeanList() {
		return this.list;
	}
	
	public Integer size() {
		return this.list.size();
	}
	
	public List<CharBean> getListByBihuaAndWuxing(Integer bihua, String wuxing) {
		List<CharBean> result = new ArrayList<CharBean>();
		return result;
	}

}
