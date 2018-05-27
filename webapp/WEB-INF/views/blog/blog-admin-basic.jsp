<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<c:import url="/WEB-INF/views/include/blog-header.jsp"></c:import>
	<div id="container">
		<div id="wrapper">
			<div id="content" class="full-screen">
				<ul class="admin-menu">
					<li class="selected">기본설정</li>
					<li><a href="${pageContext.servletContext.contextPath}/${authUser.id}/admin/category">카테고리</a></li>
					<li><a href="${pageContext.servletContext.contextPath}/${authUser.id}/admin/write">글작성</a></li>
				</ul>
				<form action="${pageContext.servletContext.contextPath}/${authUser.id}/admin/basic"
					  method="post"
					  enctype="multipart/form-data">
	 		      	<table class="admin-config">
			      		<tr>
			      			<td class="t">블로그 제목</td>
			      			<td><input type="text" size="40" name="name" value="${blogvo.name}"></td>
			      		</tr>
			      		<tr>
			      			<td class="t">로고이미지</td>
			      			<td>
								<c:if test="${blogvo.logo eq null}">
									<img id="preview" src="${pageContext.request.contextPath}/assets/images/basic_pic.png" width="150px" height="150px">
								</c:if>
								<c:if test="${blogvo.logo ne null }">
									<img id="preview" src="${pageContext.request.contextPath}/${blogvo.logo}" width="150px" height="150px">
								</c:if>
							</td>      			
			      		</tr>      		
			      		<tr>
			      			<td class="t">&nbsp;</td>
			      			<td><input type="file" name="file" id="getfile"></td>      			
			      		</tr>           		
			      		<tr>
			      			<td class="t">&nbsp;</td>
			      			<td class="s"><input type="submit" value="기본설정 변경하기"></td>      			
			      		</tr>           		
			      	</table>
				</form>
			</div>
		</div>
		<div id="footer">
			<p>
				<strong>Spring 이야기</strong> is powered by JBlog (c)2018
			</p>
		</div>
	</div>

<script>
	var file = document.querySelector('#getfile');

	file.onchange = function() {
		var fileList = file.files;
		var reader = new FileReader();

		reader.readAsDataURL(fileList[0]);

		reader.onload = function() {
			document.querySelector('#preview').src = reader.result;
		};
	};
</script>
</body>
</html>