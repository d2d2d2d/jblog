package com.douzone.jblog.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.douzone.jblog.repository.BlogRepository;
import com.douzone.jblog.repository.CategoryRepository;
import com.douzone.jblog.repository.PostRepository;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.PostVo;

@Service
public class BlogService {

	@Autowired
	private BlogRepository blogRepository;
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private CategoryRepository categoryRepository;

	//main
	public Map<String, Object> getAll(String id, Long categoryNo, Long postNo) {
		BlogVo blogVo = blogRepository.findView(id);
		List<CategoryVo> categoryList = getCategory(id);
		List<PostVo> postList = getPost(categoryNo);
		List<CategoryVo> categoryListPlusTotalPost = getCategoryPlusTotalPost(id);

		ModelMap modelMap = new ModelMap();
		modelMap.put("blogVo", blogVo);
		modelMap.put("categoryList", categoryList);
		modelMap.put("postList", postList);
		modelMap.put("categoryListPlusTotalPost", categoryListPlusTotalPost);
		return modelMap;
	}
	
	public boolean update(BlogVo vo) {
		int count = blogRepository.update(vo);
		return count == 1;
	}

	//category
	public List<CategoryVo> getCategory(String id) {
		return categoryRepository.getCategory(id);
	}
	
	public List<CategoryVo> getCategoryPlusTotalPost(String id) {
		return categoryRepository.getCategoryPlusTotalPost(id);
	}

	public boolean addCategory(CategoryVo vo) {
		int count = categoryRepository.insert(vo);
		return count== 1;
	}

	public boolean deleteCategory(Long no) {
		int count = categoryRepository.delete(no);
		return count== 1;
	}

	//post
	public List<PostVo> getPost(Long no) {
		return postRepository.getPost(no);
	}
	
	public boolean addPost(PostVo vo) {
		int count = postRepository.insert(vo);
		return count == 1;
	}

}