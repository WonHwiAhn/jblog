package com.cafe24.jblog.security;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.mvc.annotation.DefaultAnnotationHandlerMapping;

import com.cafe24.jblog.controller.BlogController;
import com.cafe24.jblog.security.Auth.Role;
import com.cafe24.jblog.vo.UserVO;


public class AuthInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, 
							 HttpServletResponse response, 
							 Object handler)
			throws Exception {
		
		System.out.println("authuser interceptor URL PATH ==> ");
		
		String uri = request.getRequestURI();
		System.out.println("URI ==> " + uri);
		String ctxPath = request.getContextPath();
		System.out.println("CTXPATH ==> " + ctxPath);
		System.out.println(uri.replace(ctxPath, ""));
		String what = uri.substring(ctxPath.length());
		// WAHT ==> /test/admin/basic
		System.out.println("WAHT ==> " + what);
		String[] divUri = uri.split("/");
		String userId = "";
		if(divUri.length>2) {
			userId = divUri[2];
			System.out.println("USERID ==> " + divUri[2]);
		}
		
		System.out.println("handler ==> " + handler);
		System.out.println("HandlerMethod.class ==> " + HandlerMethod.class);
		
		// 1. handler 종류 확인
		// false면 다른 Handler가 있다는 뜻. 그러므로 그 쪽으로 흐름을 준다.
		if(handler instanceof HandlerMethod == false) {
			return true;
		}
		
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		
		System.out.println("handlerMethod!!! ==> " + handler);
		
		// 3. @Auth 받아오기
		Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
		
		System.out.println("handler class ==> ");
		
		System.out.println("class annotation ==> ");
		
	    // -- 클래스 타입 어스 받아오기
		/*if (auth == null) {
			auth = handlerMethod.getMethod().getDeclaringClass().getAnnotation(Auth.class);
			
			if(auth.role() == Role.USER)
				System.out.println("class Auth 출력 테스트 ==> " + auth.toString());
		}*/
		
		/*for (Annotation annotation : handlerMethod.getClass().getDeclaredAnnotations()) {
		    System.out.println(annotation.toString());
		}*/
		
		Method method=handlerMethod.getMethod(); 
		/*if(method.getDeclaringClass().isAnnotationPresent(Auth.class)){
			 if(method.isAnnotationPresent(Auth.class))
			{ 
			System.out.println(method.getAnnotation(BlogController.class).value()); 
			request.setAttribute("STARTTIME",System.currentTimemillis());
			 }
			} */
		
		Annotation[] list = HandlerMethod.class.getAnnotations();
		
		// System.out.println("class annotation ==> " + list);
		
		// 4. Method에 @Auth가 없는 경우
		if(auth == null) {
			return true;
		}
		
		System.out.println("auth role ==> " + auth.role());
		System.out.println("@Auth 없는데? 오나?");
		
		// 5. @Auth가 붙어 있는 경우, 인증여부 체크
		HttpSession session = request.getSession();
		
		if(session == null) {
			if(!"".equals(userId))
				response.sendRedirect(request.getContextPath() + File.separator + divUri[2]);
			else
				response.sendRedirect(request.getContextPath() + "/user/login");
			return false;
		}
		
		UserVO authUser = (UserVO) session.getAttribute("authUser");
		
		if(authUser == null) {
			//response.sendRedirect(request.getContextPath() + "/user/login");
			if(!"".equals(userId))
				response.sendRedirect(request.getContextPath() + File.separator + divUri[2]);
			else
				response.sendRedirect(request.getContextPath() + "/user/login");
			return false;
		}
		
		/////// authUser의 auth값과 auth.role()값을 활용
		
		
		// 6. 접근허가
		return true;
		
		
		/*return super.preHandle(request, response, handler);*/
	}
	
}
