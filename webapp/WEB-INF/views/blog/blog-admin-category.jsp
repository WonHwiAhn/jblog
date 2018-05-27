<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<c:import url="/WEB-INF/views/include/blog-header.jsp"></c:import>

<script>
	$(function(){
		console.log('생성');
		$("#addCategory").click(function(){
			var name = $("#name").val();
			var explanation = $("#desc").val();
			
			if(name == "" || explanation == ""){
				return;
			}
			
			var obj = new Object();
			
	        obj.name = name;
	        obj.explanation = explanation;
	        
	        var jsonData = JSON.stringify(obj);
	        
	        //alert(jsonData);

			$.ajax({
				url:"${pageContext.servletContext.contextPath }/api/category/add",
				type:"post",
				dataType : "json",
				data:jsonData,
				contentType:"application/json",
				success:function(response){
					if(response.result != "success"){
						return ;
					}
					//if(response.data == "exist"){
						alert('카테고리를 추가하였습니다.');
						
						$('#admin-cat').empty();
						
						$('#admin-cat').append("<tr>"+
				      			"<th>번호</th>"+
				      			"<th>카테고리명</th>"+
				      			"<th>포스트 수</th>"+
				      			"<th>설명</th>"+
				      			"<th>삭제</th>"+ 			
				      		"</tr>");
						
						$.each(response.data, function(idx, value){
							if(value.no != null && value.no != ''){
								$('#admin-cat').append("<tr>"+
															"<td>"+value.no+"</td>"+
															"<td>"+value.name+"</td>"+
															"<td>"+value.cnt+"</td>"+
															"<td>"+value.explanation+"</td>"+
															"<td><a href='#' onclick='deleteCategory("+value.no+")'><img src='${pageContext.request.contextPath}/assets/images/delete.jpg'></a></td>"+
														"</tr>");
							}
						});
						
						console.log(response);
						
						$('#name').val('');
						$('#desc').val('');
						
						return;
					//}
					
					
				},
				error:function(xhr, status, e){
					console.log(status + ":" + e);
				}
			});
		}); // end addCategory
	});
</script>

<script>
function deleteCategory(no){
	// cascade가 걸려있기 때문에 물어보는 alert창을 보여준다.
	if(confirm('해당 카테고리에 글이 있다면 삭제되지 않습니다.') == false){
		return ;
	}
	if(no == ""){
		return;
	}
	
	$.ajax({
		url:"${pageContext.servletContext.contextPath }/api/category/delete?no=" + no,
		type:"get",
		data:"",
		contentType:"application/json",
		success:function(response){
			if(response.result != "success"){
				return ;
			}
			//if(response.data == "exist"){
			alert('카테고리를 삭제하였습니다.');
			
			$('#admin-cat').empty();
			
			$('#admin-cat').append("<tr>"+
	      			"<th>번호</th>"+
	      			"<th>카테고리명</th>"+
	      			"<th>포스트 수</th>"+
	      			"<th>설명</th>"+
	      			"<th>삭제</th>"+ 			
	      		"</tr>");
			
			$.each(response.data, function(idx, value){
				if(value.no != null && value.no != ''){
					$('#admin-cat').append("<tr>"+
												"<td>"+value.no+"</td>"+
												"<td>"+value.name+"</td>"+
												"<td>"+value.cnt+"</td>"+
												"<td>"+value.explanation+"</td>"+
												"<td><a href='#' onclick='deleteCategory("+value.no+")'><img src='${pageContext.request.contextPath}/assets/images/delete.jpg'></a></td>"+
											"</tr>");
				}
			});
			
			console.log(response);
			
			return;
			//}
			
			
		},
		error:function(xhr, status, e){
			console.log(status + ":" + e);
		}
	});
}
</script>

	<div id="container">
		<div id="wrapper">
			<div id="content" class="full-screen">
				<ul class="admin-menu">
					<li><a href="${pageContext.servletContext.contextPath}/${authUser.id}/admin/basic">기본설정</a></li>
					<li class="selected">카테고리</li>
					<li><a href="${pageContext.servletContext.contextPath}/${authUser.id}/admin/write">글작성</a></li>
				</ul>
		      	<table class="admin-cat" id="admin-cat">
		      		<tr>
		      			<th>번호</th>
		      			<th>카테고리명</th>
		      			<th>포스트 수</th>
		      			<th>설명</th>
		      			<th>삭제</th>      			
		      		</tr>
		      		<c:set var="count" value="${fn:length(list) }" />
		      		<c:forEach items="${list}" var="vo" varStatus="status">
		      			<c:if test="${vo.no ne null}">
							<tr>
								<td>${vo.no}</td>
								<td>${vo.name }</td>
								<td>${vo.cnt }</td>
								<td>${vo.explanation }</td>
								<td><a href='#' onclick="deleteCategory('${vo.no}')"><img src="${pageContext.request.contextPath}/assets/images/delete.jpg"></a></td>
							</tr>  		
						</c:if>
					</c:forEach>	  
				</table>
      	
      			<h4 class="n-c">새로운 카테고리 추가</h4>
		      	<table id="admin-cat-add">
		      		<tr>
		      			<td class="t">카테고리명</td>
		      			<td><input type="text" name="name" id="name"></td>
		      		</tr>
		      		<tr>
		      			<td class="t">설명</td>
		      			<td><input type="text" name="desc" id="desc"></td>
		      		</tr>
		      		<tr>
		      			<td class="s">&nbsp;</td>
		      			<td><input type="button" id="addCategory" name="addCategory" value="카테고리 추가"></td>
		      		</tr>      		      		
		      	</table> 
			</div>
		</div>
		<div id="footer">
			<p>
				<strong>Spring 이야기</strong> is powered by JBlog (c)2016
			</p>
		</div>
	</div>
</body>
</html>