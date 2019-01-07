$(document).ready(function(){
	//login()함수 선언
	function login(){
		var query = {
			memberid: $("#memberid").val(),
			password:$("#password").val()
		};   //query
		
		$.ajax({
			type:"POST",
			url:"/api/login",
			data:query,
			dataType: 'text',
			success:function(data){
				var json =JSON.parse(data);
				
				if(json.result == 'valid'){
					location.href="/web/index";
				}else if(json.result == 'invalid user'){
					$("#message").text('등록되지 않은 이용자 입니다.')
				}else if(json.result == 'invalid password'){
					$("#message").text('비밀번호 오류입니다.')
				}else{
					$("#message").text('알 수 없는 오류가 발생하였습니다.')
				}
			}
		});
	}
	
	//로그인 버튼을 클릭하면 자동 실행
	$("#login").click(function(){
		login();
	});	//login
	
	//패스워드에 커서를 두고 엔터키를 누르면 로그인 함
	$("#password").keydown(function(key) {
		if (key.keyCode == 13) {
			login();
		}
	});
	
	//아이디에 커서를 두고 엔터키를 누르면 로그인 함
	$("#id").keydown(function(key) {
		if (key.keyCode == 13) {
			login();
		}
	});
});	//document