package edu.kh.jdbc.main.model.dao;

import static edu.kh.jdbc.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import edu.kh.jdbc.manager.vo.Manager;
import edu.kh.jdbc.member.vo.Member;

//DAO : DB연결용 객체
public class MainDAO {

	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs=null;
	
	private Properties prop=null;
	// Map<String, String> 제한, XML 파일 읽고/쓰고 특화
	
	public MainDAO() {
		
		try {
			prop=new Properties();
			prop.loadFromXML(new FileInputStream("main-query.xml"));
			//main-query.xml 파일의 내용을 읽어와 Properties 객체에 저장
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/** 아이디 중복 검사 DAO
	 * @param conn
	 * @param memberId
	 * @return result
	 * @throws Exception
	 */
	public int idDupCheck(Connection conn, String memberId) throws Exception{
		
		// 1. 결과 저장용 변수
		int result = 0;
		try {
			//2. SQL 얻어오기
			String sql=prop.getProperty("idDupCheck");
			
			//3. PreparedStatement 객체 생성
			pstmt=conn.prepareStatement(sql);
			
			//4. ? 알맞은 값 세팅
			pstmt.setString(1, memberId);
			
			//5. SQL 수행 후 결과 반환 받기
			rs=pstmt.executeQuery();
			
			//6. 조회 결과 옮겨 담기
			//1행 조회 -> if
			//n행 조회 -> while
			while(rs.next()) {
				result=rs.getInt("COUNT(*)"); //컬럼명
				//result=rs.getInt(1); //컬럼 순서
			}
		} finally {
			//7. 사용한 JDBC 객체 자원 반환
			close(rs);
			close(pstmt);
			
		}
		
		//8. 결과 반환
		return result;
	}

	/** 회원 가입 DAO
	 * @param conn
	 * @param member
	 * @return result
	 * @throws Exception
	 */
	public int memberSignUp(Connection conn, Member member) throws Exception {

		// 1. 결과 저장용 변수
		int result = 0;
		try {
			// 2. SQL 얻어오기
			String sql = prop.getProperty("signUp");
			// 3. PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			// 4. ? 알맞은 값 세팅
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getMemberPw());
			pstmt.setString(3, member.getMemberName());
			pstmt.setString(4, member.getMemberGender());
			pstmt.setString(5, member.getAddress());
			// 5. SQL 수행 후 결과 반환 받기
			result = pstmt.executeUpdate();
		} finally {
			// 6. 사용한 JDBC 객체 자원 반환
			close(pstmt);
		}
		return result;
	}

	/** 회원 로그인 DAO
	 * @param conn
	 * @param memberId
	 * @param memberPw
	 * @return loginMember
	 * @throws Exception
	 */
	public Member memberLogin(Connection conn, String memberId, String memberPw) throws Exception{
		
		// 1. 결과 저장용 변수 선언
		Member loginMember=null;
		
		try {
			// 2. SQL 얻어오기(main-query.xml)
			String sql = prop.getProperty("login");
			// 3. PrepaerStatement 객체 생성 
			pstmt=conn.prepareStatement(sql);
			// 4.  ? 알맞은 값 세팅
			pstmt.setString(1, memberId);
			pstmt.setString(2, memberPw);
			
			// 5. SQL 수행 후 결과 반환 받기
			rs = pstmt.executeQuery();
			
			// 6. 조회 결과가 있는 경우 컬럼 값을 얻어와 Member 객체를 생성해서 loginMember대입
			if(rs.next()) {
				/*	
				loginMember=new Member();
				loginMember.setMemberNo(rs.getInt("MEMBER_NO"));
				loginMember.setMemberId(memberId);
				loginMember.setMemberName(rs.getString("MEMBER_NM"));
				loginMember.setMemberGender(rs.getString("MEMBER_GENDER"));
				loginMember.setEnrollDate(rs.getString("ENROLL_DATE"));
				*/
			
				loginMember=new Member(rs.getInt("MEMBER_NO"),
										memberId,
										rs.getString("MEMBER_NM"),
										rs.getString("MEMBER_GENDER"),
										rs.getString("ENROLL_DATE"),
										rs.getString("SELLER_RIGHT"),
										rs.getString("ADDRESS"));
			}
				
			
		} finally {
			// 7. 사용한 JDBC 객체 자원 반환
			close(rs);
			close(pstmt);
			
		}
		
		
		// 8. 조회 결과 반환
		return loginMember;
	}

	/**관리자 로그인 DAO
	 * @param conn
	 * @param managerId
	 * @param managerPw
	 * @return
	 * @throws Exception
	 */
	public Manager managerLogin(Connection conn, String managerId, String managerPw) throws Exception {
		

		Manager loginManager=null;
		
		try {
			String sql = prop.getProperty("managerLogin");
	
			pstmt=conn.prepareStatement(sql);
	
			pstmt.setString(1, managerId);
			pstmt.setString(2, managerPw);
			
	
			rs = pstmt.executeQuery();
			
			// 6. 조회 결과가 있는 경우 컬럼 값을 얻어와 Member 객체를 생성해서 loginMember대입
			if(rs.next()) {
				
				loginManager=new Manager();
				loginManager.setManagerNo(rs.getInt("MANAGER_NO"));
				loginManager.setManagerId(managerId);
				loginManager.setManagerName(rs.getString("MANAGER_NM"));
				loginManager.setManagerGender(rs.getString("MANAGER_GENDER"));
				loginManager.setEnrollDate(rs.getString("ENROLL_DATE"));
				
		
			}	
		} finally {
		
			close(rs);
			close(pstmt);
		
		}
		
		return loginManager;
	}

}
