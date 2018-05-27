package com.cafe24.jblog.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.jblog.vo.PostVO;
import com.cafe24.jblog.vo.UserVO;

@Repository
public class PostDAO {
	@Autowired
	private SqlSession sqlSession;
	
	// 작성글 삽입 쿼리
	public boolean insert(PostVO vo) {
		int count = sqlSession.insert("post.insert", vo);
		
		return count == 1;
	}
}
