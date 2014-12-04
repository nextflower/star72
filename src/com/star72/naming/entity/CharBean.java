package com.star72.naming.entity;

import java.io.Serializable;

/**
 * 封装了相关属性的汉字实体类
 * 
 * @author wz
 *
 */
public class CharBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CharBean() {}
	
	public CharBean(String name, Integer bihua, String wuxing, String description) {
		setName(name);
		setBihua(bihua);
		setWuxing(wuxing);
		setDescription(description);
	}
	
	private String name;
	private Integer bihua;
	private String wuxing;
	private String description;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getBihua() {
		return bihua;
	}
	public void setBihua(Integer bihua) {
		this.bihua = bihua;
	}
	public String getWuxing() {
		return wuxing;
	}
	public void setWuxing(String wuxing) {
		this.wuxing = wuxing;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

}
