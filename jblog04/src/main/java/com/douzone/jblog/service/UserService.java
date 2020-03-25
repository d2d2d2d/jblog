package com.douzone.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.BlogRepository;
import com.douzone.jblog.repository.CategoryRepository;
import com.douzone.jblog.repository.UserRepository;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.UserVo;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BlogRepository blogRepository;
	@Autowired
	private CategoryRepository categoryRepository;

	public boolean join(UserVo vo) {
		int count = userRepository.insert(vo);
		return count == 1;
	}

	public boolean createBlog(String id) {
		BlogVo vo = new BlogVo();
		vo.setId(id);
		vo.setTitle(id + "님의 블로그");
		vo.setLogo("/assets/blog/images/spring-logo.jpg");

		int count = blogRepository.insert(vo);
		createCategory(vo.getId());
		return count == 1;
	}
	
	public boolean createCategory(String id) {
		CategoryVo vo = new CategoryVo();
		vo.setName("기타");
		vo.setDescription("기타카테고리");
		vo.setId(id);

		int count = categoryRepository.insert(vo);
		return count == 1;
	}

	public UserVo getUser(UserVo vo) {
		return userRepository.findByIdAndPassword(vo);
	}

}