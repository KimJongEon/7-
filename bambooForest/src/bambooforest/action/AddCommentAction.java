package bambooforest.action;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import bambooforest.model.PostDBBean;
import bambooforest.model.ReplyBean;

public class AddCommentAction implements Action {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String comment = request.getParameter("comment");
		String memberid = request.getParameter("memberid");
		String strPostid = request.getParameter("postid");
		
		int postid = Integer.parseInt(strPostid);
		
		ReplyBean rb = new ReplyBean();
		rb.setComment(comment);
		rb.setMemberid(memberid);
		rb.setPostid(postid);
		
		PostDBBean db = PostDBBean.getInstance();
		ReplyBean result = db.addReply(rb);
		
		
		if(result != null) {
			HashMap<String, Object> map = new HashMap<>();
			map.put("postid", result.getPostid());
			map.put("replyid", result.getReplyid());
			map.put("memberid", result.getMemberid());
			map.put("comment", result.getComment());
			map.put("created", result.getCreated().toString());
			
			JSONObject jObject = new JSONObject(map);
			String jsonText = jObject.toJSONString();
			System.out.println(jsonText);
			
			return jsonText;
		}
		
		return null;
	}

}
