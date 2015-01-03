package com.star72.search.test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.wltea.analyzer.solr.IKTokenizerFactory;

import com.star72.common.utils.StarStringUtils;
import com.star72.search.solrmodule.condition.SolrCommonItem;
import com.star72.search.solrmodule.condition.SolrItem;
import com.star72.search.solrmodule.condition.SolrSearchCondition;
import com.star72.search.solrmodule.condition.SolrStringItem;
import com.star72.search.solrmodule.condition.SortedValue;
import com.star72.search.solrmodule.page.SolrResult;
import com.star72.search.solrmodule.query.EPSSolrServerForCommon;
import com.star72.search.solrmodule.write.EpsSolrDocument;

public class ServerWriterTest {
	
	public static final String FIELD_ID = "ID";
	public static final String FIELD_SOURCE = "SOURCE";
	public static final String FIELD_SORT_VALUE = "SORT_VALUE";
	public static final String FIELD_TITLE = "TITLE";
	public static final String FIELD_CONTENT = "CONTENT";
	public static final String FIELD_CAT = "CAT";
	
	private EPSSolrServerForCommon server = new EPSSolrServerForCommon();
	private String rootPath = "G:\\文档\\gudaiwenxian"; 

	@Test
	public void test() {
		
		server.setSolrURL("http://localhost:8080/Solr/gudaiwenxian");
		File rootFile = new File(rootPath);
		if(!rootFile.isDirectory()) {
			return ;
		}
		
		File[] files = rootFile.listFiles();
		if(files != null) {
			for(File f : files) {
				if(!f.getName().contains("诗藏")) {
					System.out.println(f.getName());
					writeToIndex(f);
				}
			}
		}
		
	}

	private void writeToIndex(File f) {
		if(f.isFile()) {
			String absolutePath = f.getAbsolutePath();
			String relativePath = absolutePath.replace(rootPath, "");
			List<String> catList = new ArrayList<String>();
			String[] dirArr = relativePath.split("\\\\");
			for(String s : dirArr) {
				if(StringUtils.isNotBlank(s)) {
					catList.add(s);
				}
			}
			catList.remove(catList.size() - 1);
			
			try {
				List<String> lines = FileUtils.readLines(f);
				int count = 0;
				StringBuffer sb = new StringBuffer();
				for(String line : lines) {
					if(StringUtils.isBlank(line) && StringUtils.isNotBlank(sb.toString()) && sb.length() > 20) {
						count++;
						
						List<String> parts = StarStringUtils.partedStringByLength(sb.toString(), 1000);
//						System.out.println(parts.size());
						for(String content : parts) {
							EpsSolrDocument doc = new EpsSolrDocument();
							doc.addField(FIELD_ID, UUID.randomUUID().toString());
							for(String cat : catList) {
								doc.addField(FIELD_CAT, cat);
							}
							doc.addField(FIELD_SORT_VALUE, count);
							doc.addField(FIELD_SOURCE, FilenameUtils.getBaseName(f.getName()));
							doc.addField(FIELD_CONTENT, content);
							doc.addField(FIELD_TITLE, content.substring(0, 10));
							
							server.addDocument(doc, false);
						}
						
						sb = new StringBuffer(); 
					} else {
						sb.append(line);
					}
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else {
			System.out.println(f.getName());
			File[] files = f.listFiles();
			if(files != null) {
				for(File child : files) {
					writeToIndex(child);
				}
			}
		}
	}
	
	
	@Test
	public void testQuery() {
		
		EPSSolrServerForCommon server = new EPSSolrServerForCommon();
		server.setSolrURL("http://localhost:8080/Solr/gudaiwenxian");
		//单独的查询条件
//		SolrCommonItem item = new SolrCommonItem("CONTENT", "大乘");
		SolrItem item = new SolrStringItem("*:*");
		//分页设置（可选）
		Integer pageNo = 1;
		Integer pageSize = 10;
		
		//设置排序（可选）
		//SortedValue sortedValue = new SortedValue("PUBLISHDATE", SolrQuery.ORDER.desc);
		SolrSearchCondition condition = new SolrSearchCondition(item, null, pageNo, pageSize);//new SolrSearchCondition(item);
		
//		condition.openHighlight("TITLE", "CONTENT");
//		condition.setPageNo(pageNo);
//		condition.setPageSize(pageSize);
		
		condition.openFacet("CAT");
		condition.setFacetLimit(5);
		condition.setFacetSort("CAT");
		
		SolrResult result = server.query(condition);
		
		System.out.println(result);
	}
	
}
