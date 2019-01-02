package com.hello.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hello.model.DBBean;

public class UpdateAction implements Action {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8"); //요청하는 값을 UTF-8로 인코딩, 항상 해주는것이 좋음
		String idString = request.getParameter("id");
		
		if(idString != null) {
			int id = Integer.parseInt(idString);
			DBBean db = DBBean.getInstance();
			int result = db.update(id, true);
			if(result == 1) { // executeUpdate 했을때 1이 넘어와야지 정상적인 update
				return "{ \"result\": \"OK\"}";
			}
		}
		
		return "{ \"result\": \"NG\"}";
	}

}
