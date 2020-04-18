package com.douzone.jblog.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.PostVo;

@Repository
public class PostRepository {
	@Autowired
	private SqlSession sqlSession;
	
	public int insert(PostVo vo) {
		return sqlSession.insert("post.insert", vo);
	}

	public List<PostVo> getPostList(Long no) {
		return sqlSession.selectList("post.findAll", no);
	}

	public PostVo getPost(Long no) {
		return sqlSession.selectOne("post.find", no);
	}

}
