package com.cafe24.jblog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cafe24.jblog.security.AuthUser;
import com.cafe24.jblog.service.BlogService;
import com.cafe24.jblog.service.CategoryService;
import com.cafe24.jblog.service.UserService;
import com.cafe24.jblog.vo.CategoryVO;
import com.cafe24.jblog.vo.UserVO;

@Controller
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private BlogService blogService;
	
	// 카테고리 메인 페이지
	@RequestMapping(value="/{id}/admin/category", method = RequestMethod.GET)
	public String info(@PathVariable String id,
					   @AuthUser UserVO vo,
					   Model model) {
		
		// 사용자가 url로 관리자 페이지로 접근할 때 메인페이지로 돌려버리는 소스
		if( vo == null || vo.getId().equals( id ) == false ) {
			return "redirect:/" + id; 
		}
		
		Long no = blogService.getUserNo(id);
		
		List<CategoryVO> list = categoryService.getList(no);
		// 블로그 정보를 화면에 뿌려줌.
		model.addAttribute("blogvo", blogService.getInfo(no));
		model.addAttribute("list", list);
		model.addAttribute("id", id);
		
		System.out.println("cate list size ==> " + list.size());
		
		return "blog/blog-admin-category";
	}
	
	// 카테고리 추가
	/*@RequestMapping(value="/{id}/admin/category/add", method = RequestMethod.POST)
	public String add(@PathVariable String id,
					   Model model) {
		List<CategoryVO> list = categoryService.getList();
		
		model.addAttribute("list", list);
		
		System.out.println("cate list size ==> " + list.size());
		
		return "blog/blog-admin-category";
	}*/
}
