package com.cafe24.jblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cafe24.jblog.security.Auth;
import com.cafe24.jblog.service.BlogService;
import com.cafe24.jblog.service.UserService;
import com.cafe24.jblog.vo.BlogVO;
import com.cafe24.jblog.vo.UserVO;

@Controller
@RequestMapping(value="/user")
public class UserController {

	@Autowired
	private UserService userService;
	/*@Autowired
	private UserService userService1;*/
	
	@Autowired
	private BlogService blogService;
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login() {
		System.out.println("여기도 오지?");
		return "/user/login";
	}
	
	@RequestMapping(value="/join", method=RequestMethod.GET)
	public String join() {
		return "/user/join";
	}
	
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join(@ModelAttribute UserVO vo, RedirectAttributes rttr) {
		
		System.out.println("userVO pass ==> " + vo.getPassword());
		
		if(userService.join(vo)) {
			System.out.println("삽입 성공");
			vo = userService.getUser(vo);
			
			System.out.println("join vo ==> " + vo.getNo());
			
			blogService.create(new BlogVO(vo.getNo(), vo.getId()+"의 블로그", null));
			
			rttr.addFlashAttribute("msg", "success");
			
			return "redirect:/user/joinsuccess";
		}
		
		System.out.println("회원가입 실패!");
		return "/user/join";
	}
	
	@RequestMapping(value="/joinsuccess", method=RequestMethod.GET)
	public String joinS() {
		return "/user/joinsuccess";
	}
}
