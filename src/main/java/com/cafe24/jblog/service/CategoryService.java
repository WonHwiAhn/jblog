package com.cafe24.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.jblog.repository.CategoryDAO;
import com.cafe24.jblog.vo.CategoryVO;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryDAO categoryDAO;
	
	public List<CategoryVO> getList(Long no){
		return categoryDAO.getList(no);
	}
	
	public boolean insert(CategoryVO vo) {
		return categoryDAO.insert(vo); 
	}
	
	public boolean delete(Long no) {
		return categoryDAO.delete(no);
	}
	
	// 카테고리 번호로 리스트 구하기
	public List<CategoryVO> getList(CategoryVO vo){
		return categoryDAO.getList(vo);
	}
}
