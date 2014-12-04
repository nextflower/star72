package com.star72.naming.entity;

import java.io.Serializable;

/**
 * 最终生成的名字实体类
 * 
 * @author wz
 *
 */
public class NameBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NameBean() {}
	
	private String xing;//姓
	private String ming;//名
	private String midChar;//中间

	public String getXing() {
		return xing;
	}
	public void setXing(String xing) {
		this.xing = xing;
	}
	public String getMing() {
		return ming;
	}
	public void setMing(String ming) {
		this.ming = ming;
	}
	@Override
	public String toString() {
		return "NameBean [full=" + xing + ming + ", xing=" + xing + ", ming=" + ming + "]";
	}
	public String getMidChar() {
		return midChar;
	}
	public void setMidChar(String midChar) {
		this.midChar = midChar;
	}

}
