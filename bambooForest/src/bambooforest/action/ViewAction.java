package bambooforest.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bambooforest.model.PostDBBean;

public class ViewAction implements Action {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");	// 요청하는 값을 UTF-8로 인코딩, 항상 해주는것이 좋음
		
		String pid = request.getParameter("postid");
		if(pid != null) {
			int postid = Integer.parseInt(pid);
			PostDBBean db = PostDBBean.getInstance();
			request.setAttribute("post", db.getPost(postid));
			request.setAttribute("replyList", db.getReplyList(postid));
		}
		
		return "/view.jsp";
	}

}
