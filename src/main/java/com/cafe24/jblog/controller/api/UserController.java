package com.cafe24.jblog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cafe24.jblog.dto.JSONResult;
import com.cafe24.jblog.repository.UserDAO;
import com.cafe24.jblog.vo.UserVO;

@Controller("userAPIController")
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserDAO userDao;

	@ResponseBody
	@RequestMapping("/checkemail")
	public JSONResult checkMail(@RequestParam(value = "email", required = true, defaultValue = "") String email) {
		UserVO vo = userDao.getEmail(email);
		return JSONResult.success(vo == null ? "not exists" : "exist");
	}

}
