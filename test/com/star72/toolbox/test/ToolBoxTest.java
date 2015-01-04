package com.star72.toolbox.test;

import java.io.IOException;

import org.junit.Test;

import com.star72.toolbox.util.ToolBoxUtils;

public class ToolBoxTest {

	@Test
	public void fan2jianTest() throws IOException {
		String source = "中华人民共和国张赵";
		String result = ToolBoxUtils.convertJian2Fan(source);
		System.out.println(result);
	}
	
	@Test
	public void jian2fanTest() throws IOException {
		String source = "國語日報社自民國92年發起「送報到山巔」公益活動至今，十二年來從未間斷，因為我們相信，這是縮小城鄉差距、拓展偏鄉學童視野的最好方式。";
		String result = ToolBoxUtils.convertFan2Jian(source);
		System.out.println(result);
	}
	
}
