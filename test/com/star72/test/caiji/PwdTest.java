package com.star72.test.caiji;

import org.junit.Test;

import com.star72.cmsmain.common.security.encoder.Md5PwdEncoder;

public class PwdTest {
	
	@Test
	public void test() {
		//5f4dcc3b5aa765d61d8327deb882cf99
		//5f4dcc3b5aa765d61d8327deb882cf99
		Md5PwdEncoder en = new Md5PwdEncoder();
		String pwd = en.encodePassword("star_!@#1");
		System.out.println(pwd);
	}

}
