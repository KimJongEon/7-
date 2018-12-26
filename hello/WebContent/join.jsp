<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
	<script
  	src="https://code.jquery.com/jquery-3.3.1.min.js"
  	integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
  	crossorigin="anonymous">
  	</script>

	<script>
	$(document).ready(function(){
		// login() 함수 선언
		function login() {
				var query = {
						id:$("#id").val(),
						passwd:$("#passwd").val()
				};
				$.ajax({
					type : 'POST',
					url : '/api/hello',
					dataType : 'text',
					data : query,
					success : function(data) {
						//alert(data); // JSON 파싱 전 data 값 확인
						var obj = JSON.parse(data); //JSON 파싱 : 인수로 전달받은 문자열을 자바스크립트 객체로 변환하여 반환
						//alert(obj.message); // JSON 파싱 후 data 값 확인

						console.log(obj.message); // HelloAction에서 넘어오는 return값 확인
						
						if(obj.message == "1"){
							$("#message").removeClass("invisible"); //클래스 삭제
							$("#message").addClass("normal"); //클래스 추가
							$("#message").text("환영합니다.");
						}else if(obj.message == "0"){
							$("#message").removeClass("invisible");
							$("#message").addClass("warning");
							$("#message").text("비밀번호가 맞지 않습니다.");
						}else{
							$("#message").removeClass("invisible");
							$("#message").addClass("warning");
							$("#message").text("아이디없음");
						}
						
					} // success
				}); // ajax
			}
		
			//로그인 버튼 클릭하면 login() 함수 실행
			$("#loginBtn").click(function() {
				login();
			});
			
			//id에 커서를 두고 엔터키를 누르면 로그인 함
			$("#id").keydown(function(key) {
				if (key.keyCode == 13) {
					login();
				}
			});
			
			//passwd에 커서를 두고 엔터키를 누르면 로그인 함
			$("#passwd").keydown(function(key) {
				if (key.keyCode == 13) {
					login();
				}
			});
			
		}); // document
	</script>


	<style>
		.invisible { visibility : hidden;}
		.normal{ visibility : visible; color:blue;}
		.warning { visibility : visible; color:red;}
	</style>

</head>
<body>
	<h1>Join</h1>
	<p id="message" class="invisible"></p>
	<input id="id" type="text" autofocus/> <br/>
	<input id="passwd" type="password"/> <br/>
	
	<button id="loginBtn" style="height:50px; width:173px;">로그인</button>
</body>
</html>