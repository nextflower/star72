package com.star72.wenxian.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.star72.cmsmain.cms.entity.main.Channel;
import com.star72.cmsmain.cms.entity.main.CmsModel;
import com.star72.cmsmain.cms.entity.main.Content;
import com.star72.cmsmain.cms.entity.main.ContentExt;
import com.star72.cmsmain.cms.entity.main.ContentTxt;
import com.star72.cmsmain.cms.manager.main.ChannelMng;
import com.star72.cmsmain.cms.manager.main.CmsModelMng;
import com.star72.cmsmain.cms.manager.main.ContentMng;
import com.star72.cmsmain.core.entity.CmsSite;
import com.star72.cmsmain.core.entity.CmsUser;
import com.star72.cmsmain.core.web.util.CmsUtils;
import com.star72.common.utils.PinyinUtil;
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
	
	@Autowired
	private ChannelMng channelMng;
	@Autowired
	private ContentMng contentMng;
	@Autowired
	private CmsModelMng modelMng;

	@RequiresPermissions("wenxian:import")
	@RequestMapping("/wenxian/import.do")
	public void view(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws IOException {
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		
		//importData(site, user);
		importShiGe(site, user);//导入诗歌
		
	}
	
	private void importShiGe(CmsSite site, CmsUser user) throws IOException {
		
		Channel channel = channelMng.findById(11);//诗歌
		
		//TODO
		String path = "D:\\gudian\\首页\\11诗词\\";//14382
		
		List<String> list = new ArrayList<String>();
		list.add("10-全宋诗-卷四");
		
		
			int count = 14388;
			
			for(int i=0; i<1; i++) {
				
				for(String s : list) {
					File root = new File(path + s);
					String bn = FilenameUtils.getBaseName(root.getName());
					String source = bn.split("-")[1];
					String author = null;
					String chaodai = null;
					int jishu = 0;
					
					String title = null;
					String contentStr = null;
					File[] childs = root.listFiles();
					
					Arrays.sort(childs, new Comparator<File>() {
						@Override
						public int compare(File o1, File o2) {
							String o1Name = FilenameUtils.getBaseName(o1.getName());
							String o2Name = FilenameUtils.getBaseName(o2.getName());
							String[] arr1 = o1Name.split("-");
							String[] arr2 = o2Name.split("-");
							return Integer.parseInt(arr1[0]) - Integer.parseInt(arr2[0]);
						}
					});
					
					if(childs != null) {
						for(File child : childs) {
							jishu++;
							if(jishu <= 36000) {
								String baseName = FilenameUtils.getBaseName(child.getName());
								
								String[] arr = baseName.split("-");
								if(arr.length == 4) {
									title = arr[1];
									chaodai = arr[2];
									author = arr[3];
								} else if(arr.length == 2) {
									title = arr[1];
								}
								contentStr = getContentStr(child);
								
								System.out.println(baseName + "---【" + jishu + "】---【" + childs.length + "】---" + count + ",source:" + source + ",author:" + author + ",chaodai:" + chaodai
									 + ",content_length:" + contentStr.length());
								
								storeToDB(site, user, channel, title, contentStr, source, author, chaodai, "诗词", "", count);
								child.delete();
								
							}
						}
					}
				}
			}
		
	}
	
	private void importShiGe2(CmsSite site, CmsUser user) throws IOException {
		
		Channel channel = channelMng.findById(11);//诗歌
		
		//TODO
		String path = "D:\\gudian\\首页\\11诗词\\16-宋诗钞-清-吴之振";//14204
		
		String source = "先秦汉魏晋南北朝诗";
		String author = null;
		String chaodai = null;
		
		File ffff = new File(path);
		
		int count = 14204;
		String title = null;
		String contentStr = null;
		File root = new File(path);
		File[] childs = root.listFiles();
		if(childs != null) {
			for(File child : childs) {
				String baseName = FilenameUtils.getBaseName(child.getName());
				System.out.println(baseName + "---" + childs.length);
				String[] arr = baseName.split("-");
				if(arr.length == 4) {
					title = arr[1];
					chaodai = arr[2];
					author = arr[3];
				} else if(arr.length == 2) {
					title = arr[1];
				}
				contentStr = getContentStr(child);
				storeToDB(site, user, channel, title, contentStr, source, author, chaodai, "诗词", "", count);
			}
		}
		
	}
	
	

	/**
	 * 用于导入10大分类书籍
	 * @param site
	 * @param user
	 * @throws IOException
	 */
	public void importData(CmsSite site, CmsUser user) throws IOException {
		
		//生成channel map
		Map<String, Channel> chMap = new HashMap<String, Channel>();
		List<Channel> topList = channelMng.getTopList(site.getId(), false);
		for(Channel ch : topList) {
			chMap.put(ch.getName(), ch);
		}
		
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
		
		String pathRaw = "H:\\文档\\gudaiwenxian";//原始文件夹
		String pathMake = "H:\\文档\\gudian\\首页";//加工后的文件夹
		
		
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
			Channel channel = chMap.get(rootCat);
			if(channel == null) {
				System.out.println(rootCat);
			}
			
			
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
					String title = source;
					String contentStr = getContentStr(value);
					storeToDB(site, user, channel, title, contentStr, source, author, chaodai, rootCat, childCats, count);
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
									storeToDB(site, user, channel, title2, contentStr, source, author, chaodai, rootCat, childCats, count);
								}
							}
							
						} else {
							String contentStr = getContentStr(fi);
							storeToDB(site, user, channel, title, contentStr, source, author, chaodai, rootCat, childCats, count);
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
					storeToDB(site, user, channel, title, contentStr, source, author, chaodai, rootCat, childCats, count);
				}
				
			}
			
		}
		
	}

	private void storeToDB(CmsSite site, CmsUser user, Channel channel, String title, String contentStr, String source,
			String author, String chaodai, String rootCat, String childCats,int count) {
		
		//各个属性值的非空判断
		if(StringUtils.isBlank(author)) {
			author = "佚名";
		}
		if(StringUtils.isBlank(chaodai)) {
			chaodai = "不详";
		}
		
		
		//作者的首字母缩写: A B C D E F G
		List<String> list = PinyinUtil.hanzi2PinyinNoDiao(author);
		String authorPinyin = null;
		if(list != null && list.size() > 0) {
			authorPinyin = list.get(0).substring(0, 1).toUpperCase();
		}
		
		//title\author\source\txt\childCats\authorPinyin\count\chaodai\
		
		Content c = new Content();
		c.setSite(site);
		c.setUser(user);
		c.setChannel(channel);
		c.setModel(modelMng.findById(9));
		
		ContentExt ext = new ContentExt();
		ext.setTitle(title);
		ext.setAuthor(author);
		ext.setShortTitle(source);
		
		ext.setCommon1(childCats);
		ext.setCommon2(authorPinyin);
		ext.setCommon3(chaodai);
		ext.setCommon4(String.valueOf(count));
		
		
		ContentTxt txt = new ContentTxt();
		txt.setTxt(contentStr);
		
		
		String[] tagArr = null;// tag数组,目前未指定
		Integer[] channelIds = null;// 副栏目id,需考虑如何指定
		Integer[] topicIds = null;// 主题id,需考虑如何指定
		Integer[] viewGroupIds = new Integer[]{};// 浏览权限,暂设置为空
		Boolean draft = false;// 状态,目前直接指定
		Boolean forMember = false;// 是否会员,目前直接指定
		Integer typeID = 1;// 稿件类型,目前指定为普通
		String[] attachmentPaths = null;
		String[] attachmentNames = null;
		String[] attachmentFilenames = null;
		String[] pics = null;
		
		if(contentStr.length() < 1000000) {
			
			try {
				//TODO
				contentMng.save(c, ext, txt, channel.getId(), typeID, draft, user, forMember);
			} catch (Exception e) {
				if(e.getMessage().contains("Incorrect string value")) {
					System.out.println(e.getMessage());
				} else {
					e.printStackTrace();
				}
			}
			
		}
		
	}

	private List<String> getContentList(File file) throws IOException {
		List<String> result = new ArrayList<String>();
		
		if(StarFileUtils.getFileSizeMB(file) > 35) {
			return result;
		}
		List<String> lines = FileUtils.readLines(file, "utf8");
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
		List<String> lines = FileUtils.readLines(file, "utf8");
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
