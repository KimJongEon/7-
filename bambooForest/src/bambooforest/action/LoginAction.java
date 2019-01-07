package bambooforest.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import bambooforest.model.MemberDBBean;

public class LoginAction implements Action {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String memberid = request.getParameter("memberid");
		String password = request.getParameter("password");
		
		MemberDBBean db = MemberDBBean.getInstance();
		int result = db.memberCheck(memberid, password);
		String resultString = null;
		
		switch(result) {
		case MemberDBBean.VALID:
			resultString = "valid";
			request.getSession().setAttribute("member", db.getMember(memberid));
			break;
		
		case MemberDBBean.INVALID_PASSWORD:
			resultString = "invalid password";
			break;
			
		case MemberDBBean.INVALID_USER:
			resultString = "invalid user";
			break;
		default:
			resultString = "error";
		}
		
		JSONObject json = new JSONObject();
		json.put("result", resultString);
		
	    return json.toString();

	}

}
