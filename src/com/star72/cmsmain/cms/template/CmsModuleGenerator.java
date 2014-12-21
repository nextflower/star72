package com.star72.cmsmain.cms.template;


public class CmsModuleGenerator {
	private static String packName = "com.star72.cmsmain.cms.template";
	private static String fileName = "star72.properties";

	public static void main(String[] args) {
		new ModuleGenerator(packName, fileName).generate();
	}
}
