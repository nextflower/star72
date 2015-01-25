package com.star72.wenxian.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.star72.cmsmain.cms.entity.main.Content;
import com.star72.cmsmain.cms.entity.main.ContentExt;
import com.star72.cmsmain.cms.entity.main.ContentTxt;
import com.star72.common.utils.StarFileUtils;
import com.star72.common.utils.StarStringUtils;

/**
 * 古典文献初始导入
 * 
 * @author larry
 *
 */
@Controller
public class WenXianImportAct {

	@RequiresPermissions("wenxian:import")
	@RequestMapping("/wenxian/import.do")
	public void view(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws IOException {
		
		importData();
		
	}
	
	public void importData() throws IOException {
		
		Set<String> catSet = new HashSet<String>();
		catSet.add("易藏");
		catSet.add("儒藏");
		catSet.add("道藏");
		catSet.add("佛藏");
		catSet.add("子藏");
		catSet.add("史藏");
		catSet.add("诗藏");
		catSet.add("集藏");
		catSet.add("医藏");
		catSet.add("艺藏");
		catSet.add("诗歌");
		
		String pathRaw = "G:\\文档\\gudaiwenxian";//原始文件夹
		String pathMake = "G:\\文档\\gudian\\首页";//加工后的文件夹
		
		
		File rawFile = new File(pathRaw);
		File makeFile = new File(pathMake);
		
		//生成原始文件和加工后文件的映射map,若无对应,则为null
		Map<File, File> map = getCompareMap(rawFile, makeFile);
		
		int count = 0;
		
		for(File file : map.keySet()) {
			count++;
			File value = map.get(file);
			String author = null;//作者
			String chaodai = null;//朝代
			String source = null;//来源图书
			String ap = file.getAbsolutePath();
			String[] pathArr = ap.split("\\\\");
			String rootCat = null;//顶级分类
			String childCats = null;//子级分类
			StringBuffer catSb = new StringBuffer();
			for(String s : pathArr) {
				if(rootCat != null) {
					if(s.contains(".txt")) {
						continue;
					}
					if(s.length() <= 4 || s.contains("草木鸟兽虫鱼") || s.contains("正统道藏") || rootCat.equals("佛藏"))
					catSb.append("," + s);
				}
				if(catSet.contains(s)) {
					rootCat = s;
				}
			}
			
			childCats = catSb.toString().replaceFirst(",", "");
			
			
			
			if(value != null) {//已经深度加工的文件
				//System.out.println(value.getAbsolutePath());
				String baseName = FilenameUtils.getBaseName(value.getAbsolutePath());
				String[] arr = baseName.split("-");
				author = arr.length == 4 ? arr[3] : "佚名";
				chaodai = arr.length >= 3 ? arr[2] : "不详";
				source = arr[1];
				
				//System.out.println("作者：" + author + ",朝代：" + chaodai + ",来源：" + source + ",排序：" + count);
				
				if(value.isFile()) {
					//文件,直接入库,不在拆分
					StringBuffer sb = new StringBuffer();
					String title = source;
					String contentStr = getContentStr(file);
					storeToDB(title, contentStr, source, author, chaodai, rootCat, childCats, count);
				} else {
					//目录,将其下级目录进行入库
					File[] files = value.listFiles();
					int temp = 0;
					Set<String> tempSet = new HashSet<String>();
					for(File fi : files) {
						temp++;
						String bName = FilenameUtils.getBaseName(fi.getName());
						String[] arr2 = bName.split("-");
						String title = null;
						if(arr2.length >= 2) {
							title = arr2[1];
						} else {
							title = bName;
						}
						
						if(tempSet.contains(title)) {
							title = title.concat("[" + temp + "]");
							tempSet.add(title);
						} else {
							tempSet.add(title);
						}
						
						if(fi.isDirectory()) {
							
							File[] lfs = fi.listFiles();
							if(lfs != null) {
								int lfCount = 0;
								for(File f : lfs) {
									lfCount++;
									String title2 = title + "[" + lfCount + "]";
									String contentStr = getContentStr(f);
									storeToDB(title2, contentStr, source, author, chaodai, rootCat, childCats, count);
								}
							}
							
						} else {
							String contentStr = getContentStr(fi);
							storeToDB(title, contentStr, source, author, chaodai, rootCat, childCats, count);
						}
						
						
					}
				}
			} else {//按照原来的文件进行统一处理
				//List<String> lines = FileUtils.readLines(file);
				String bName = FilenameUtils.getBaseName(file.getName());
				String[] arr = bName.split("-");
				author = arr.length == 3 ? arr[2] : "佚名";
				chaodai = arr.length >= 2 ? arr[1] : "不详";
				source = arr[0];
				
				List<String> list = getContentList(file);
				
				for(int i=0; i <list.size(); i++) {
					String title = source + "[" + (i + 1) + "]";
					String contentStr = list.get(i);
					storeToDB(title, contentStr, source, author, chaodai, rootCat, childCats, count);
				}
				
			}
			
		}
		
	}

	private void storeToDB(String title, String contentStr, String source,
			String author, String chaodai, String rootCat, String childCats,int count) {
		
		System.out.println("" + count + ",title:" + title + ",source:" + source + ",author:" + author + ",chaodai:" + chaodai
				+ ",rootCat:" + rootCat + ",childCats:" + childCats);
		
		//各个属性值的非空判断
		
		//作者的首字母缩写
		
		Content c = new Content();
		
		ContentExt ext = new ContentExt();
		ext.setTitle(title);
		ext.setAuthor(author);
		
		ContentTxt txt = new ContentTxt();
		txt.setTxt(contentStr);
		
		
	}

	private List<String> getContentList(File file) throws IOException {
		List<String> result = new ArrayList<String>();
		
		if(StarFileUtils.getFileSizeMB(file) > 35) {
			return result;
		}
		
		List<String> lines = FileUtils.readLines(file);
		StringBuffer sb = new StringBuffer();
		
		for(String line : lines) {
			sb.append("<p>" + StarStringUtils.deleteAllHTMLTag(line) + "</p>");
			sb.append("\r\n");
			if(sb.length() >= 10000) {
				sb.insert(0, "\r\n");
				sb.insert(0, "<div class=\"wxContent\">");
				sb.append("\r\n");
				sb.append("</div>");
				result.add(sb.toString());
				sb = new StringBuffer();
			}
		}
		
		result.add(sb.toString());
		return result;
	}

	private String getContentStr(File file) throws IOException {
		StringBuffer sb = new StringBuffer();
		List<String> lines = FileUtils.readLines(file);
		sb.append("<div class=\"wxContent\">");
		sb.append("\r\n");
		for(String line : lines) {
			sb.append("<p>" + StarStringUtils.deleteAllHTMLTag(line) + "</p>");
			sb.append("\r\n");
		}
		sb.append("\r\n");
		sb.append("</div>");
		return sb.toString();
	}

	private Map<File, File> getCompareMap(File rawFile, File makeFile) {
		
		Map<File, File> map = new HashMap<File, File>();
		if(rawFile == null || makeFile == null) {
			return map;
		}
		
		//获取文件名
		Set<String> rawFileNames = StarFileUtils.getFileNames(rawFile, true, false);
		Set<String> makeFileNames = StarFileUtils.getFileNames(makeFile, true, true);
		
		//判断是否文件名全部符合要求
		boolean isAllRight = isAllRight(makeFileNames);
		if(!isAllRight) {
			return map;
		}
		//包含作者的文件名全部符合条件
		
		//筛选包含作者的标准结果
		Map<String, String> makeFileNameMap = new HashMap<String, String>();
		for(String makeFileName : makeFileNames) {
			String[] arr = makeFileName.split("\\\\");
			int count = 0;
			for(String s : arr) {
				if(s.split("-").length == 4) {
					count++;
				}
			}
			if(count == 1 && arr[arr.length-1].split("-").length == 4) {
				String baseName = FilenameUtils.getBaseName(makeFileName);
				makeFileNameMap.put(baseName.split("-")[1], makeFileName);
			}
		}
		
		int mapCount = 0;
		for(String rawFileName : rawFileNames) {
			String baseName = FilenameUtils.getBaseName(rawFileName);
			String value = makeFileNameMap.get(baseName);
			if(value != null) {
				mapCount++;
				map.put(new File(rawFileName), new File(value));
			} else {
				map.put(new File(rawFileName), null);
			}
		}
		
		System.out.println("原始文件数量：" + rawFileNames.size());
		System.out.println("作者文件数量：" + makeFileNameMap.size());
		System.out.println(mapCount);
		
		return map;
	}

	private boolean isAllRight(Set<String> makeFileNames) {
		boolean isAllRight = true;
		for(String makeFileName : makeFileNames) {
			boolean isRight = false;
			boolean isFile = false;
			String[] arr = makeFileName.split("\\\\");
			for(int i=arr.length - 1; i >= 0; i--) {
				String temp = arr[i];
				if(temp.endsWith(".txt")) {
					isFile = true;
				}
				if(StringUtils.countMatches(temp, "-") == 3 || StringUtils.countMatches(temp, "-") == 4) {
					isRight = true;
					break;
				}
			}
			if(!isRight && isFile) {
				System.out.println(makeFileName);
				isAllRight = false;
			}
		}
		return isAllRight;
	}
}
