package com.star72.test.caiji;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import com.star72.common.utils.StarStringUtils;

/**
 * 
 * @author larry
 *
 */
public class ShiciHandleTest{
	
	@Test
	public void test() throws IOException {
		String path = "D:\\temp\\元诗选.txt";
		String destPath = FilenameUtils.getFullPath(path) + FilenameUtils.getBaseName(path);
		List<String> readLines = FileUtils.readLines(new File(path));
		
		//稿件容器
		List<String> contentList = new ArrayList<String>();
		//标题变量
		String title = null;
		//计数器
		int count = 0;
		
		
		for(String line : readLines) {
			
			//如果是标题
			if(isTitle(line)) {
				
				if(title != null) {
					//进行处理
					String[] split = title.trim().split(" ");
					//System.out.println(split[0]);
					if(split[0].length() > 30) {
						contentList.add(0, title);
						title = split[0].substring(0, 30);
					} else {
						if(title.length() > 30) {
							contentList.add(0, title);	
						}
						title = split[0];
					}
					FileUtils.writeLines(new File(destPath + File.separator + title + ".txt"), contentList);
					
					title = null;
					contentList.clear();
					
				} else if(contentList.size() > 0) {
					contentList.clear();
				}
				
				//重新初始化
				title = (++count) + "-" + StarStringUtils.deleteBiaodian(line);
				
			} else {
				contentList.add(line);
			}
			
		}
		
		if(title != null) {
			FileUtils.writeLines(new File(destPath + File.separator + title + ".txt"), contentList);
		}
		
	}
	
	
	//是否为标题
	private boolean isTitle(String line) {
		boolean falg = false;
		
//		falg = isTitle_yuefushiji(line);//乐府诗集
		falg = isTitle_yuanshixuan(line);//元诗选
		
		return falg;
	}
	
	private boolean isTitle_yuanshixuan(String line) {
		if(line != null) {
			if(line.contains("◆") || line.contains("○")) {
				return true;
			}
		}
		return false;
	}

	private boolean isTitle_yuefushiji(String line) {
		if(line != null && line.contains("○")) {
			return true;
		}
		return false;
	}


	public void testSong() throws IOException {
		String destPath = "D:\\TEST\\star\\quansong4\\";
		String path = "D:\\TEST\\star\\quansong4.txt";
		List<String> readLines = FileUtils.readLines(new File(path));
		List<String> contentList = new ArrayList<String>();
		String title = null;
		
		Pattern pattern = Pattern.compile("(.{1,5})　(.+)");
		
		int count = 148460;
		int hitCount = 148460;
		for(String line : readLines) {
			if(!line.startsWith("    ") && StringUtils.isNotBlank(line)) {
				count++;
				Matcher m = pattern.matcher(line);
				if(m.matches()) {
					
					if(title != null) {
						//生成一个新文件
						///System.out.println(title);
						
						//，。、﹗＂"
						if(title.contains("，") || title.contains("。") || title.contains("、") || title.contains("﹗") || title.contains("＂")|| title.contains("\"")) {
							title = title.replace("，", "").replace("。", "").replace("、", "").replace("﹗", "").replace("＂", "").replace("\"", "");
						}
						
						FileUtils.writeLines(new File(destPath + title + ".txt"), contentList);
						
						contentList.clear();
						title = null;
					} 
					
					hitCount++;
					//System.out.println(line);
					String g1 = m.group(1);//卷891_12
					String g2 = m.group(2);//河传
					title = hitCount + "-" + g2 + "-宋" + "-" + g1;
					//System.out.println(title);
				} else {
					System.out.println(line);
				}
			} else {
				//普通行
				if(StringUtils.isNotBlank(line)) {
					contentList.add(line);
				}
			}
		}
		
		System.out.println(count);
		System.out.println(hitCount);
		
	}
	
	
	public void testTang() throws IOException {
		
		String destPath = "D:\\TEST\\star\\quantang\\";
		
		String path = "D:\\TEST\\star\\quantang.txt";
		List<String> readLines = FileUtils.readLines(new File(path));
		List<String> contentList = new ArrayList<String>();
		String title = null;
		Pattern pattern = Pattern.compile("(.+)【(.+)】(.*)");
		
		int count = 0;
		for(String line : readLines) {
			if(line.contains("《国学原典")) {
				continue;
			}
			if(line.startsWith("   卷") && StringUtils.isNotBlank(line)) {
				//System.out.println(line);
				Matcher m = pattern.matcher(line);
				if(m.matches()) {
					
					if(title != null) {
						//生成一个新文件
						///System.out.println(title);
						
						FileUtils.writeLines(new File(destPath + title + ".txt"), contentList);
						
						contentList.clear();
						title = null;
					} 
					
					count++;
					//System.out.println(line);
					String g1 = m.group(1);//卷891_12
					String g2 = m.group(2);//河传
					String g3 = StringUtils.deleteWhitespace(m.group(3));//温庭筠
					if(StringUtils.isBlank(g3)) {
						g3 = "佚名";
					}
					title = count + "-" + g2 + "-唐" + "-" + g3;
					
				}
			} else {
				//普通行
				if(StringUtils.isNotBlank(line)) {
					contentList.add(line);
				}
			}
			
		}
		
		System.out.println(count);
	}

}
