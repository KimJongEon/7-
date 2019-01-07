package bambooforest.model;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class MemberDBBean {
	// DBBean은 보통 싱글톤으로 생성한다 -> 싱글톤 : 시스템 전체에서 하나의 인스턴스만 생성하는 것
	private static MemberDBBean instance = new MemberDBBean();

	// 생성자를 private으로 막아야 한다
	private MemberDBBean() {
	}

	// 외부에서 먼ㅇ류미나워리ㅏㅓㅁ눙리ㅓㅏㅜ
	// statice은 new를 하지 않아도 생성
	public static MemberDBBean getInstance() {
		return instance;
	}
	
	public static final int VALID = 0;
	public static final int INVALID_PASSWORD = 1;
	public static final int INVALID_USER = 2;
	public static final int ERROR = 100;
	
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
	
	// 로그인 체크
	public int memberCheck(String memberid, String password) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "select * from member where memberid = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, memberid);
			
			rs = stmt.executeQuery();
			if(rs.next()) {
				String dbPassword = rs.getString("password");
				if(dbPassword.equals(password)) {
					return VALID;
				}else {
					return INVALID_PASSWORD;
				}
			}else {
				return INVALID_USER;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null)try {rs.close();}catch(Exception e) {}
			if(stmt!=null)try {stmt.close();}catch(Exception e) {}
			if(conn!=null)try {conn.close();}catch(Exception e) {}
		}
		
		return ERROR;
	}
	
	//----
	public MemberBean getMember(String memberid) {
		Connection conn = getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "select * from member where memberid = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, memberid);
			rs = stmt.executeQuery();
			
			if(rs.next()) {
				MemberBean member = new MemberBean();
				member.setMemberid(rs.getString("memberid"));
				member.setPassword(rs.getString("password"));
				return member;
			}else {
				return null;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null)try {rs.close();}catch(Exception e) {}
			if(stmt!=null)try {stmt.close();}catch(Exception e) {}
			if(conn!=null)try {conn.close();}catch(Exception e) {}
		}
		
		return null;
	}
}
