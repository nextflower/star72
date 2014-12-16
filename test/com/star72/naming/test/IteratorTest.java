package com.star72.naming.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.star72.naming.constants.CharLibConstants;
import com.star72.naming.create.KangXiNameCreator;
import com.star72.naming.dicreader.DicReader;
import com.star72.naming.entity.NameBean;

public class IteratorTest {
	
	private String[] xingSet = new String[]{"丁","于","王","田","任","宋","汪","段","殷","章","程","杨","温","郭","穆","谢","魏","邓","窦","顾","苏","兰"};
	private String[] wuxingSet = new String[]{CharLibConstants.WUXING_JIN,CharLibConstants.WUXING_MU,CharLibConstants.WUXING_SHUI,CharLibConstants.WUXING_HUO,CharLibConstants.WUXING_TU};
	@Test
	public void test() throws IOException {
		
		
		Map<String, Integer> kangxiBihua = DicReader.getKangxiBihua();
		
		Set<String> hanziSet = kangxiBihua.keySet();
		
//		Set<String> mingSet = new HashSet<String>();
		
		KangXiNameCreator creator = new KangXiNameCreator();
		
		
		for(String xing : xingSet) {
			int count = 0;
			List<NameBean> list = new ArrayList<NameBean>();
			for(String hz1 : hanziSet) {
				for(String hz2 : hanziSet) {
					count++;
					NameBean nb = new NameBean();
					nb.setXing(xing);
					nb.setMing(hz1.concat(hz2));
					boolean flag = creator.validateSancaiWuge(nb);
					if(flag) {
						list.add(nb);
					}
				}
			}
			System.out.println(xing + "_" + kangxiBihua.get(xing) + "_" + list.size());
			
			StringBuffer sb = new StringBuffer();
			for(String wuxing : wuxingSet) {
				Set<String> mingset = new HashSet<String>();
				int num = 0;
				
				for(NameBean nb : list) {
					boolean flag = creator.validate(nb, wuxing, null);
					if(flag) {
						sb.append("" + nb.getMing() + " | ");
						num++;
						if(num % 10 == 0) {
							//System.out.println(sb.toString());
							mingset.add(sb.toString());
							sb = new StringBuffer();
						}
						//mingset.add(nb.getMing());
					}
				}
				//System.out.println(sb.toString());
				mingset.add(sb.toString());
				
				FileUtils.writeLines(new File("d:/test/kangxi/lib/" + xing + "_" + kangxiBihua.get(xing) + "_" + wuxing + ".txt"), mingset);
			}
			
		}
		
		
		
	}

}
