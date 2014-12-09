package com.star72.naming.test;

import java.util.Map;

import org.junit.Test;

import com.star72.naming.dicreader.DicReader;

public class BihuaTest {
	
	@Test
	public void test() {
		Map<String, Integer> hanziBihua_quan = DicReader.getHanziBihua_quan();
		Map<String, Integer> wuxingBihua_shiyi = DicReader.getWuxingBihua_shiyi();
		for(String s : wuxingBihua_shiyi.keySet()) {
			Integer bihua = wuxingBihua_shiyi.get(s);
			Integer bh2 = hanziBihua_quan.get(s);
			if(bh2 == null || bihua == null) {
				continue;
			}
			
			if(bihua != bh2) {
				System.out.println(s + "_" + bihua + "_" + bh2);
			}
		}
	}

}
