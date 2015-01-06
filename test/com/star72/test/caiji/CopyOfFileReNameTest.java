package com.star72.test.caiji;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

import com.star72.common.utils.StarStringUtils;

public class CopyOfFileReNameTest {
	
	/*
	 * gudian\\首页\\02儒藏-0370部
	 * gudian\\首页\\03道藏-1689部
	 * 04佛藏-5159部
	 * 05子藏-1155部
	 * 06史藏-1725部
	 * 07诗藏-0322部
	 * 08集藏-1467部
	 * 09医藏-0869部
	 * 10艺藏-0386部
	 * 11诗词
	 */
	
	public static final String FLAG_STR = "�";
	
	public static boolean openRename = false;
	
	@Test
	public void test() {
		String rightPath = "F:\\文档\\gudaiwenxian";
		String errorPath = "F:\\文档\\gudian\\首页";
		
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> list = new ArrayList<String>();
		getNameMap(new File(rightPath), map);
		
		getErrorNameList(new File(errorPath), list);
		
		//getInfoFromInternet(list);
		
		Set<String> aaSet = new HashSet<String>();
		
		for(String s : list) {
			
			//System.out.println(s);
			
			String[] dirs = s.split("\\\\");
			String last = dirs[dirs.length - 1];
			String[] namesplit = last.split("-");
			String name = null;
			if(namesplit.length >= 2) {
				name = namesplit[1];
			}
			
			if(name != null && name.contains(FLAG_STR)) {
				
				String[] split = name.split("-");
				if(split.length == 4) {
					
				} else {
					//findHit(s, map);//找到内容中匹配的对象
				}
				
				//renameByCompare(map, s, name);//根据名字进行替换,已经完成
			}
		}
		
	}

