package com.star72.toolbox.action;

import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.star72.cmsmain.common.web.ResponseUtils;
import com.star72.common.utils.StarDateUtils;
import com.star72.common.utils.math.PLZUUtils;
import com.star72.toolbox.util.MoneyUtil;
import com.star72.toolbox.util.ToolBoxUtils;

/**
 * 在线工具类服务类,该action仅针对静态方法类
 * 
 * @author larry
 *
 */
@Controller
public class ToolBoxStaticAct {
	
	/**
	 * 计算排列
	 * @param n
	 * @param r
	 */
	@RequestMapping(value = "/toolBoxStatic/pailie.jspx", method = RequestMethod.POST)
	public void pailie(Integer n, Integer r, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String info = null;
		if(n == null || r == null) {
			info = "n或者r为空";
		} else if(r > n) {
			info = "r不能大于n";
		} else {
			BigDecimal result = PLZUUtils.computePaiLie(n, r);
			info = "您计算的公式为：P(" + n + "," + r + "), 计算结果如下：\r\n" + result;
		}
		ResponseUtils.renderText(response, info);
	}
	
	/**
	 * 计算组合
	 * @param n
	 * @param r
	 */
	@RequestMapping(value = "/toolBoxStatic/zuhe.jspx", method = RequestMethod.POST)
	public void zuhe(Integer n, Integer r, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String info = null;
		if(n == null || r == null) {
			info = "n或者r为空";
		} else if(r > n) {
			info = "r不能大于n";
		} else {
			BigDecimal result = PLZUUtils.computeZuhe(n, r);
			info = "您计算的公式为：C(" + n + "," + r + "), 计算结果如下：\r\n" + result;
		}
		ResponseUtils.renderText(response, info);
	}
	
	/**
	 * 数字转财务大写
	 */
	@RequestMapping(value = "/toolBoxStatic/num2UpperMoney.jspx", method = RequestMethod.POST)
	public void num2UpperMoney(Double amount, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		ResponseUtils.renderText(response, MoneyUtil.amountToChinese(amount));
	}
	
	/**
	 * 两个日期之间的间隔
	 * @param start 起始日期
	 * @param end 结束日期
	 */
	@RequestMapping(value = "/toolBoxStatic/daysBetweenDate.jspx", method = RequestMethod.POST)
	public void daysBetweenDate(Date start, Date end, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws JSONException {
		JSONObject json = new JSONObject();
		if(start == null || end == null) {
			ResponseUtils.renderJson(response, json.toString());
			return;
		}
		json.put("day", StarDateUtils.getDaysBetweenDate(start, end));
		json.put("week", StarDateUtils.getWeeksBetweenDate(start, end));
		json.put("month", StarDateUtils.getMonthsBetweenDate(start, end));
		json.put("year", StarDateUtils.getYearsBetweenDate(start, end));
		ResponseUtils.renderJson(response, json.toString());
	}
	
	/**
	 * 某个日期距离今天的间隔
	 * @param start 起始日期
	 */
	@RequestMapping(value = "/toolBoxStatic/days2Today.jspx", method = RequestMethod.POST)
	public void days2Today(Date start, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws JSONException {
		JSONObject json = new JSONObject();
		if(start == null) {
			ResponseUtils.renderJson(response, json.toString());
			return;
		}
		Date end = new Date();
		json.put("day", StarDateUtils.getDaysBetweenDate(start, end));
		json.put("week", StarDateUtils.getWeeksBetweenDate(start, end));
		json.put("month", StarDateUtils.getMonthsBetweenDate(start, end));
		json.put("year", StarDateUtils.getYearsBetweenDate(start, end));
		ResponseUtils.renderJson(response, json.toString());
	}
	
	/**
	 * 繁体转简体
	 */
	@RequestMapping(value = "/toolBoxStatic/fan2jian.jspx", method = RequestMethod.POST)
	public void fan2jian(String source, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		ResponseUtils.renderText(response, ToolBoxUtils.convertFan2Jian(source));
	}
	
	/**
	 * 简体转繁体
	 */
	@RequestMapping(value = "/toolBoxStatic/jian2fan.jspx", method = RequestMethod.POST)
	public void jian2fan(String source, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		ResponseUtils.renderText(response, ToolBoxUtils.convertJian2Fan(source));
	}

}
