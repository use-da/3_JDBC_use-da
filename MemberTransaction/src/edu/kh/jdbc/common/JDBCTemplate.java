package edu.kh.jdbc.common;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCTemplate {

	/*
		DB연결(Connection생성) ,자동 커밋 off, 트랜잭션 제어, JDBC객체 자원 반환(close)
		이러한 JDBC에서 반복 사용되는 코드를 모아둔 클래스

		모든 필드, 메서드가 static
		-> 어디서든지 클래스명.필드명 / 클래스명.메서드명 호출 가능(별도 객체 생성X)

	*/
	
	
	
	private static Connection conn=null;
	// static메서드에서 필드를 사용하기 위해서는 static필드여야한다
	
	
	
	/**DB정보를 담고있는 Connection 객체 생성 및 반환 메서드    conn=JDBCTemplate.getConnection();
	 * @return conn
	 */
	public static Connection getConnection() {
		
		try {
			
			//현재 커넥션 객체가 없을 경우 -> 새 커넥션 객체 생성
			if(conn==null || conn.isClosed()) {
				//conn.isClosed() : 커넥션이 close상태일 때 true반환
				
				Properties prop=new Properties();
				//Map<String,String>형태의 객체, XML입출력 특화
				
				//driver.xml 파일 읽어오기
				prop.loadFromXML(new FileInputStream("driver.xml"));
				//XML에 작성된 내용이 Properties객체에 모두 저장됨
				
				//XML에서 읽어온 값을 String 변수에 저장
				String driver=prop.getProperty("driver");
				String url=prop.getProperty("url");
				String user=prop.getProperty("user");
				String password=prop.getProperty("password");
				
				//커넥션 생성
				Class.forName(driver);      //Oracle JDBC Driver 객체 메모리 로드
				//DriveManager를 이용해 Connection 객체 생성
				conn = DriverManager.getConnection(url,user,password);
				//자동커밋 비활성화
				conn.setAutoCommit(false);
			}
		} catch (Exception e) {
			System.out.println("[Connection 생성 중 예외 발생]");
			e.printStackTrace();
		}
		return conn;
	}
	
	/**Connection 객체자원반환 메서드
	 * @param conn
	 */
	public static void close(Connection conn) {
		
		try {
			//전달 받은 conn이 참조하는 Connection객체가 있고 
			//Connection 객체가 close상태가 아니라면
			if(conn != null && !conn.isClosed() ) conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	/**Statement(부모), PreparedStatement(자식) 객체자원반환 메서드  
	 * (다형성, 동적바인딩 적용)
	 * @param conn
	 */
	public static void close(Statement stmt) {
		try {
			
			if(stmt != null && !stmt.isClosed() ) stmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	/**ResultSet 객체자원반환 메서드
	 * @param rs
	 */
	public static void close(ResultSet rs) {
		try {
			if(rs != null && !rs.isClosed() ) rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	/**트랜잭션 Commit 메서드
	 * @param conn
	 */
	public static void commit(Connection conn) {
		try {
			//연결되어있는 Connection 객체만 Commit 진행
			if(conn !=null && !conn.isClosed())	conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	/**트랜잭션 rollBack 메서드
	 * @param conn
	 */
	public static void rollback(Connection conn) {
		try {
			//연결되어있는 Connection 객체만 rollback 진행
			if(conn !=null && !conn.isClosed())	conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
}
