<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<c:import url="/WEB-INF/views/include/blog-header.jsp"></c:import>

<script>
	// 카테고리를 클릭했을 경우
	$(function(){
		$('.cateLink').click(function(){
			var $element = $(this);
			console.log($element.attr('data-no'));
			var categoryNo = $element.attr('data-no');
			
			var obj = new Object();
			
	        obj.no = categoryNo;
	        
	        var jsonData = JSON.stringify(obj);
			
			$.ajax({
				url:"${pageContext.servletContext.contextPath }/api/category/${blogvo.no}/getPost",
				type:"post",
				dataType : "json",
				data:jsonData,
				contentType:"application/json",
				success:function(response){
					$('#content').empty();
					
					if(response.data.length < 1){
						$('#content').append("<div class='blog-content'>"+
								"<h4>해당 카테고리의 글이 없습니다!!!!!!!</h4>"+
								"</div>");
					}
					
					$.each(response.data, function(idx, val){
						if(response.data.no == null){
							if(idx == 0){
								$('#content').append("<div class='blog-content'>"+
										"<h4>제목: "+val.title+"</h4>"+
										"<p>"+
										"	내용: "+val.content+
										"<p>"+
										"</div>");
							}
							$('#content').append("<ul class='blog-list'>"+
										"<li>"+
										"<a href='/jblog/${id}?postNo="+idx+"&categoryNo="+categoryNo+"'>"+idx+"&nbsp;&nbsp;&nbsp;"+val.title+"</a>"+ 
										"<span>"+val.reg_date+"</span>"+	
										"</li>"+
										"</ul>");
								
								/*
								<ul class="blog-list">
									<li>
									<a href="/jblog/${id}?postNo=${status.index}">${status.index}&nbsp;&nbsp;&nbsp;${post.title}</a> 
									<span>${post.reg_date}</span>	
									</li>
								</ul>
								*/
							
						}
						
						/* if(response.data.no != null){
							if(idx == 0){
								$('#content').append("<div class='blog-content'>"+
										"<h4>제목: "+val.title+"</h4>"+
										"<p>"+
										"	내용: "+val.content+
										"<p>"+
										"</div>");
							}
						} */
					});
					
				},
				error:function(xhr){
					
				}
			});
		});
	})
</script>

<div id="container">
	<div id="wrapper">
		<div id="content"><!-- ${postInfo[postNo].title} -->
			<c:forEach items="${postInfo}" var="post" varStatus="status">
				
				<c:if test="${postNo eq null}">
					<c:if test="${status.index == 0 }">
					<div class="blog-content">
						<h4>제목: ${post.title}</h4>
						<p>
							내용: ${post.content}
						<p>
					</div>
					</c:if>
				</c:if>
		
				<c:if test="${postNo ne null}">
					<c:if test="${status.index == 0 }">
						<div class="blog-content">
							<h4>제목: ${postInfo[postNo].title}</h4>
							<p>
								내용: ${postInfo[postNo].content}
							<p>
						</div>
					</c:if>
				</c:if>
				<ul class="blog-list">
					<c:if test="${IsCate eq -1}">
						<li><a href="/jblog/${id}?postNo=${status.index}">${status.index}&nbsp;&nbsp;&nbsp;${post.title}</a> <span>${post.reg_date}</span>	</li>
					</c:if>
					<c:if test="${IsCate ne -1}">
						<li><a href="/jblog/${id}?postNo=${status.index}&categoryNo=${IsCate}">${status.index}&nbsp;&nbsp;&nbsp;${post.title}</a> <span>${post.reg_date}</span>	</li>
					</c:if>
				</ul>
			</c:forEach>
		</div>
	</div>

	<div id="extra">
		<div class="blog-logo">
			<c:if test="${blogvo.logo eq null}">
				<img src="${pageContext.request.contextPath}/assets/images/basic_pic.png" width="150px" height="150px">
			</c:if>
			<c:if test="${blogvo.logo ne null }">
				<img src="${pageContext.request.contextPath}/${blogvo.logo}" width="150px" height="150px">
			</c:if>
		</div>
	</div>

	<div id="navigation">
		<h2>카테고리</h2>
		<ul>
			<c:forEach items="${categoryInfo}" var="category" varStatus="status">
				<li>
					<%-- <a href="/jblog/${id}/categoryNo?category=${category.no}">${category.name}</a> --%>
					<a href="#" class="cateLink" data-no=${category.no }>${category.name}</a>
				</li>
			</c:forEach>
		</ul>
	</div>
	<c:import url="/WEB-INF/views/include/blog-footer.jsp"></c:import>
</div>
</body>
</html>