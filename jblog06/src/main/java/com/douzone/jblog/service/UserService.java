package com.douzone.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.BlogRepository;
import com.douzone.jblog.repository.CategoryRepository;
import com.douzone.jblog.repository.PostRepository;
import com.douzone.jblog.repository.UserRepository;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.PostVo;
import com.douzone.jblog.vo.UserVo;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BlogRepository blogRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private PostRepository postRepository;

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
		createPost(vo.getNo());
		return count == 1;
	}

	public boolean createPost(Long categoryNp) {
		PostVo vo = new PostVo();
		vo.setTitle("Spring Camp 2016 참여기");
		vo.setContents("스프링 캠프에서는 JVM(Java Virtual Machine) 기반 시스템의 백엔드(Back-end) 또는\r\n" + 
				"						서버사이드(Server-side)라고 칭하는 영역을 개발하는 애플리케이션 서버 개발에 관한 기술과 정보, 경험을\r\n" + 
				"						공유하는 컨퍼런스입니다.<br> 핵심주제로 Java와 Spring IO Platform을 다루고 있으며, 그외\r\n" + 
				"						Architecture나 JVM Language, Software Development Process 등 애플리케이션\r\n" + 
				"						서버 개발에 필요한 다양한 주제를 다루려고 노력하고 있습니다.<br> 우리는 같은 일을 하고, 같은 관심사를\r\n" + 
				"						가진 개발자들이지만 서로를 모릅니다.<br> 스프링 캠프라는 컨퍼런스에 찾아온 낯선 개발자들 사이에서 자신을\r\n" + 
				"						소개하고 이야기를 나누고 웃고 즐기며면서 어색함을 떨쳐내고 우리가 같은 분야에서 함께 일하고 있는 친구이자 동료라는\r\n" + 
				"						것을 인지하고 새로운 인연의 고리를 연결하고 이어갈 수 있는 순간으로 만들어가려 합니다.");
		vo.setCategoryNo(categoryNp);

		int count = postRepository.insert(vo);
		return count == 1;
	}

	public UserVo getUser(UserVo vo) {
		return userRepository.findByIdAndPassword(vo);
	}

}