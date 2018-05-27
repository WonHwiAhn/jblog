package com.cafe24.jblog.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cafe24.jblog.dto.JSONResult;
import com.cafe24.jblog.security.AuthUser;
import com.cafe24.jblog.service.CategoryService;
import com.cafe24.jblog.service.UserService;
import com.cafe24.jblog.vo.CategoryVO;
import com.cafe24.jblog.vo.UserVO;

@Controller("categoryAPIController")
@RequestMapping("/api/category")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private UserService userService;
	
	@ResponseBody
	@RequestMapping(value="/add", method = RequestMethod.POST)
	public JSONResult add(//@ModelAttribute CategoryVO vo, 헤더값 없을 때
						  @RequestBody CategoryVO vo, //헤더값 있을 때
						  @AuthUser UserVO userVO
						  /*@RequestParam(value="name", required=false, defaultValue="") String name*/
						 /* @RequestParam(value="desc", required=false, defaultValue="") String disc*/) {
		
		System.out.println(vo);
		
		if(userVO != null) {
			vo.setUserNo(userVO.getNo());
		}
		if(categoryService.insert(vo)) {
			List<CategoryVO> list = categoryService.getList(userVO.getNo());
			return JSONResult.success(list == null ? "not exists" : list);
		}
		
		return JSONResult.success("not exists");
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public JSONResult delete(@ModelAttribute CategoryVO vo,
							 @AuthUser UserVO userVO
						  /*@RequestParam(value="name", required=false, defaultValue="") String name*/
						 /* @RequestParam(value="desc", required=false, defaultValue="") String disc*/) {
		
		if(userVO != null) {
			vo.setUserNo(userVO.getNo());
		}
		
		if(categoryService.delete(vo.getNo())) {
			List<CategoryVO> list = categoryService.getList(userVO.getNo());
			return JSONResult.success(list == null ? "not exists" : list);
		}
		
		return JSONResult.success("not exists");
	}
	
	@ResponseBody
	@RequestMapping("{blogNo}/getPost")
	public JSONResult getPost(@RequestBody CategoryVO vo,
							  @AuthUser UserVO userVO,
							  @PathVariable Long blogNo) {
		
		if(userVO != null) {
			vo.setUserNo(userVO.getNo());
		}else {
			vo.setUserNo(blogNo);
		}
		
		System.out.println("category vo ====> " + vo);
		if(vo.getNo() != null) {
			
			System.out.println("category vo ====> " + vo);
			
			List<CategoryVO> list = categoryService.getList(vo);
			return JSONResult.success(list == null ? "not exists" : list);
		}
		
		
		return JSONResult.success("not exists");
	}
}
