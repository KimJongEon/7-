package bambooforest.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class PostDBBean {
	// DBBean은 보통 싱글톤으로 생성한다 -> 싱글톤 : 시스템 전체에서 하나의 인스턴스만 생성하는 것
	private static PostDBBean instance = new PostDBBean();

	// 생성자를 private으로 막아야 한다
	private PostDBBean() {
	}

	// 외부에서 먼ㅇ류미나워리ㅏㅓㅁ눙리ㅓㅏㅜ
	// statice은 new를 하지 않아도 생성
	public static PostDBBean getInstance() {
		return instance;
	}
	
	private Connection getConnection() {
		Context context;
		DataSource datasource = null;
		Connection conn = null;

		try {
			context = new InitialContext();
			datasource = (DataSource) context.lookup("java:comp/env/jdbc/jsp");
			conn = datasource.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return conn;
	}
	
	// 게시글 리스트 조회
	public ArrayList<PostBean> getPostList(){
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		//--------
		ArrayList<PostBean> postlist = null;
		PostBean p = null;
		
		try {
			String sql = "select * from post";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			postlist = new ArrayList<PostBean>();
			
			while(rs.next()) {
				p = new PostBean();
				p.setPostid(rs.getInt("postid"));
				p.setTitle(rs.getString("title"));
				//p.setContent(rs.getString("content"));
				p.setCreated(rs.getTimestamp("created"));
				//p.setMemberid(rs.getString("memberid"));
				postlist.add(p);
			}
		} catch (Exception e) {
			System.out.println("getPostList 게시글 리스트 조회 오류");
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
					System.out.println("rs오류");
					e.printStackTrace();
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e) {
					System.out.println("stmt 오류");
					e.printStackTrace();
				}
			if (conn != null)
				try {
					conn.close();
				} catch (Exception e) {
					System.out.println("conn 오류");
					e.printStackTrace();
				}
		}
		return postlist;
	}
	
	// 댓글 조회
	public ArrayList<ReplyBean> getReplyList(int postid) {
        Connection conn = getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<ReplyBean> reply = new ArrayList<ReplyBean>();
        
        try {
           String sql = "select * from reply where postid = ?";
           stmt = conn.prepareStatement(sql);
           stmt.setLong(1, postid);
           rs = stmt.executeQuery();
           
           
           while (rs.next()) {
              ReplyBean r = new ReplyBean();
              r.setPostid(rs.getInt("postid"));
              r.setComment(rs.getString("comment"));
              r.setReplyid(rs.getInt("replyid"));
              r.setMemberid(rs.getString("memberid"));
              r.setCreated(rs.getTimestamp("created"));
              reply.add(r);
              
           }
           
           
        }catch (Exception e) {
  
        }finally{
           try {
              if(rs != null) rs.close();
               if(stmt != null) stmt.close();
               if(conn !=null) conn.close();
              
           } catch (SQLException ex) {
              // TODO Auto-generated catch block
              ex.printStackTrace();
           }
        }
     
        
        return reply;
        
     }
	
	// 게시글 조회 (링크 선택시)
	public PostBean getPost(int postid) { //ArrayList<PostBean>
        Connection conn = null;
         PreparedStatement pstmt = null;
         ResultSet rs = null;  
         PostBean bean = null;
         
         //ArrayList<PostBean> post = new ArrayList<PostBean>();
         try {
            conn = getConnection();
            String sql = "select * from post where postid = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, postid);
            rs = pstmt.executeQuery();
            
           if(rs.next()) {
        	   bean = new PostBean();
               bean.setPostid(rs.getInt("postid"));
               bean.setTitle(rs.getString("title"));
               bean.setContent(rs.getString("content"));
               bean.setCreated(rs.getTimestamp("created"));
               bean.setMemberid(rs.getString("memberid"));
               //post.add(bean);
            }
         } catch (Exception e) {
            e.printStackTrace();
            System.out.println("post select error");
         }finally {
            try {rs.close();} catch (SQLException e) {}
            try {pstmt.close();} catch (SQLException e) {}
            try {conn.close();} catch (SQLException e) {}         
         }
      return bean; 
   }
	
	
	// 글 등록
	public int addPost(PostBean post) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		int result = 0;
		
		try {
			String sql = "insert into post (title, content, memberid) values (?,?,?)";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, post.getTitle());
			stmt.setString(2, post.getContent());
			stmt.setString(3, post.getMemberid());
			result = stmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("addPost 오류");
			e.printStackTrace();
		} finally {
			try {stmt.close();} catch (SQLException e) {}
            try {conn.close();} catch (SQLException e) {}     
		}
		
		return result;
	}
	
	
	// 댓글 등록
	public ReplyBean addReply(ReplyBean reply) {
        Connection conn = getConnection();
        PreparedStatement stmt = null , stmt2 = null;
        ResultSet rs = null, rsKey = null;
        int result = 0;
        
        try {
           String sql = "insert into reply (postid, memberid, comment) values(?,?,?)";
                 stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                 
                 stmt.setInt(1, reply.getPostid());
                 stmt.setString(2, reply.getMemberid());
                 stmt.setString(3, reply.getComment());
                 result = stmt.executeUpdate();
                 rsKey = stmt.getGeneratedKeys();
                 if(rsKey.next()) {
                	 result = rsKey.getInt("repliyid");
                 }
                 
           String sql2 = "select * from reply where replyid = ?";
                 stmt2 = conn.prepareStatement(sql2);
                 stmt2.setInt(1, result);
                 rs = stmt2.executeQuery();
                 if(rs.next()) {
                	 reply.setCreated(rs.getTimestamp("created"));
                	 reply.setReplyid(result);
                	 return reply;
                 }
        }catch (Exception e) {
        	e.printStackTrace();
        	System.out.println("addReply error");
        }finally{
        	 try {rs.close();} catch (SQLException e) {}
             try {stmt.close();} catch (SQLException e) {}
             try {conn.close();} catch (SQLException e) {}
        }
        return null;
     
     }
}
