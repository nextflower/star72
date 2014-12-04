package com.star72.naming.charlib;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.star72.naming.entity.CharBean;
import com.star72.naming.entity.CharLib;

/**
 * 根据配置文件生成字库
 * 
 * @author wz
 *
 */
public class WuxingCharLibCreator implements CharLibCreator {

	public CharLib createCharLib() {
		CharLib result = new CharLib();
		String charLibPath = "com/star72/naming/dic/wuxingbihua_shiyi_ji.txt";
		Resource res = new ClassPathResource(charLibPath);
		try {
			File file = res.getFile();
			List<String> list = FileUtils.readLines(file);
			for(String s : list) {
				String[] split = s.split("_");
				if(split.length == 3) {
					String bihua = split[0];
					String charName = split[1];
					String wuxingStr = split[2];
					String[] wuxingArr = wuxingStr.split("：");
					String wuxing = null;
					String description = null;
					if(wuxingArr.length == 2) {
						wuxing = wuxingArr[0];
						description = wuxingArr[1];
					}
					CharBean cb = new CharBean(charName, Integer.parseInt(bihua), wuxing, description);
					result.addCharBean(cb);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static void main(String[] args) {
		WuxingCharLibCreator creator = new WuxingCharLibCreator();
		CharLib lib = creator.createCharLib();
		System.out.println(lib.size());
	}

}
