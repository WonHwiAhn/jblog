package com.cafe24.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cafe24.jblog.repository.BlogDAO;
import com.cafe24.jblog.vo.BlogVO;
import com.cafe24.jblog.vo.PostVO;

@Service
public class BlogService {
	@Autowired
	private BlogDAO blogDAO;
	
	// 회원가입
	public void create(BlogVO vo) {
		System.out.println("blog service vo ==> " + vo.getNo());
		System.out.println("blog service vo ==> " + vo.getName());
		blogDAO.create(vo);
	}
	
	// 회원 아이디를 통한 블로그 정보 조회
	public BlogVO getInfo(Long no) {
		return blogDAO.getInfo(no);
	}
	
	// 회원 아이디를 통한 회원 고유번호 조회
	public Long getUserNo(String id) {
		return blogDAO.getUserNo(id);
	}
	
	// 회원 블로그 수정
	public boolean update(BlogVO vo) {
		return blogDAO.update(vo);
	}
	
	public List<PostVO> getPostInfo(Long no) {
		return blogDAO.getPost(no);
	}
}
