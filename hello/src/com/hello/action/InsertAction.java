package com.hello.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hello.model.DBBean;

public class InsertAction implements Action {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		// 파라미터를 받아옴
		String job = request.getParameter("job");
		String resultString = "NG";
		String generatedId = "";
		
		if(job != null) {
			DBBean db = DBBean.getInstance();
			int result = db.insert(job);
			//하나만 추가 하면 1이 리턴되는것이 정상
			if(result != 0) { // 0이 아닐때 성공 -> id가 리턴
				generatedId = String.valueOf(result);
				resultString = "OK";
			}
		}
		String json = "{ \"result\": \"";
		json += resultString;
		json += "\", \"id\": \"";
		json += generatedId;
		json += "\" }";
		
		return json;
	}

}
