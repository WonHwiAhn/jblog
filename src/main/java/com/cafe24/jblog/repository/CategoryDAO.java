package com.cafe24.jblog.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.jblog.vo.CategoryVO;

@Repository
public class CategoryDAO {
	@Autowired 
	private SqlSession sqlSession;
	
	public List<CategoryVO> getList(Long no){
		return sqlSession.selectList("category.getList", no);
	}
	
	public boolean insert(CategoryVO vo) {
		int count = sqlSession.insert("category.insert", vo);
		return count == 1;
	}
	
	public boolean delete(Long no) {
		int count = sqlSession.delete("category.delete", no);
		return count == 1;
	}
	
	public List<CategoryVO> getList(CategoryVO vo){
		return sqlSession.selectList("category.getListIsCate", vo);
	}
}
