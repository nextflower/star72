package com.star72.naming.test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.star72.naming.charlib.WuxingCharLibCreator;
import com.star72.naming.constants.CharLibConstants;
import com.star72.naming.create.KangXiNameCreator;
import com.star72.naming.create.PersonNameCreator;
import com.star72.naming.dicreader.DicReader;
import com.star72.naming.entity.CharLib;
import com.star72.naming.entity.NameBean;

public class CreatorTest {
	
	
	
	@Test
	public void kangxiCreatorTest() {
		KangXiNameCreator creator = new KangXiNameCreator();
		String xing = "王";
		String midChar = null;
		Boolean isTwoChar = true;
		Boolean needDiezi = true;
		String xishen = "金";
		String shengxiao = "龙";
		List<NameBean> list = creator.create(xing, midChar, isTwoChar, needDiezi, xishen, shengxiao);
		
		System.out.println(list.size());
		
	}
	
	@Test
	public void testSingleName() {
		KangXiNameCreator creator = new KangXiNameCreator();
		NameBean nb = new NameBean();
		nb.setXing("李");
		nb.setMing("鑫");
		boolean flag = creator.validate(nb, "金", "龙");
		System.out.println(flag);
	}
	
	@Test
	public void readerTest() {
		
//		Map<String, Integer> kangxiBihua = DicReader.getKangxiBihua();
//		System.out.println(kangxiBihua);
		
//		Map<String, Set<String>> kangxiWuxingMap = DicReader.getKangxiWuxingMap();
//		System.out.println(kangxiWuxingMap);
//		System.out.println(kangxiWuxingMap.keySet());
		
		Map<String, String> kangxiBushou = DicReader.getKangxiBushou();
		System.out.println(kangxiBushou);
		
	}

}
