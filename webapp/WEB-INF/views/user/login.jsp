<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
	
	
<c:import url="/WEB-INF/views/include/main-header.jsp"></c:import>

	<div class="center-content">
		<form class="login-form" action="${pageContext.servletContext.contextPath}/user/auth">
      		<label>아이디</label> <input type="text" name="email">
      		<label>패스워드</label> <input type="text" name="password">
      		<input type="submit" value="로그인">
		</form>
	</div>
	
<c:import url="/WEB-INF/views/include/main-footer.jsp"></c:import>