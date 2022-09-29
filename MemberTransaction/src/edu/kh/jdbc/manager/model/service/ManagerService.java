package edu.kh.jdbc.manager.model.service;

import static edu.kh.jdbc.common.JDBCTemplate.close;
import static edu.kh.jdbc.common.JDBCTemplate.commit;
import static edu.kh.jdbc.common.JDBCTemplate.getConnection;
import static edu.kh.jdbc.common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.List;

import edu.kh.jdbc.manager.model.dao.ManagerDAO;
import edu.kh.jdbc.member.vo.Member;
import edu.kh.jdbc.member.vo.ReportBoard;
import edu.kh.jdbc.member.vo.SRBoard;



public class ManagerService {

	private ManagerDAO mDAO=new ManagerDAO();
	
	/**판매신청 조회
	 * @return srBoardList
	 * @throws Exception
	 */
	public List<SRBoard> selectSRBoard() throws Exception {
		Connection conn=getConnection();
		List<SRBoard>srBoardList=mDAO.selectSRBoard(conn);
		
		close(conn);
		
		return srBoardList;
	}

	/**판매권한 승인
	 * @param member
	 * @return result
	 * @throws Exception
	 */
	public int sellerRightUpdate(Member member) throws Exception {
		Connection conn = getConnection();
		
		int result = mDAO.sellerRightUpdate(conn, member );
		
		if (result > 0)
			commit(conn);
		else
			rollback(conn);
	
		close(conn);
		
		return result;
	}

	/**판매 신청 삭제
	 * @param srBoardNo
	 * @return result
	 * @throws Exception
	 */
	public int deleteSRBoard(int srBoardNo) throws Exception {
		Connection conn = getConnection();
		
		int result = mDAO.deleteSRBoard(conn, srBoardNo );
		
		if (result > 0)
			commit(conn);
		else
			rollback(conn);
	
		close(conn);
		
		return result;
	}

	public List<ReportBoard> selectBadMember() throws Exception {
		Connection conn=getConnection();
		List<ReportBoard>reportBoardList=mDAO.selectBadMember(conn);
		
		close(conn);
		
		return reportBoardList;
	}
	
}
