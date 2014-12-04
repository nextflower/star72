package com.star72.naming.charlib;

import com.star72.naming.entity.CharLib;

/**
 * 用于生成不同的字库
 * 
 * @author wz
 *
 */
public interface CharLibCreator {

	/**
	 * 生成字库
	 * @return
	 */
	public CharLib createCharLib();
	
}
