package com.star72.naming.charlib;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.star72.common.utils.StarStringUtils;
import com.star72.naming.entity.CharBean;
import com.star72.naming.entity.CharLib;

/**
 * 根据配置文件生成字库
 * 
 * @author wz
 *
 */
public class WuxingCharLibCreator implements CharLibCreator {
	
	public CharLib createCharLib() {
		CharLib result = new CharLib();
		Set<String> goodCharSet = getGoodCharSet();
		for(String s : goodCharSet) {
			if(StringUtils.isNotBlank(s)) {
				CharBean cb = new CharBean(s, null, null, null);
				result.addCharBean(cb);
			}
		}
		return result;
	}

	public CharLib createCharLib1() {
		Set<String> badCharSet = getBadCharSet();
		CharLib result = new CharLib();
		String charLibPath = "com/star72/naming/dic/wuxingbihua_shiyi.txt";
		Resource res = new ClassPathResource(charLibPath);
		try {
			File file = res.getFile();
			List<String> list = FileUtils.readLines(file);
			int count = 0;
			for(String s : list) {
				String[] split = s.split("_");
				if(split.length == 3) {
					String bihua = split[0];
					String charName = split[1];
					String wuxingStr = split[2];
					String[] wuxingArr = wuxingStr.split("：");
					String wuxing = null;
					String description = null;
					if(wuxingArr.length == 2) {
						wuxing = wuxingArr[0];
						description = wuxingArr[1];
					}
					CharBean cb = new CharBean(charName, Integer.parseInt(bihua), wuxing, description);
					if(!badCharSet.contains(charName)) {
						result.addCharBean(cb);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	public Set<String> getGoodCharSet() {
		
		String s1 = "暠渺逢渲逦缘温逸嗣缜频选颔渝颖暄颜通领途清曦湾遥曼罗曹腾石崊飒湘崇飞飙罡飚喆善知风道曙啸巡邦昭昮真巧映巨巩春显昊源明群昌昂巍邑星易州川昕羽普景晨顺翌部翔唯";
		String s2 = "智滢晴晶致睿滔滕晋睻翠唐臻晟翰晗晔晓滋漫屹皇馥耀馨山皓哲枫品林展香裕柳益盈联盛柔聪尚和尔咏潇柘少小潞尉尊盼柏封峰峻朵朴木本望纶朔朗澎月育有红朋澔纨纪醉澜";
		String s3 = "纯启松听含岳濯濮绚杰行衍绅胜绍来岩名维同胤吉合金综绿材岚采白百继杉君登续绮向绪衡紫骐讯骞议索民素骅记骁城书骏访归习彬茜彩永乘彪茗彤乔水彦培乐基骥义之久彰茂影主丹江诤丽";
		String s4 = "详高垚异丰语荷临弛丫弘汉诵中弓诺荣汇丛业东识丞世荔强弼诗与诚一池万忆坚莱心莲男莹任沛莺志以沃代沁仪甜仕生忠念沧仁沣坤莎从莉介泓菱菲征律亿畴菡亦泊泉亮亭";
		String s5 = "京泰御云驹驰亚泽微泣菁波德马圣徽畅璥航洋洁文船斌佰佳广璇幼洪幻新年园平方舜作洺璐璟斯国誉洲固佑佐舒良济伯传米伦旁伸艺浚希艾艳旋帅帆伊伋常浩时旷旺";
		String s6 = "伟海众旭琬琮信琪修芬琦俪琼芷琸芳琴琰芸琳涛政廷延理俊建涵琛芝保琒芙嘉若淇魁庚敖淑英庆瑾敏苹瑶序瑷侃康瑎苑苒瑞瑜敬苗瑗淼依庭跃鑫珑天冰冬珍策珏珈";
		String s7 = "珊冠蕊珂蕴军珺筠夏冕蕾珮筱冉多拓冀珠蔓越玚函玟蔚超笑玉凯凡玄凤玹奇凝玲奎玮环凌奕妮资铭妤扬承铎妍儒才如赫妙兰兴兵其钢姣钦钧檬姿全钰薇贞贝钊贤元";
		String s8 = "成贵充钟克光战先姗献武葆正长镇堂欢欣锴欧萌萍锡稀锦键锐营稹萱锋萸倧捷蓉闻倡倩蓝立墨倾蓓牧增竹毅豪章特豫竣毓健蒙士壮殿谊谋偿振段谱谷偌爱谦蒲壁谨";
		String s9 = "爽厚默黛桐迪际祥蝶情桂惟黎惜祺历厅迈迎惠燕迅迄连桦祖远运祚树发麒双达友子又栎示参辰栋孤学熙季辉辈格辅悦叶台召可阳辛校孺宋守安宏北轩雁宁雄宇雅轮棋宛恒定宙照实宝宜轺宗";
		String s10 = "轼鹏雪雨宪秋雯秀恨恩宣煜容宸宾森科棠棣煊雷鹭家然南富卓怀密隆博梓升禾千禹焯华思梅半卉梁焕福印怡禄鸿卿焘融卡鸣寻怿梦卫青烨靓靖静娜娟威力娅娇功娆虹楠烈娴劭加劻娣势楷";
		String s11 = "娥劼励虚霖婕霞霜勇震霆霄勘龙霏婉霈炎婷研婵露勤炅婧炜媛慕齐灵列则刚创初灿利音韵灏韶磊韩韬慧韦前鼎嫒榜剑意碧愉踏瀚瀛";
		
		Set<String> fullSet = new HashSet<String>();
		fullSet.addAll(StarStringUtils.parseStr2SingleStrList(s1));
		fullSet.addAll(StarStringUtils.parseStr2SingleStrList(s2));
		fullSet.addAll(StarStringUtils.parseStr2SingleStrList(s3));
		fullSet.addAll(StarStringUtils.parseStr2SingleStrList(s4));
		fullSet.addAll(StarStringUtils.parseStr2SingleStrList(s5));
		fullSet.addAll(StarStringUtils.parseStr2SingleStrList(s6));
		fullSet.addAll(StarStringUtils.parseStr2SingleStrList(s7));
		fullSet.addAll(StarStringUtils.parseStr2SingleStrList(s8));
		fullSet.addAll(StarStringUtils.parseStr2SingleStrList(s9));
		fullSet.addAll(StarStringUtils.parseStr2SingleStrList(s10));
		fullSet.addAll(StarStringUtils.parseStr2SingleStrList(s11));
		
		return fullSet;
	}
	
	public Set<String> getBadCharSet() {
		
		String s1 = "刀卜丁二力了刃三弓巾口乞亡孓女勺丸戈什氏手四爪斤亢牛犬牙巴比不歹反父户吊井屯厄王曰尻且失矢瓜去五必弁布匆兄夯矛皿母目丕皮穴疋尼奴他只瓦用此吏色舌死寺旬曳再早字伎件交伉考朽朱并伐刑凶休妃伏亥好米牟牝收汀血打多耳尖匠决老劣六肉宅旨地艮圭灰似伍戌羊伊衣夷有车赤串吹七忍妊伸身私伺宋忒酉妆孜走坐";
		String s2 = "改旱何妓忌劫妗困我吴杖妣呆妨佛否汗忙尨每汝尾但弟佃伶灸牢卵男弄努求町佟彤巫足坑牡坍秃抄刺儿姓青取刹刷祀些卒板杯杵杷斧姑孤乖忽昏肩纠卷抗肯快狂枚杻券卧";
		
		Set<String> fullSet = new HashSet<String>();
		fullSet.addAll(StarStringUtils.parseStr2SingleStrList(s1));
		fullSet.addAll(StarStringUtils.parseStr2SingleStrList(s2));
		
		return fullSet;
	}

}
