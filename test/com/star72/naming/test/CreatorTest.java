package com.star72.naming.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.star72.common.utils.DateUtils;
import com.star72.naming.create.KangXiNameCreator;
import com.star72.naming.dicreader.DicReader;
import com.star72.naming.entity.NameBean;

public class CreatorTest {
	
	
	
	@Test
	public void kangxiCreatorTest() {
		KangXiNameCreator creator = new KangXiNameCreator();
		String xing = "陈";
		String midChar = null;
		Boolean isTwoChar = false;
		Boolean needDiezi = true;
		String xishen = "土";
		String shengxiao = null;
		List<NameBean> list = creator.create(xing, midChar, isTwoChar, needDiezi, xishen, shengxiao);
		
		System.out.println(list.size());
		Random r = new Random();
		int num = r.nextInt(10000);
		File f = new File("F:/TEMP/" + xing + "_" + xishen + "_" + shengxiao + "_" + DateUtils.getCurrentDay() + "_" + num + ".txt");
		System.out.println(f.getAbsolutePath());
		
		try {
			FileUtils.writeLines(f, list);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
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
		
//		Map<String, String> kangxiBushou = DicReader.getKangxiBushou();
//		System.out.println(kangxiBushou);
		
		int day = DateUtils.getCurrentDay();
		System.out.println(day);
		
	}
	
	@Test
	public void temp() {
		
		KangXiNameCreator creator = new KangXiNameCreator();
		
		Map<String, Set<String>> kangxiWuxingMap = DicReader.getKangxiWuxingMap();
		Set<String> set_sh = kangxiWuxingMap.get("土");
		String xing = "蒋定";
		
			for(String sh : set_sh) {
				System.out.println(sh);
			}
	}

}
