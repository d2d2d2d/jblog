package com.douzone.jblog.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.BlogVo;

@Repository
public class BlogRepository {
	@Autowired
	private SqlSession sqlSession;

	public int insert(BlogVo vo) {
		return sqlSession.insert("blog.insert", vo);
	}
	
	public BlogVo findView(String id) {
		BlogVo vo = sqlSession.selectOne("blog.findView", id);
		return vo;
	}

	public int update(BlogVo vo) {
		return sqlSession.update("blog.update", vo);
	}

}
