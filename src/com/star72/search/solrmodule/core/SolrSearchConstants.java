package com.star72.search.solrmodule.core;

public class SolrSearchConstants {
	
	/*
	 * 高亮默认属性
	 */
	public static final Boolean HIGHLIGHT_OPEN = false;//是否开启高亮
	public static final Boolean HIGHLIGHT_REQUIREFIELDMATCH = false;//是否需要匹配字段
	public static final String HIGHLIGHT_POST = "</span>";//高亮后缀
	public static final String HIGHLIGHT_PRE = "<span class=\'highlight\'>";//高亮前缀
	public static final Integer HIGHLIGHT_FRAGSIZE = 70;//切片长度
	public static final Integer HIGHLIGHT_SNIPPETS = 3;//切片数量
	
	/*
	 * facet默认属性
	 */
	public static final Boolean FACET_OPEN = false;//是否启用facet查询
	public static final Integer FACET_LIMIT = 100;//限制Facet字段返回的结果条数
	public static final Integer FACET_MIN_COUNT = 0;//限制了Facet字段值的最小count
	public static final Boolean FACET_MISSING = false;//默认为””,如果设置为true或者on,那么将统计那些该Facet字段值为null的记录.
	
	/*
	 * group默认属性
	 */
	public static final Boolean GROUP_OPEN = false;//是否启用group查询
	public static final Integer GROUP_LIMIT = 100;//限制group字段返回的结果条数
	
	
	public static final Boolean SHOW_DEBUG_INFO = false;//是否启用group查询
	
	
	/*
	 * 分页默认属性
	 */
	public static final Integer PAGE_SIZE = 10;//默认页面大小
	public static final Integer PAGE_NO = 0;//默认页码
	public static final Integer PAGE_TOTAL_COUNT = 0;
	
	/*
	 * 入库常量
	 */
	public static final Integer DEFAULT_COMMIT_BATCH = 500;
	
	/*
	 * 默认动态字段的前缀,当项目应用中仅有一个动态字段时,默认使用该值作为其动态字段的前缀
	 * 同时要求在schema.xml中定义类似：<dynamicField name="EpsDynamic_`*" type="string" indexed="true" stored="true"/>
	 */
	public static final String DYNAMIC_FILED_PREFIX = "EpsDynamic_`";
	
}
