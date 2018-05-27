package com.cafe24.jblog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cafe24.jblog.security.Auth;
import com.cafe24.jblog.security.AuthUser;
import com.cafe24.jblog.security.Auth.Role;
import com.cafe24.jblog.service.BlogService;
import com.cafe24.jblog.service.CategoryService;
import com.cafe24.jblog.service.FileUploadService;
import com.cafe24.jblog.service.PostService;
import com.cafe24.jblog.service.UserService;
import com.cafe24.jblog.vo.BlogVO;
import com.cafe24.jblog.vo.CategoryVO;
import com.cafe24.jblog.vo.PostVO;
import com.cafe24.jblog.vo.UserVO;

// @Auth(role=Auth.Role.USER) jblog는 메소드에서 auth처리 ㄱㄱ
@Controller
@RequestMapping(value= {"/main", ""})
public class BlogController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private PostService postService;
	@Autowired
	private BlogService blogService;
	@Autowired
	private FileUploadService fileUploadService;
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String main(@PathVariable String id,
					   @ModelAttribute CategoryVO vo,
					   @RequestParam(value="postNo", required=false, defaultValue="") String postNo,
					   @RequestParam(value="categoryNo", required=false, defaultValue="-1") String categoryNo,
					   @AuthUser UserVO authUser,
					   Model model) {
		
		// System.out.println("BlogController vo ==> " + authUser.getNo());

		// 아이디를 통해 해당 사용자의 고유 번호를 얻어옴.
		Long no = blogService.getUserNo(id);
		
		if(no != null) {
			// 블로그 정보를 화면에 뿌려줌.
						model.addAttribute("blogvo", blogService.getInfo(no));
			// 메인화면에 뿌려질 category 가져오기
			model.addAttribute("categoryInfo", categoryService.getList(no));
			
			model.addAttribute("id", id);
			
			if(!"".equals(postNo))
				model.addAttribute("postNo", postNo);
			
			// 카테고리 번호가 없을 때
			if("-1".equals(categoryNo)) {
				// 메인화면에 뿌려질 post들의 정보 가져오기
				model.addAttribute("postInfo", blogService.getPostInfo(no));
				model.addAttribute("IsCate", "-1");
			}else {
				
				Long userNo = userService.getNo(id);
				vo.setUserNo(userNo);
				vo.setNo(Long.parseLong(categoryNo));
				
				// 메인화면에 뿌려질 post들의 정보 가져오기
				model.addAttribute("postInfo", categoryService.getList(vo));
				model.addAttribute("IsCate", categoryNo);
			}
			
			return "blog/blog-main";
		}else {
			return "redirect:/user/login";
		}
	}
	
	@Auth(role=Auth.Role.ADMIN)
	@RequestMapping(value="/{id}/admin/basic", method = RequestMethod.GET)
	public String adminMain(@PathVariable String id,
							@AuthUser UserVO userVO,
							Model model) {
		System.out.println("blog admin basic controller");
		
		// 사용자가 url로 관리자 페이지로 접근할 때 메인페이지로 돌려버리는 소스
		// 이 부분은 @Auth처리 나머지는 if문처리
		/*if( userVO == null || userVO.getId().equals( id ) == false ) {
			return "redirect:/" + id; 
		}*/
		
		BlogVO vo = blogService.getInfo(userVO.getNo());
		if(vo != null) {
			model.addAttribute("blogvo", vo);
			model.addAttribute("id", id);
			
			return "blog/blog-admin-basic";
		}else {
			return "user/login";
		}
		
	}
	
	@RequestMapping(value="/{id}/admin/basic", method = RequestMethod.POST)
	public String adminMainManage(@PathVariable String id,
								  @AuthUser UserVO userVO,
								  @ModelAttribute BlogVO vo,
								  @RequestParam(value="file", required=false, defaultValue="") MultipartFile multipartFile,
								  @RequestParam(value="title", required=true, defaultValue="제목을 입력하지 않았습니다.") String title,
								  Model model
								  ) {
		String url = fileUploadService.restore(multipartFile, userVO.getNo());
		
		vo.setNo(userVO.getNo());
		vo.setLogo(url);
		
		blogService.update(vo);
		
		model.addAttribute("blogvo", vo);
		model.addAttribute("id", id);
		return "blog/blog-admin-basic";
	}
	
	/*@RequestMapping(value="/{id}/admin/category", method = RequestMethod.GET)
	public String adminCategory(@PathVariable String id) {
		return "blog/blog-admin-category";
	}*/
	
	@RequestMapping(value="/{id}/admin/write", method = RequestMethod.GET)
	public String adminWrite(@PathVariable String id,
							 @AuthUser UserVO vo,
							 Model model) {
		
		// 사용자가 url로 관리자 페이지로 접근할 때 메인페이지로 돌려버리는 소스
		if( vo == null || vo.getId().equals( id ) == false ) {
			return "redirect:/" + id; 
		}
		
		List<CategoryVO> list = categoryService.getList(vo.getNo());
		
		// 아이디를 통해 해당 사용자의 고유 번호를 얻어옴.
		Long no = blogService.getUserNo(id);
		
		// 블로그 정보를 화면에 뿌려줌.
		model.addAttribute("blogvo", blogService.getInfo(no));
		model.addAttribute("list", list);
		model.addAttribute("id", id);
		
		return "blog/blog-admin-write";
	}
	
	@RequestMapping(value="/{id}/admin/write", method = RequestMethod.POST)
	public String adminWrite(@PathVariable String id,
							 @ModelAttribute PostVO vo,
							 @AuthUser UserVO authUser,
							 @RequestParam(value="title", required=true, defaultValue="제목을 입력하지 않았습니다.") String title,
							 @RequestParam(value="category", required=true, defaultValue="1") int category,
							 @RequestParam(value="content", required=true, defaultValue="내용을 입력하지 않았습니다.") String content,
							 Model model
							) {
		if(id.equals("") || id == null)
			return "user/login";
		
		vo.setBlogNo(authUser.getNo());
		
		postService.insert(vo);
		
		
		// ==> 나중에 리스트로 받아서 보내야됨. 내용 6개정도 짤라서
		//model.addAttribute("postvo", vo); 
		
		return "redirect:/"+id;
	}
	
	/*@RequestMapping(value="/{id}/category", method = RequestMethod.GET)
	public String getCategoryPost(@PathVariable String id,
								  @AuthUser UserVO authUser,
								  @RequestParam(value="categoryNo", required=false, defaultValue="") Long no) {
		
		
		
		return "";
	}*/
} 
