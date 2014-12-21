package com.star72.naming.test;

import java.util.List;

import org.junit.Test;

import com.star72.common.utils.DomainQueryUtil;
import com.star72.common.utils.PinyinUtil;

public class DomainTest {
	
	@Test
	public void test() {
		List<String> list = PinyinUtil.hanzi2PinyinNoDiao("古代文献");
		//String domain = concat(list, "") + ".com";
		String domain = "shushishenghuo.com";
		System.out.println(domain);
		DomainQueryUtil.queryByWanWang(domain, false);
	}
	
	public String concat(List<String> list, String plus) {
		StringBuffer sb = new StringBuffer();
		for(String s : list) {
			sb.append(s);
		}
		return sb.toString();
	}

}
