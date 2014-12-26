package com.star72.naming.test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import com.star72.common.utils.DomainQueryUtil;

public class DomainTest {
	
	@Test
	public void test2() {
		String domain = "adfaihl.com";
		boolean flag = DomainQueryUtil.queryByWanWang(domain, false);
		if(flag) {
			System.out.println(domain);
		}
	}
	
	@Test
	public void test() {
		String s = " a ai an ang ao ba bai ban bang bao bei ben beng bi bian biao bie bin bing bo bu ca cai can cang cao ce cen ceng cha chai chan chang chao che chen cheng chi chong chou chu chuai chuan chuang chui chun chuo ci cong cou cu cuan cui cun cuo " +
				" da dai dan dang dao de dei deng di dian diao die ding diu dong dou du duan dui dun duo " +
				" e en er " +
				" fa fan fang fei fen feng fo fou fu " +
				" ga gai gan gang gao ge gei gen geng gong gou gu gua guai guan guang gui gun guo " +
				" ha hai han hang hao he hei hen heng hong hou hu hua huai huan huang hui hun huo " +
				" ji jia jian jiang jiao jie jin jing jiong jiu ju juan jue jun " +
				" ka kai kan kang kao ke ken keng kong kou ku kua kuai kuan kuang kui kun kuo " +
				" la lai lan lang lao le lei leng li lia lian liang liao lie lin ling liu long lou lu lv luan lve lun luo " +
				" ma mai man mang mao me mei men meng mi mian miao mie min ming miu mo mou mu " +
				" na nai nan nang nao ne nei nen neng ni nian niang niao nie nin ning niu nong nu nv nuan nve nuo " +
				" o ou " +
				" pa pai pan pang pao pei pen peng pi pian piao pie pin ping po pou pu " +
				" qi qia qian qiang qiao qie qin qing qiong qiu qu quan que qun " +
				" ran rang rao re ren reng ri rong rou ru ruan rui run ruo " +
				" sa sai san sang sao se sen seng sha shai shan shang shao she shei shen sheng shi shou shu shua shuai shuan shuang shui shun shuo si song sou su suan sui sun suo " +
				" ta tai tan tang tao te teng ti tian tiao tie ting tong tou tu tuan tui tun tuo " +
				" wa wai wan wang wei wen weng wo wu " +
				" xi xia xian xiang xiao xie xin xing xiong xiu xu xuan xue xun " +
				" ya yai yan yang yao ye yi yin ying yong you yu yuan yue yun " +
				" za zai zan zang zao ze zei zen zeng zha zhai zhan zhang zhao zhe zhei zhen zheng zhi zhong zhou zhu zhua zhuai zhuan zhuang zhui zhun zhuo zi zong zou zu zuan zui zun zuo";
		
		String[] split = s.split(" ");
		
		Set<String> set = new HashSet<String>();
		for(String pin : split) {
			if(StringUtils.isNotBlank(pin)) {
				set.add(pin.trim());
			}
		}
		
		System.out.println(set.size());
		
		Set<String> testedSet = new TreeSet<String>();
		for(String s1 : set) {
			for(String s2 : set) {
				for(String s3 : set) {
					String domain = s1 + s2 + s3 + ".com";
					//System.out.print(domain);
					if(!testedSet.contains(domain)) {
						testedSet.add(domain);
						boolean flag = DomainQueryUtil.queryByWanWang(domain, false);
						if(flag) {
							System.out.println(domain);
						}
					}
					//System.out.println();
				}
			}
		}
		
		
	}
	
	public String concat(List<String> list, String plus) {
		StringBuffer sb = new StringBuffer();
		for(String s : list) {
			sb.append(s);
		}
		return sb.toString();
	}

}
