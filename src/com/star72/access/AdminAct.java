package com.star72.access;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 后台访问入口
 * 
 * @author wz
 *
 */
@Controller
public class AdminAct {
	
	@RequestMapping("/login.do")
	public String login(HttpServletRequest request) {
		boolean logined = true;
		if(logined) {
			return "login";
		} else {
			return "login";
		}
	}

}
