package com.star72.toolbox.test;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;

import com.star72.common.utils.StarStringUtils;
import com.star72.toolbox.util.ToolBoxUtils;

public class ToolBoxTest {
	
	@Test
	public void test22() {
		String s = "<p><div id=\"cont\"></p>";
		System.out.println(StarStringUtils.deleteAllHTMLTag(s));
	}

	
	public void fan2jianTest() throws IOException {
		String source = "中华人民共和国张赵";
		String result = ToolBoxUtils.convertJian2Fan(source);
		System.out.println(result);
	}

	public void jian2fanTest() throws IOException {
		String source = "國語日報社自民國92年發起「送報到山巔」公益活動至今，十二年來從未間斷，因為我們相信，這是縮小城鄉差距、拓展偏鄉學童視野的最好方式。";
		String result = ToolBoxUtils.convertFan2Jian(source);
		System.out.println(result);
	}
	
	
	public void testMd5() {
		long t = System.currentTimeMillis();
		for(int i=0; i<Integer.MAX_VALUE; i++) {
			System.out.println(i + "[" + ((System.currentTimeMillis() - t) * 1.0 / (1000)) + "秒]");
			Md5("12312312312312");
		}
	}
	
	private void Md5(String plainText) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}

			//System.out.println("result: " + buf.toString());// 32位的加密

			//System.out.println("result: " + buf.toString().substring(8, 24));// 16位的加密

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

}
