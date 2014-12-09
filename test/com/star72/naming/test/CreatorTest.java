package com.star72.naming.test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.star72.naming.charlib.WuxingCharLibCreator;
import com.star72.naming.constants.CharLibConstants;
import com.star72.naming.create.PersonNameCreator;
import com.star72.naming.dicreader.DicReader;
import com.star72.naming.entity.CharLib;
import com.star72.naming.entity.NameBean;

public class CreatorTest {
	
	@Test
	public void testPrint() {
		PersonNameCreator pnCreator = new PersonNameCreator();
		String xingshen = "水";
		String shengxiao = "马";
		String xing = "李";
		String ming = "";
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
//		Map<String, Set<String>> map_ji = DicReader.getShengxiaoBushou_ji();
//		Map<String, Set<String>> map_xi = DicReader.getShengxiaoBushou_xi();
//		System.out.println(map_ji);
//		System.out.println(map_xi);
		
//		Map<String, Set<String>> bushouHanzi = DicReader.getBushouHanzi();
//		System.out.println(bushouHanzi);
		
//		Map<Integer, Set<String>> bushouBihua = DicReader.getBushouBihua();
//		System.out.println(bushouBihua);
		
//		Map<String, Set<String>> wuxingHanzi = DicReader.getWuxingHanzi();
//		System.out.println(wuxingHanzi);
		
//		Map<String, Integer> hanziBihua = DicReader.getHanziBihua();
//		System.out.println(hanziBihua);
//		System.out.println(hanziBihua.size());
		
//		Map<String, Integer> kangxiBihua = DicReader.getKangxiBihua();
//		System.out.println(kangxiBihua);
//		System.out.println(kangxiBihua.size());
		
		Map<String, String> kangxiBushou = DicReader.getKangxiBushou();
		System.out.println(kangxiBushou);
		System.out.println(kangxiBushou.size());
		
	}

}
