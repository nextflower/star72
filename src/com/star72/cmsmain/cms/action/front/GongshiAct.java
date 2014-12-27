package com.star72.cmsmain.cms.action.front;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.star72.cmsmain.common.web.ResponseUtils;
import com.star72.common.utils.math.PLZUUtils;

/**
 * 前台公式处理类
 * 
 * @author larry
 *
 */
@Controller
public class GongshiAct {
	
	@RequestMapping(value = "/gongshi/pailie.jspx", method = RequestMethod.POST)
	public void pailie(Integer n, Integer r, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String info = null;
		if(n == null || r == null) {
			info = "n或者r为空";
		} else if(r > n) {
			info = "r不能大于n";
		} else {
			BigDecimal result = PLZUUtils.computePaiLie(n, r);
			info = "您计算的公式为：C(" + n + "," + r + "), 计算结果如下：\r\n" + getFormatString(result);
		}
		ResponseUtils.renderText(response, info);
	}
	
	@RequestMapping(value = "/gongshi/zuhe.jspx", method = RequestMethod.POST)
	public void zuhe(Integer n, Integer r, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		String info = null;
		if(n == null || r == null) {
			info = "n或者r为空";
		} else if(r > n) {
			info = "r不能大于n";
		} else {
			BigDecimal result = PLZUUtils.computeZuhe(n, r);
			info = "您计算的公式为：P(" + n + "," + r + "), 计算结果如下：\r\n" + getFormatString(result);
		}
		ResponseUtils.renderText(response, info);
	}

	private String getFormatString(BigDecimal result) {
		return result.toString();
	}

}
