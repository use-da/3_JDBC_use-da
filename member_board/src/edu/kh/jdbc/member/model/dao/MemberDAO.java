package edu.kh.jdbc.member.model.dao;

import static edu.kh.jdbc.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.kh.jdbc.member.vo.Member;

public class MemberDAO {

	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private Properties prop;
	
	public MemberDAO() {
		
		try {
			prop=new Properties();
			prop.loadFromXML(new FileInputStream("member-query.xml"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 회원목록 조회 DAO
	 * @param conn
	 * @return memberList
	 * @throws Exception
	 */
	public List<Member> selectAll(Connection conn) throws Exception{
		// 결과 저장용 변수 선언
		List<Member>memberList=new ArrayList<>();
		try {
			//SQL 얻어오기
			String sql=prop.getProperty("selectAll");
//			String sql="SELECT MEMBER_ID, MEMBER_NM, MEMBER_GENDER\r\n"
//					+ "		FROM MEMBER\r\n"
//					+ "		WHERE SECESSION_FL='N'\r\n"
//					+ "		ORDER BY MEMBER_NO DESC";
			
			//Statement 객체 생성
			stmt=conn.createStatement();
			//SQL(SELECT) 수행 후 결과(ResultSet) 반환
			rs=stmt.executeQuery(sql);
			//반복문(While)을 이용해 조회 결과의 각 행에 접근 후 
			//컬럼 값을 얻어와 Member 객체에 저장 후 List에 추가
			while(rs.next()) {
				String memberId=rs.getString("MEMBER_ID");
				String memberName=rs.getString("MEMBER_NM");
				String memberGender=rs.getString("MEMBER_GENDER");
				
				Member member= new Member(memberId,memberName,memberGender);
				//권장방법 set사용 (매개변수 생성자보다 자주 사용)
//				Member member=new Member();
//				member.setMemberId(memberId);
//				member.setMemberName(memberName);
//				member.setMemberGender(memberGender);
				
				memberList.add(member);
			}
		} finally {
			//JDBC 객체 자원 반환
			close(rs);
			close(stmt);
		}
		//조회 결과를 옮겨 담은 List반환
		return memberList;
	}

	/**회원 정보 수정 DAO
	 * @param conn
	 * @param member
	 * @return result
	 * @throws Exception
	 */
	public int updateMember(Connection conn, Member member) throws Exception{
		//결과 저장용 변수 생성
		int result=0; //UPDATE 결과 반영된 행의 개수 저장용 변수
		try {
			//SQL얻어오기
			String sql=prop.getProperty("updateMember");
			//PreparedStatement 객체 생성
			pstmt=conn.prepareStatement(sql);
			
			//?값 대입
			pstmt.setString(1, member.getMemberName());
			pstmt.setString(2, member.getMemberGender());
			pstmt.setInt(3, member.getMemberNo());
			
			//SQL수행 후 결과 반환
			result=pstmt.executeUpdate();
		} finally {
			//JDBC 객체 반환
			close(pstmt);
		}
		//수정 결과 반환
		return result;
	}	//DB동기화 작업필요 -> View
	
	/**비밀번호 변경 DAO
	 * @param conn
	 * @param currentPw
	 * @param newPw1
	 * @param memberNo
	 * @return result
	 * @throws Exception
	 */
	public int updatePw(Connection conn, String currentPw, String newPw1, int memberNo) throws Exception {
		//결과 저장용 변수 생성
				int result=0; //UPDATE 결과 반영된 행의 개수 저장용 변수
				
				try {
					//SQL얻어오기
					String sql=prop.getProperty("updatePw");
					//PreparedStatement 객체 생성
					pstmt=conn.prepareStatement(sql);
					
					//?값 대입
					pstmt.setString(1, newPw1);
					pstmt.setInt(2, memberNo);
					pstmt.setString(3, currentPw);
					
					//SQL수행 후 결과 반환
					result=pstmt.executeUpdate();
							
				} finally {
					//JDBC 객체 반환
					close(pstmt);
				}
				//수정 결과 반환
				return result;
	}
	/** 회원탈퇴 DAO
	 * @param conn
	 * @param memberPw
	 * @param memberNo
	 * @return result
	 * @throws Exception
	 */
	public int secession(Connection conn, String memberPw, int memberNo) throws Exception {
		//결과 저장용 변수 생성
		int result=0; //UPDATE 결과 반영된 행의 개수 저장용 변수
		
		try {
			//SQL얻어오기
			String sql=prop.getProperty("secession");
			//PreparedStatement 객체 생성
			pstmt=conn.prepareStatement(sql);
			
			//?값 대입
			pstmt.setInt(1,memberNo );
			pstmt.setString(2,memberPw );

			//SQL수행 후 결과 반환
			result=pstmt.executeUpdate();
					
		} finally {
			//JDBC 객체 반환
			close(pstmt);
		}
		//수정 결과 반환
		return result;
	}
}
