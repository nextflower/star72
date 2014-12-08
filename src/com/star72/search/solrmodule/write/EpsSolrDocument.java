package com.star72.search.solrmodule.write;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.common.SolrInputDocument;

import com.star72.search.solrmodule.core.SolrSearchConstants;


/**
 * 应用端索引类,继承自SolrInputDocument,在此基础上增加了一个动态字段的设置
 * @author wz
 *
 */
public class EpsSolrDocument extends SolrInputDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5864576971556167348L;
	
	/**
	 * 增加自定义的动态字段,建议只有在应用中需要多个动态字段的时候使用这种方式
	 * @param field 字段名
	 * @param value 字段值
	 * @param boost 权重
	 * @param prefix 动态字段的前缀,若为null,则使用默认值EpsDynamic_`
	 */
	public void addDynamicFieldBySelf(String field, Object value, String prefix) {
		addDynamicField(field, value, 1.0f, prefix);
	}
	
	/**
	 * 增加自定义的动态字段,建议只有在应用中需要多个动态字段的时候使用这种方式
	 * @param field 字段名
	 * @param value 字段值
	 * @param boost 权重
	 * @param prefix 动态字段的前缀,若为null,则使用默认值EpsDynamic_`
	 */
	public void addDynamicFieldBySelf(String field, Object value, float boost, String prefix) {
		addDynamicField(field, value, boost, prefix);
	}
	
	/**
	 * 增加默认动态字段,会以EpsDynamic_`作为前缀,当应用中只需要一个动态字段的时候建议使用这种方式
	 * @param field 字段名
	 * @param value 字段值
	 */
	public void addDynamicField(String field, Object value) {
		addDynamicField(field, value, 1.0f);
	}
	
	/**
	 * 增加默认动态字段,会以EpsDynamic_`作为前缀,当应用中只需要一个动态字段的时候建议使用这种方式
	 * @param field 字段名
	 * @param value 字段值
	 */
	public void addDynamicField(String field, Object value, float boost) {
		addDynamicField(field, value, boost, null);
	}
	
	/**
	 * 增加动态字段
	 * @param field 字段名
	 * @param value 字段值
	 * @param boost 权重
	 * @param prefix 动态字段的前缀,若为null,则使用默认值EpsDynamic_`
	 */
	private void addDynamicField(String field, Object value, float boost, String prefix) {
		if(StringUtils.isBlank(prefix)) {
			prefix = SolrSearchConstants.DYNAMIC_FILED_PREFIX;
		}
		addField(prefix + field, value, boost);
	}

}
