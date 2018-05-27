package com.cafe24.jblog.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cafe24.jblog.security.Auth;
import com.cafe24.jblog.security.AuthUser;
import com.cafe24.jblog.vo.UserVO;

@Controller
@RequestMapping(path={"/main", ""})
public class MainController {
	
	@Auth
	@RequestMapping(value="")
	public String main(@AuthUser UserVO authUser,
					   HttpServletRequest request) {
		System.out.println("Main /main AuthUser ==> " + authUser);
		
		System.out.println("contextPath ==> " + request.getContextPath());
		
		return "main/index";
	}
} 
