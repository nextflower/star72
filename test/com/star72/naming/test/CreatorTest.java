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
		String xing = "李";
		String midChar = null;
		Boolean isTwoChar = false;
		Boolean needDiezi = true;
		String xishen = "土";
		String shengxiao = "蛇";
		List<NameBean> list = creator.create(xing, midChar, isTwoChar, needDiezi, xishen, shengxiao);
		
		for(NameBean nb : list) {
			System.out.println(nb);
		}
	}
	
	@Test
	public void testPrint() {
		PersonNameCreator pnCreator = new PersonNameCreator();
		String xingshen = "金";
		String shengxiao = "龙";
		String xing = "王";
		String ming = "王炳鑫";
		String print = pnCreator.print(xing, ming, xingshen, shengxiao);
		System.out.println(print);
	}
	
	@Test
	public void test() {
		
		WuxingCharLibCreator creator = new WuxingCharLibCreator();
		String charLibPath = "com/star72/naming/dic/wuxingbihua_shiyi.txt";
		CharLib charLib = creator.createCharLib();
		System.out.println(charLib.size());
		PersonNameCreator pnCreator = new PersonNameCreator();
		List<NameBean> list = pnCreator.create(charLib, "王", null, false, true, "金", CharLibConstants.SHENGXIAO_LONG);
		for(NameBean nb : list) {
			String print = pnCreator.print(nb.getXing(), nb.getMing(), "金", CharLibConstants.SHENGXIAO_LONG);
			System.out.println(print);
		}
		System.out.println();
		System.out.println(list.size());
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
