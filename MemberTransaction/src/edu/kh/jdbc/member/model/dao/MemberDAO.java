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

import edu.kh.jdbc.main.view.MainView;
import edu.kh.jdbc.member.vo.Member;
import edu.kh.jdbc.member.vo.ReportBoard;
import edu.kh.jdbc.member.vo.SRBoard;

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
			pstmt.setString(3, member.getAddress());
			pstmt.setInt(4, member.getMemberNo());
			
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


	/**판매권한 신청DAO
	 * @param conn
	 * @param sr
	 * @return result
	 * @throws Exception
	 */
	public int sellerEnroll(Connection conn, SRBoard sr) throws Exception {
		
		int result=0; 
		try {
			String sql=prop.getProperty("sellerEnroll");
			
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setInt(1, sr.getMemberNo());
			pstmt.setString(2, sr.getPhone());

			result=pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
		
		return result;
	}


	/**관리자 회원 탈퇴 DAO
	 * @param conn
	 * @param memberNo
	 * @return result
	 * @throws Exception
	 */
	public int secession(Connection conn, int memberNo) throws Exception {
		int result = 0;

		try {

			String sql = prop.getProperty("unlimitSecession");

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, memberNo);

			result = pstmt.executeUpdate();

		} finally {
			close(pstmt);
		}
		return result;
	}


	public int badMember(Connection conn, ReportBoard rb) throws Exception {
		
		int result=0; 
		try {
			String sql=prop.getProperty("badMember");
			
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setInt(1, rb.getMemberNo());
			pstmt.setString(2, rb.getBadMember());
			pstmt.setString(3, rb.getReportContent());

			result=pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
		
		return result;
	}
}
