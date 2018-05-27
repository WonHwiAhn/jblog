package com.cafe24.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.jblog.repository.PostDAO;
import com.cafe24.jblog.vo.PostVO;

@Service
public class PostService {
	
	@Autowired
	private PostDAO postDAO;
	
	// 회원가입
	public void insert(PostVO vo) {
		postDAO.insert(vo);
	}
}
