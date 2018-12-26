package com.hello.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloAction implements Action {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");	// 요청하는 값을 UTF-8로 인코딩, 항상 해주는것이 좋음
		
		String id = request.getParameter("id");
		String passwd = request.getParameter("passwd");
		String message = null; // 0,1,-1 값을 담을 message 변수 선언
		
		// 1 로그인 성공, 0 비밀번호 맞지 않음, -1 아이디 존재하지 않음
		if( id.equals("admin") && passwd.equals("admin")) {
			message = "1";
		}else if( id.equals("admin") && !passwd.equals("admin")) {
			message = "0";
		}else {
			message = "-1";
		}
		
		return "{ \"result\": \"OK\", \"message\": "+ message +" }";
	}

}
