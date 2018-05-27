<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<c:import url="/WEB-INF/views/include/main-header.jsp"></c:import>
	
	<script>
		$(function(){
			$("#btn-checkemail").click(function(){
				var email = $("#blog-id").val();
				
				if(email == ""){
					return;
				}
				
				$.ajax({
					url:"${pageContext.servletContext.contextPath }/api/user/checkemail?email=" + email,
					type:"get",
					data:"",
					contentType:"application/json",
					success:function(response){
						if(response.result != "success"){
							return ;
						}
						if(response.data == "exist"){
							alert('이미 존재하는 아이디입니다.');
							$("#blog-id").val(email).focus();
							return;
						}
						
						$("#img-checkemail").show();
						$("#btn-checkemail").hide();
					},
					error:function(xhr, status, e){
						console.log(status + ":" + e);
					}
				});
				
			});
			
			// 중복체크를 하고나서 아이디를 수정할 경우
			$('#blog-id').keypress(function(){
				if($('#img-checkemail').css('display') == 'inline'){
					$("#img-checkemail").hide();
					$("#btn-checkemail").show();
				}
			});
			
			// form submit할 경우 validation
			$('#join-form').submit(function(event){
				var userName = $('#name').val();
				var userId = $('#blog-id').val();
				var userPassword = $('#password').val();
				var agree = $('#agree-prov').prop('checked');
				
				
				// 이름이 입력이 안 되있을 경우
				if(userName == ''){
					alert('이름을 입력해주세요.');
					$('#name').onfocus();
					event.preventDefault();
					return ;
				}
				
				// 아이디가 입력이 안 되있을 경우
				if(userId == ''){
					alert('아이디를 입력해주세요.');
					$('#blog-id').onfocus();
					event.preventDefault();
					return ;
				}
				
				if($('#img-checkemail').css('display') == 'none'){
					alert('중복확인 체크해주세요 :)');
					event.preventDefault();
					return ;
				}
				
				// 패스워드가 입력이 안 되있을 경우
				if(userId == ''){
					alert('패스워드를 입력해주세요.');
					$('#password').onfocus();
					event.preventDefault();
					return ;
				}
				
				// 체크가 안되어 있을 경우
				if(agree == false){
					alert('이용약관에 체크하셔야 가입가능 ㅇㅇ');
					event.preventDefault();
					return ;
				}
				
				
			});
		});
	</script>

	<div class="center-content">
	<form class="join-form" id="join-form" method="post" action="${pageContext.servletContext.contextPath}/user/join">
		<label class="block-label" for="name">이름</label> 
		<input id="name" name="name" type="text" value="" required> 
		<label class="block-label" for="blog-id">아이디</label> 
			<input id="blog-id" name="id" type="text" required>
			<input id="btn-checkemail" type="button" value="id 중복체크"> 
			<img id="img-checkemail" style="display: none;"
				src="${pageContext.request.contextPath}/assets/images/check.png">

		<label class="block-label" for="password">패스워드</label> 
		<input id="password" name="password" type="password" required />

		<fieldset>
			<legend>약관동의</legend>
			<input id="agree-prov" type="checkbox" name="agreeProv" value="">
			<label class="l-float">서비스 약관에 동의합니다.</label>
		</fieldset>

		<input type="submit" value="가입하기">

	</form>
</div>
<c:import url="/WEB-INF/views/include/main-footer.jsp"></c:import>