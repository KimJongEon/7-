package bambooforest.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bambooforest.model.MemberBean;

public class AddPostAction implements Action {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String result;
		
		if(request.getMethod().equals("Post")) {
			result = processPost(request, response);
		}else {
			result = processGet(request, response);
		}
		
		return result;
	}
	
	public String processGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//session 얻음
		HttpSession session = request.getSession();
		
		//set or get Attribute는 모두 Object타입으로 꺼내옴
		//MemberBean을 통째로 getAttribute
		MemberBean member = (MemberBean)session.getAttribute("member");
		
		if(member == null) {
			return "/login.jsp";
		}else {
			return "/addPostForm.jsp";
		}
		
	}
	
	public String processPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		
		return null;
	}
}
