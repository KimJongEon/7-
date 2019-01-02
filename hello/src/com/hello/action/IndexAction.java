package com.hello.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hello.model.DBBean;

public class IndexAction implements Action {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) {
		
		DBBean db = DBBean.getInstance();
		request.setAttribute("rs", db.select());
		
		return "/info.jsp";

	}

}
