package com.hello.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DBBean {
	// DBBean은 보통 싱글톤으로 생성한다 -> 싱글톤 : 시스템 전체에서 하나의 인스턴스만 생성하는 것
	private static DBBean instance = new DBBean();
	
	// 생성자를 private으로 막아야 한다
	private DBBean() {}
	
	
	// 외부에서 먼ㅇ류미나워리ㅏㅓㅁ눙리ㅓㅏㅜ
	// statice은 new를 하지 않아도 생성
	public static DBBean getInstance() {
		return instance;
	}
	
	// 커넥션 풀 객체 생성
	private Connection getConnection() throws Exception {
		
		Context initCtx = new InitialContext();
		Context envCtx = (Context)initCtx.lookup("java:comp/env");
		DataSource ds = (DataSource)envCtx.lookup("jdbc/jsp");
		return ds.getConnection();
	}
	
	public ArrayList<TodoBean> select(){
		ArrayList<TodoBean> list = null;
		Connection conn = null;
		
		try {
			conn = getConnection();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		if(conn != null) {
			String sql = "select * from todo";
			try {
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery();
				list = new ArrayList<>();
				while(rs.next()) {
					TodoBean tb = new TodoBean();
					tb.setId(rs.getInt("id"));
					tb.setJob(rs.getString("job"));
					tb.setDone(rs.getBoolean("done"));
					
					list.add(tb);
				}
				
				rs.close();
				stmt.close();
				conn.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		return list;
	}
	
	public int insert(String job) {
		Connection conn = null;
		
		try {
			conn = getConnection();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		int result = 0;
		try {
			String sql = "insert into todo (job) values (?)";
			PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); // id(primary key)에 autoincrement옵션을 설정했을때 사용
			stmt.setString(1, job);
			result = stmt.executeUpdate();
			if(result == 1) {
				ResultSet rs = stmt.getGeneratedKeys();
				if(rs.next()) {
					result = rs.getInt(1);
				}
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return result; // id가 리턴됨
	}
	
	public int update (int id, boolean done) {
		Connection conn = null;
		
		try {
			conn = getConnection();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		int result = 0;
		
		try {
			String sql = "update todo set job = ? where id = ? ";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setBoolean(1, done);
			stmt.setInt(2, id);
			result = stmt.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return result;
	}
}
