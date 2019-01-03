package bambooforest.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bambooforest.model.PostDBBean;

public class IndexAction implements Action {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// DB에서 게시글 목록을 읽어와 request.setAttribute 할 예정
		request.setCharacterEncoding("UTF-8");	// 요청하는 값을 UTF-8로 인코딩, 항상 해주는것이 좋음
		
		PostDBBean db = PostDBBean.getInstance();
		request.setAttribute("postList", db.getPostList());
		
		
		return "/list.jsp";
	}

}
