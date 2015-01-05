package com.star72.wenxian.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * 后台查询的结果封装类
 * 
 * @author wz
 *
 */
public class WenxianBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2730092519652358013L;

	private String id;
	private String source;
	private String content;
	private String title;
	private List<String> cats;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<String> getCats() {
		return cats;
	}
	public void setCats(List<String> cats) {
		this.cats = cats;
	}
	@Override
	public String toString() {
		return "WenxianBean [id=" + id + ", source=" + source + ", content="
				+ content + ", title=" + title + ", cats=" + cats + "]";
	}
	
}