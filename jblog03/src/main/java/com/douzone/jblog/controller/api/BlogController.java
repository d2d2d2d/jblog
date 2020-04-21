package com.douzone.jblog.controller.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
	
	@DeleteMapping("/delete/{no}")
	public JsonResult delete(
			@PathVariable("no") Long no,
			@RequestParam(value="password", required=true, defaultValue="") String password) {
		boolean result = blogService.deleteCategory(no);

		return JsonResult.success(result ? no : -1);

	}
	
//	@PostMapping("/uploadImage")
//	public JsonResult uploadImage(@PathVariable String id,
//			@RequestBody BlogVo vo) {
//		
//		vo.setLogo(logo);
//		
//		return JsonResult.success(vo);
//	}
////	
//	@RequestMapping(value="upload", method=RequestMethod.POST)
//	public String upload(
//			@PathVariable String id,
//			@RequestParam(value="logo-file") MultipartFile multipartFile,
//			@RequestParam(value="title", required=true, defaultValue="") String title,
//			Model model,
//			HttpServletRequest request) {
//
//		String url = fileUploadService.restore(multipartFile);
//
//		BlogVo blogVo = new BlogVo();
//
//		blogVo.setId(id);
//		blogVo.setLogo(url);
//		blogVo.setTitle(title);
//
//		blogService.update(blogVo);
//
//		return "redirect:/{id}";
//	}
}
