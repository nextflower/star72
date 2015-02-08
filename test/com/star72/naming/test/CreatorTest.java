package com.star72.naming.test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.star72.common.utils.StarDateUtils;
import com.star72.naming.create.KangXiNameCreator;
import com.star72.naming.dicreader.DicReader;
import com.star72.naming.entity.NameBean;

public class CreatorTest {
	
	
	
	@Test
	public void kangxiCreatorTest() {
		KangXiNameCreator creator = new KangXiNameCreator();
		String xing = "王";
		String midChar = null;
		Boolean isTwoChar = false;
		Boolean needDiezi = false;
		String xishen = "金";
		String shengxiao = "马";
		List<NameBean> list = creator.create(xing, midChar, isTwoChar, needDiezi, xishen, shengxiao);
		
		System.out.println(list.size());
		Random r = new Random();
		int num = r.nextInt(10000);
		File f = new File("F:/TEMP/" + xing + "_" + xishen + "_" + shengxiao + "_" + StarDateUtils.getCurrentDay() + "_" + num + ".txt");
		System.out.println(f.getAbsolutePath());
		
		try {
			FileUtils.writeLines(f, list);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void testSingleName() {
		KangXiNameCreator creator = new KangXiNameCreator();
		NameBean nb = new NameBean();
		nb.setXing("李");
		nb.setMing("鑫");
		boolean flag = creator.validate(nb, "金", "龙");
		System.out.println(flag);
	}
	
}