	private void getInfoFromInternet(List<String> list) {
		if(list == null) {
			return ;
		}
		
		String url = "http://www.baidu.com/s?rsv_spt=1&issp=1&f=8&rsv_bp=0&rsv_idx=2&ie=utf-8&tn=baiduhome_pg&wd=";
		for(String path : list) {
			
			System.out.println(path);
			
			String baseName = FilenameUtils.getBaseName(path);
			String[] arr = baseName.split("-");
			if(arr.length == 4) {
				String title = arr[1];
				String author = arr[3];
				
				String auRe = author.replace(FLAG_STR, ".{1}");
				
				try {
					Document doc = Jsoup.parse(new URL(url + title), 10000);
					if(doc == null) {
						return;
					}
					
					Element doc1 = doc.getElementById("1");
					Element doc2 = doc.getElementById("2");
					Element doc3 = doc.getElementById("3");
					Element doc4 = doc.getElementById("4");
					Element doc5 = doc.getElementById("5");
					Element doc6 = doc.getElementById("6");
					
					hitAuthor(path, auRe, doc1);
					hitAuthor(path, auRe, doc2);
					hitAuthor(path, auRe, doc3);
//					hitAuthor(path, auRe, doc4);
//					hitAuthor(path, auRe, doc5);
//					hitAuthor(path, auRe, doc6);
					
					System.out.println();
					
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}
	}

	private void hitAuthor(String path, String auRe, Element doc1) {
		if(doc1 != null) {
			String text = doc1.text();
			Pattern pattern = Pattern.compile(auRe);
			Matcher m = pattern.matcher(text);
			if(m.find()) {
				String group = m.group();
				System.out.print("===" + group);
			}
		}
	}

	public void findHit(String s, Map<String, List<String>> map) {
		System.out.println(s);
		
		String[] dirs = s.split("\\\\");
		String last = dirs[dirs.length - 1];
		String parent = dirs[dirs.length - 2];
		String[] namesplit = parent.split("-");
		String[] indexStrArr = last.split("-");
		String name = null;
		String indexStr = null;
		String indexStrSource = null;
		if(namesplit.length >= 2) {
			name = namesplit[1];
		}
		if(indexStrArr.length == 2) {
			indexStrSource = FilenameUtils.getBaseName(indexStrArr[1]);
			indexStr = StringUtils.deleteWhitespace(indexStrSource);
			System.out.println(indexStr);
		}
		
		
		if(name != null && indexStr != null) {
			for(String key : map.keySet()) {
				if(key.length() == name.length()) {
					List<String> keyList = StarStringUtils.parseStr2SingleStrList(name);
					boolean flag = true;
					for(String ks : keyList) {
						if(FLAG_STR.equals(ks) || key.contains(ks)) {
							
						} else {
							flag = false;
							break;
						}
					}
					
					if(flag) {
						List<String> ll = map.get(key);
						if(ll.size() == 1) {
							String realPath = ll.get(0);
							File file = new File(realPath);
							if(file.exists()) {
								
								try {
									
									long length = file.length();
									if(length * 1.0 / (1024 * 1024) > 20) {
										System.out.println(length * 1.0 / (1024 * 1024));
										continue;
									}
									
									List<String> readLines = FileUtils.readLines(file);
									
									for(String line : readLines) {
										String lineTemp = StringUtils.deleteWhitespace(line);
										if(lineTemp.length() >= indexStr.length()) {
											lineTemp = lineTemp.substring(0, indexStr.length());
											List<String> lineKeyList = StarStringUtils.parseStr2SingleStrList(indexStr);
											boolean flag2 = true;
											for(String ks : lineKeyList) {
												if(FLAG_STR.equals(ks) || StringUtils.isBlank(ks) || lineTemp.contains(ks)) {
													
												} else {
													flag2 = false;
													break;
												}
											}
											if(flag2) {
												String dest = s.replace(indexStrSource, lineTemp);
												File ff = new File(s);
												System.out.println();
												System.out.println();
												System.out.println(dest);
												System.out.println();
												System.out.println();
												if(openRename) {
													ff.renameTo(new File(dest));
												}
												break;
											}
										}
									}
									
//									System.out.println(name + "——" + indexStr);//第四十六回润甫巧说裴仁基世�智取黎阳仓.txt
//									System.out.println(realPath);
//									System.out.println();
//									System.out.println();
									
								} catch (IOException e) {
									System.out.println(e.getMessage());
								} catch(Throwable e) {
									System.out.println(e.getMessage());
								}
							}
							
						}
						
					}
				}
			}
		}

	}

	public void renameByCompare(Map<String, List<String>> map, String s,
			String name) {
		for(String key : map.keySet()) {
			if(key.length() == name.length()) {
				List<String> keyList = StarStringUtils.parseStr2SingleStrList(name);
				boolean flag = true;
				for(String ks : keyList) {
					if(FLAG_STR.equals(ks) || key.contains(ks)) {
						
					} else {
						flag = false;
						break;
					}
				}
				
				if(flag) {
					List<String> ll = map.get(key);
					if(ll != null) {
						//System.out.println(s);
						String dest = s.replace(name, key);
						File file = new File(s);
						System.out.println();
						System.out.println();
						System.out.println(dest);
						System.out.println();
						System.out.println();
						if(openRename) {
							file.renameTo(new File(dest));
						}
					}
				}
				
			}
		}
	}

	private void getErrorNameList(File file, List<String> list) {
		if(file == null) {
			return;
		}
		
		String name = file.getName();
		if(name.contains("11诗词")) {
			return;
		}
		
		if(file.isDirectory()) {
			File[] listFiles = file.listFiles();
			if(listFiles != null) {
				for(File child : listFiles) {
					getErrorNameList(child, list);
				}
			}
			String absPath = file.getAbsolutePath();
			if(absPath.contains(FLAG_STR)) {
				list.add(file.getAbsolutePath());
			}
			
		} else {
			String n = file.getName();
			if(n.contains(FLAG_STR)) {
				list.add(file.getAbsolutePath());
			}
		}
	}



	/**
	 * 获取正确的文件名
	 * @param file
	 * @param map
	 */
	private void getNameMap(File file, Map<String, List<String>> map) {
		if(file == null) {
			return;
		}
		if(file.isDirectory()) {
			File[] listFiles = file.listFiles();
			if(listFiles != null) {
				for(File child : listFiles) {
					getNameMap(child, map);
				}
			}
		} else {
			String name = file.getName();
			String baseName = FilenameUtils.getBaseName(name);
			List<String> set = map.get(baseName);
			if(set == null) {
				set = new ArrayList<String>();
				map.put(baseName, set);
			}
			set.add(file.getAbsolutePath());
		}
	}
	

}
