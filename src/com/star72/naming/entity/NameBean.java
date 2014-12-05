package com.star72.naming.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.star72.common.utils.StarStringUtils;

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
	
	//五格
	private Integer ge_tian = null;
	private Integer ge_di = null;
	private Integer ge_ren = null;
	private Integer ge_wai = null;
	private Integer ge_zong = null;
	
	private String sancai = null;
	
	private boolean hasRightGe = false;
	
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
		return "NameBean [full=" + xing + ming + ", xing=" + xing + ", ming=" + ming + "]," +
				" [tian" + ge_tian + ", ren" + ge_ren + ", di" + ge_di + ", zong" + ge_zong + ", wai" + ge_wai + "]";
	}
	public String getMidChar() {
		return midChar;
	}
	public void setMidChar(String midChar) {
		this.midChar = midChar;
	}
	public Integer getGe_tian() {
		return ge_tian;
	}
	public void setGe_tian(Integer ge_tian) {
		this.ge_tian = ge_tian;
	}
	public Integer getGe_di() {
		return ge_di;
	}
	public void setGe_di(Integer ge_di) {
		this.ge_di = ge_di;
	}
	public Integer getGe_ren() {
		return ge_ren;
	}
	public void setGe_ren(Integer ge_ren) {
		this.ge_ren = ge_ren;
	}
	public Integer getGe_wai() {
		return ge_wai;
	}
	public void setGe_wai(Integer ge_wai) {
		this.ge_wai = ge_wai;
	}
	public Integer getGe_zong() {
		return ge_zong;
	}
	public void setGe_zong(Integer ge_zong) {
		this.ge_zong = ge_zong;
	}
	
	public void setHasRightGe(boolean hasRightGe) {
		this.hasRightGe = hasRightGe;
	}
	
	public boolean hasRightGe() {
		return this.hasRightGe;
	}
	public String getSancai() {
		return sancai;
	}
	public void setSancai(String sancai) {
		this.sancai = sancai;
	}
	
}
