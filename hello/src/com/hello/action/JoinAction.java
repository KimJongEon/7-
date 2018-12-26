package com.hello.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JoinAction implements Action {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");	// 요청하는 값을 UTF-8로 인코딩, 항상 해주는것이 좋음

		return "/join.jsp";

	}

}
