package com.star72.access;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminAct {
	
	@RequestMapping("/login.do")
	public String login(HttpServletRequest request) {
		return "login";
	}

}
