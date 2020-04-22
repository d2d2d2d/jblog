package com.douzone.jblog.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.service.FileUploadService;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.PostVo;

@Controller
@RequestMapping( "/{id:(?!assets).*}" )
public class BlogController {

	@Autowired
	private BlogService blogService;
	@Autowired
	private FileUploadService fileUploadService;

	@RequestMapping( {"", "/{pathNo1}", "/{pathNo1}/{pathNo2}" } )
	public String index( 
			@PathVariable String id,
			@PathVariable Optional<Long> pathNo1,
			@PathVariable Optional<Long> pathNo2,
			ModelMap modelMap,
			HttpServletRequest request) {

		Long categoryNo = 0L;
		Long postNo = 0L;

		if( pathNo2.isPresent() ) {
			postNo = pathNo2.get();
			categoryNo = pathNo1.get();
		} else if( pathNo1.isPresent() ){
			categoryNo = pathNo1.get();
		}

		modelMap.addAllAttributes(blogService.getAll( id, categoryNo, postNo ));
		BlogVo blogVo = (BlogVo) modelMap.get("blogVo");
		HttpSession session = request.getSession(true);
		session.setAttribute("blogVo", blogVo);
		return "blog/blog-main";
	}

	@RequestMapping( "/admin/basic" )
	public String adminBasic( @PathVariable String id ) {
		return "blog/blog-admin-basic";
	}

	@RequestMapping(value="/admin/category", method=RequestMethod.GET)
	public String adminCategory( 
			@PathVariable String id,
			HttpServletRequest request) {
		List<CategoryVo> list = blogService.getCategoryPlusTotalPost(id);
		request.setAttribute("list", list);
		return "blog/blog-admin-category";
	}

	@RequestMapping(value="/admin/category", method=RequestMethod.POST)
	public String addCategory( 
			@PathVariable String id,
			CategoryVo vo) {
		blogService.addCategory(vo);
		return "redirect:/{id}/admin/category";
	}

	@RequestMapping(value="/admin/category/delete/{no}")
	public String deleteCategory( 
			@PathVariable("no") Long no
			) {
		blogService.deleteCategory(no);
		return "redirect:/{id}/admin/category";
	}

	@RequestMapping(value="/admin/write", method=RequestMethod.GET)
	public String adminWrite(
			@PathVariable String id,
			HttpServletRequest request) {
		List<CategoryVo> list = blogService.getCategory(id);
		request.setAttribute("list", list);
		return "blog/blog-admin-write";
	}

	@RequestMapping(value="/admin/write", method=RequestMethod.POST)
	public String addPost(
			@PathVariable String id,
			PostVo vo) {
		blogService.addPost(vo);
		return "redirect:/{id}";
	}

	@RequestMapping(value="upload", method=RequestMethod.POST)
	public String upload(
			@PathVariable String id,
			@RequestParam(value="logo-file") MultipartFile multipartFile,
			@RequestParam(value="title", required=true, defaultValue="") String title,
			Model model) {

		String url = fileUploadService.restore(multipartFile);

		BlogVo blogVo = new BlogVo();

		blogVo.setId(id);
		blogVo.setLogo(url);
		blogVo.setTitle(title);

		blogService.update(blogVo);

		model.addAttribute("blogVo", blogVo);
		return "redirect:/{id}";
	}
}
