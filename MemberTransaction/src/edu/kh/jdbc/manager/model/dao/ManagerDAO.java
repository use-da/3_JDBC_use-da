package edu.kh.jdbc.manager.model.dao;

import static edu.kh.jdbc.common.JDBCTemplate.close;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.kh.jdbc.member.vo.Member;
import edu.kh.jdbc.member.vo.ReportBoard;
import edu.kh.jdbc.member.vo.SRBoard;

public class ManagerDAO {

	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private Properties prop;
	
	public ManagerDAO() {
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream("manager-query.xml"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**판매 신청 조회
	 * @param conn
	 * @return srBoardList
	 * @throws Exception
	 */
	public List<SRBoard> selectSRBoard(Connection conn) throws Exception {
		List<SRBoard>srBoardList=new ArrayList<>();
		try {
			String sql=prop.getProperty("selectSRBoard");
			
			stmt=conn.createStatement();
			
			rs=stmt.executeQuery(sql);
			
			while(rs.next()) {
				int srBoardNo=rs.getInt("SR_BOARD_NO");
				int memberNo=rs.getInt("MEMBER_NO");
				String phone=rs.getString("PHONE");
				String sellerRight=rs.getString("SELLER_RIGHT");
				
				SRBoard sr=new SRBoard();
				sr.setSrBoardNo(srBoardNo);
				sr.setMemberNo(memberNo);
				sr.setPhone(phone);
				sr.setSellerRight(sellerRight);
				
				srBoardList.add(sr);
			}
		} finally {
			close(rs);
			close(stmt);
		}
		return srBoardList;
	}

	/**판매권한 승인
	 * @param conn
	 * @param member
	 * @return result
	 * @throws Exception
	 */
	public int sellerRightUpdate(Connection conn, Member member)throws Exception {
		
		int result=0; 
		try {
			String sql=prop.getProperty("sellerRightUpdate");

			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, member.getSellerRight());
			pstmt.setInt(2, member.getMemberNo());
			
			result=pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
		return result;
	}

	/**판매신청DAO
	 * @param conn
	 * @param srBoardNo
	 * @return result
	 * @throws Exception
	 */
	public int deleteSRBoard(Connection conn, int srBoardNo) throws Exception {
		int result=0; 
		try {
			String sql=prop.getProperty("deleteSRBoard");

			pstmt=conn.prepareStatement(sql);
			
			pstmt.setInt(1,srBoardNo);
			
			result=pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
		return result;
	}

	public List<ReportBoard> selectBadMember(Connection conn)  throws Exception{
		List<ReportBoard>reportBoardList=new ArrayList<>();
		try {
			String sql=prop.getProperty("selectBadMember");
			
			stmt=conn.createStatement();
			
			rs=stmt.executeQuery(sql);
			
			while(rs.next()) {
				int rbNo=rs.getInt("RB_NO");
				String badMember=rs.getString("BAD_MEMBER");
				String reportContent=rs.getString("REPORT_CONTENT");
				
				ReportBoard rb=new ReportBoard();
				rb.setRbNo(rbNo);
				rb.setBadMember(badMember);
				rb.setReportContent(reportContent);
				
				reportBoardList.add(rb);
			}
		} finally {
			close(rs);
			close(stmt);
		}
		
		return  reportBoardList;
	}

}
