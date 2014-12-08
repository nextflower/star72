package com.star72.search.solrmodule.utils;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.solr.client.solrj.SolrQuery;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class SolrUtils {
	
	public static int HASH_SIZE = 64;
	
	private static Integer DEFAULT_PAGE_SIZE = 20;
	
	public static final String JOIN_CHAR_FIRST = "`";
	public static final String JOIN_CHAR_SECOND = ";";
	
	public static long returnSimHash(String source) {
		return returnSimHash(statisticsWordFq(source));
	}
	
	/**
	 * 生成simHash
	 * @param map
	 * @return
	 */
	public static long returnSimHash(Map<String,Integer> map) {
		long simhash = 0;
		if(map!=null&&map.size()>0)
		{
			int[] v = new int[64];
			
			Set<String> set = map.keySet();
			String key = null;
			for(Iterator<String> it = set.iterator();it.hasNext();)
			{
				key = it.next(); 
				
				long hashLong = HashUtil.mixHash(key);
				for (int i = 0; i < HASH_SIZE; ++i) {
					boolean bitSet = ((hashLong >> i) & 1L) == 1L;
					v[i] += (bitSet) ? map.get(key) : -map.get(key);
				}
			}
			
		    for (int i = 0; i < HASH_SIZE; ++i) {
		      if (v[i] > 0) {
		        simhash |= (1L << i);
		      }
		    }
		}
		return simhash;
	}
	
	/**
	 * 计算海明距离
	 * 
	 * @param hash1
	 * @param hash2
	 * @return
	 */
	public static int hammingDistance(long hash1, long hash2) {
		long bits = hash1 ^ hash2;
		int count = 0;
		while (bits != 0) {
			bits &= bits - 1;
			++count;
		}
		return count;
	}
	
	
	/**
	 * 生成相似度,该算法是改进版的空间向量模型，
	 * 参考文献http://www.docin.com/p-581112695.html：一种改进的基于向量空间文本相似度算法的研究与实现
	 * 算法公式：在普通空间向量计算公式的基础上 * b * (两篇文档中重复的特征值数量) / (包含特征值较少的文档的特征值数量)
	 * 其中b是一个比例因子，这里设置为1
	 * 
	 * 测试结果更接近实际
	 * 
	 * @param tmap1 格式：  中国=2，每个=1...
	 * @param tmap2 格式：  中国=2，每个=1...
	 * @return
	 */
	public static double computeSimilarity(Map<String,Integer> tmap1,Map<String,Integer> tmap2) {
		
		if(tmap1 == null || tmap2 == null) {
			return -1;
		}
		
		double b = 1;
		
		double sqdoc1 = 0;
		double sqdoc2 = 0;
		double denominator = 0;
		
		Set<String> set = new HashSet<String>();
		
		for(String key : tmap1.keySet()) {
			set.add(key);
		}
		
		for(String key : tmap2.keySet()) {
			set.add(key);
		}
		
		for(String s : set) {
			
			Integer freq1 = tmap1.get(s);
			Integer freq2 = tmap2.get(s);

			if(freq1==null) {
				freq1 = 0;
			} 
			
			if(freq2==null) {
				freq2 = 0;
			} 
			
			denominator += freq1 * freq2;
			
			sqdoc1 += freq1 * freq1;
			
			sqdoc2 += freq2 * freq2;

		}
		
		if(sqdoc1 == 0 || sqdoc2==0) {
			return 0;
		}
		
		/*
		 * 两篇文档中都存在的特征值数量
		 */
		int sameWordCount = getSameTermList(tmap1, tmap2, true).size();
		
		/*
		 * 包含特征值较少的文档的特征值数量
		 */
		int minCount = tmap1.size() < tmap2.size() ? tmap1.size() : tmap2.size();
		
		return denominator / Math.sqrt(sqdoc1 * sqdoc2) * b * sameWordCount / minCount;
		
	}
	
	/**
	 * 统计词频
	 * 
	 * @param source 需要分词的文本
	 * @return
	 */
	@Deprecated
	public static String statisticsWordFqToString(String source) {
		
		Map<String, Integer> map = statisticsWordFq(source);
		
		if(map.size() == 0) {
			return "";
		}
		
		StringBuffer sb = new StringBuffer();
		
		int count = 0;
		
		for(String key : map.keySet()) {
			count++;
			Integer value = map.get(key);
			if(value != null && count < map.size()) {
				sb.append(key + "`"  + value + "`");
			} else {
				sb.append(key + "`"  + value);
			}
		}
		
		return sb.toString();
	}
	
	
	/**
	 * 统计词频
	 * 
	 * @param source 需要分词的文本
	 * @return
	 */
	public static Map<String,Integer> statisticsWordFq(String source) {
		Map<String,Integer> map = new HashMap<String, Integer>();
		
		if(source == null) {
			return map;
		}
		
		//获取分词器
		Analyzer analyzer = new IKAnalyzer();
		StringReader sr = new StringReader(source);
		TokenStream ts = analyzer.tokenStream(null, sr);
		ts.addAttribute(CharTermAttribute.class);
		
		try {
			while (ts.incrementToken()) {
				
				CharTermAttribute charTermAttribute = ts
						.getAttribute(CharTermAttribute.class);
				String key = charTermAttribute.toString();
				
				if(map.containsKey(key))
				{
					map.put(key, map.get(key)+1);
				}else
				{
					map.put(key, new Integer(1));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally
		{
			sr.close();
		}
		return map;
	}
	
	/**
	 * 将词频信息生成为文本
	 * 
	 * @param source
	 * @return
	 */
	public static String statisticsWordFqStr(String source) {
		Map<String, Integer> fqMap = statisticsWordFq(StrUtils.deleteAllHTMLTag(source));
		StringBuffer buf = new StringBuffer();
		for(String key : fqMap.keySet()) {
			buf.append(key + JOIN_CHAR_FIRST + fqMap.get(key) + JOIN_CHAR_SECOND);
		}
		return buf.toString();
	}
	
	/**
	 * 分词结果
	 * 
	 * @param source
	 * @return
	 */
	public static Set<String> getAnalysisTokens(String source) {
		Set<String> result = new HashSet<String>();
		
		if(source == null) {
			return result;
		}
		
		//获取分词器
		Analyzer analyzer = new IKAnalyzer();
		StringReader sr = new StringReader(source);
		TokenStream ts = analyzer.tokenStream(null, sr);
		ts.addAttribute(CharTermAttribute.class);
		
		try {
			while (ts.incrementToken()) {
				CharTermAttribute charTermAttribute = ts.getAttribute(CharTermAttribute.class);
				String key = charTermAttribute.toString();
				result.add(key);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally
		{
			sr.close();
		}
		
		return result;
	}
	
	public static List<String> getAnalysisTokensToList(String source) {
		List<String> result = new ArrayList<String>();
		
		if(source == null) {
			return result;
		}
		
		//获取分词器
		Analyzer analyzer = new IKAnalyzer();
		StringReader sr = new StringReader(source);
		TokenStream ts = analyzer.tokenStream(null, sr);
		ts.addAttribute(CharTermAttribute.class);
		
		try {
			while (ts.incrementToken()) {
				CharTermAttribute charTermAttribute = ts.getAttribute(CharTermAttribute.class);
				String key = charTermAttribute.toString();
				result.add(key);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally
		{
			sr.close();
		}
		
		return result;
	}
	
	
	/**
	  * 将数据库CLOB对象中的文本读取到String中.
	  * 
	  * @param clob 数据库clob对象 
	  * @return
	  */
	public static String getCLOBString(Clob clob) {
		
		if(clob == null) {
			return "";
		}
		
		StringBuilder sb = new StringBuilder();
		
		try {
			
			Reader in = clob.getCharacterStream();
			
			int len = 0;
			
			char[] buf = new char[1024];
			
			while((len = in.read(buf)) != -1) {
				sb.append(buf,0,len);
			}
			in.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}


	/**
	 * 设置高亮字段
	 * 
	 * @param query 查询对象
	 * @param highLightFields 需要高亮的字段
	 */
	public static void setHighLight(SolrQuery q, String[] highLightFields) {
		
		if(highLightFields == null) {
			return;
		}
		
		q.setHighlight(true);
		
		q.setHighlightFragsize(70);
		
		q.setHighlightRequireFieldMatch(true);
		
		q.setHighlightSnippets(1);
		
		q.setHighlightSimplePost("</span>");
		
		q.setHighlightSimplePre("<span class=\'highlight\'>");
		
		for(String field:highLightFields)
		{
			q.addHighlightField(field);
		}
	}


	/**
	 * 生成基本查询语句
	 * 
	 * @param keyword 关键字
	 * @param fields 字段
	 * @return
	 */
	public static String createBaseQueryString(String keyword, String[] fields) {
		StringBuffer sb = new StringBuffer();
		
		for(String field : fields) {
			sb.append(field + ":" +  keyword + " ");
		}
		
		return sb.toString();
	}


	/**
	 * 获取term
	 * @param tmap1
	 * @param tmap2
	 * @param b 如果为true,返回相同的term;如果为false,返回不同的term
	 * @return
	 */
	public static List<String> getSameTermList(Map<String, Integer> tmap1,Map<String, Integer> tmap2, boolean b) {
		
		List<String> same = new ArrayList<String>();
		List<String> different = new ArrayList<String>();
		
		Set<String> set = new HashSet<String>();
		
		for(String key : tmap1.keySet()) {
			set.add(key);
		}
		
		for(String key : tmap2.keySet()) {
			set.add(key);
		}
		
		for(String key : set) {
			
			if(tmap1.containsKey(key) && tmap2.containsKey(key)) {
				same.add(key);	
			} else {
				different.add(key);
			}
		}
		return b ? same : different;
	}


	public static void setHighLight(String solr_home, String solrLibName,SolrQuery params, String[] highlightFields) {
		//...
	}


	public static void setPageInfo(SolrQuery query, Integer start, Integer rows) {
		/*
		 * 分页控制
		 */
		if(start == null) {
			start = 0;
		}
		
		if(rows == null) {
			rows = DEFAULT_PAGE_SIZE;
		}
		
		query.setStart(start);
		query.setRows(rows);
	}



}
