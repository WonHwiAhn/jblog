package com.cafe24.jblog.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.jblog.vo.UserVO;

@Repository
public class UserDAO {
	
	@Autowired
	private SqlSession sqlSession;
	
	// 회원 가입 쿼리
	public boolean insert(UserVO vo) {
		int count = sqlSession.insert("user.insert", vo);
		
		return count == 1;
	}
	
	// 회원 가입시 id 중복체크 쿼리
	public UserVO getEmail(String id) {
		return sqlSession.selectOne("user.checkEmail", id);
	}
	
	// 로그인 쿼리
	public UserVO login(UserVO vo) {
		return sqlSession.selectOne("user.login", vo);
	}
	
	// 기본설정에서 제목만 변경했을 때 유저의 url을 따로 가져오는 쿼리
	public String getUrl(Long no) {
		return sqlSession.selectOne("user.getUrl", no);
	}
	
	public Long getNo(String id) {
		return sqlSession.selectOne("user.getNo", id);
	}
}
