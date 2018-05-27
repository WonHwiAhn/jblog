package com.cafe24.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.jblog.repository.UserDAO;
import com.cafe24.jblog.vo.UserVO;

@Service
public class UserService {
	@Autowired
	private UserDAO userDAO;
	
	// 회원가입
	public boolean join(UserVO vo) {
		return userDAO.insert(vo);
	}
	
	// 로그인
	public UserVO getUser(UserVO vo) {
		return userDAO.login(vo);
	}
	
	// 아이디로 회원 번호 가져오기
	public Long getNo(String id) {
		return userDAO.getNo(id);
	}
}
