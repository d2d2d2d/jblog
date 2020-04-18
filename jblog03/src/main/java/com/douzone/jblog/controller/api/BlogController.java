package com.douzone.jblog.controller.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.douzone.jblog.dto.JsonResult;
import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;


@RestController("BlogApiController")
@RequestMapping("/{id:(?!assets).*}/api/blog")
public class BlogController {

	@Autowired
	private BlogService blogService;

	@RequestMapping("/list")
	public JsonResult list(@PathVariable String id,
			HttpServletRequest request) {
		List<CategoryVo> list = blogService.getCategoryPlusTotalPost(id);
		return JsonResult.success(list);
	}

	@PostMapping("/add")
	public JsonResult addCategory(@PathVariable String id,
			@RequestBody CategoryVo vo) {
		vo.setId(id);
		blogService.addCategory(vo);
		return JsonResult.success(vo);
	}
//	@DeleteMapping("/delete/{no}")
//	public JsonResult delete(
//			@PathVariable("no") Long no,
//			@RequestParam(value="password", required=true, defaultValue="") String password) {
//		boolean result = BlogService.deleteMessage(no, password);
//		return JsonResult.success(result ? no : -1);
//
//	}
}
