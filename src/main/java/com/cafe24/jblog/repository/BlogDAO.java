package com.cafe24.jblog.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.jblog.vo.BlogVO;
import com.cafe24.jblog.vo.PostVO;

@Repository
public class BlogDAO {
	@Autowired
	private SqlSession sqlSession;
	
	// 작성글 삽입 쿼리
	/*public boolean insert() {
	}*/
	
	// 블로그 생성 (사용자 가입시 블로그 생성)
	public boolean create(BlogVO vo) {
		int count = sqlSession.insert("blog.create", vo);
		
		return count == 1;
	}
	
	// 블로그 정보 조회 (번호로 조회)
	public BlogVO getInfo(Long no) {
		return sqlSession.selectOne("blog.getInfo", no);
	}
	
	// 아이디를 통해 사용자 고유번호 조회
	public Long getUserNo(String id) {
		return sqlSession.selectOne("blog.getNo", id);
	}
	
	// 블로그 정보 수정
	public boolean update(BlogVO vo) {
		int count = sqlSession.update("blog.update", vo);
		return count == 1;
	}
	
	// 블로그 글 조회
	public List<PostVO> getPost(Long no) {
		return sqlSession.selectList("blog.getPost", no);
	}
}
