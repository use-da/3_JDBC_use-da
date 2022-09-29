package edu.kh.jdbc.member.model.service;

import static edu.kh.jdbc.common.JDBCTemplate.*;
import java.sql.Connection;
import java.util.List;
import edu.kh.jdbc.member.model.dao.MemberDAO;
import edu.kh.jdbc.member.vo.Member;
import edu.kh.jdbc.member.vo.ReportBoard;
import edu.kh.jdbc.member.vo.SRBoard;

public class MemberService {
	private MemberDAO dao=new MemberDAO();
	
	/**회원 정보 수정 서비스
	 * @param member
	 * @return result
	 * @throws Exception
	 */
	public int updateMember(Member member) throws Exception{
		//커넥션 생성
		Connection conn=getConnection();
		//DAO 메서드 호출 후 결과 반환 
		int result=dao.updateMember(conn,member);
		//트랜잭션 제어 처리
		if(result>0) commit(conn);
		else		 rollback(conn);
		//커넥션 반환
		close(conn);
		//수정 결과 반환
		return result;
	}
	/**비밀번호 변경
	 * @param currentPw
	 * @param newPw1
	 * @param memberNo
	 * @return result
	 * @throws Exception
	 */
	public int updatePw(String currentPw, String newPw1, int memberNo) throws Exception {
		// 커넥션 생성
		Connection conn = getConnection();
		// DAO 메서드 호출 후 결과 반환
		int result = dao.updatePw(conn, currentPw, newPw1, memberNo );
		// 트랜잭션 제어 처리
		if (result > 0)
			commit(conn);
		else
			rollback(conn);
		// 커넥션 반환
		close(conn);

		return result;
	}
	/** 회원 탈퇴 서비스
	 * @param memberPw
	 * @param memberNo
	 * @return result
	 * @throws Exception
	 */
	public int secession(String memberPw, int memberNo) throws Exception {
		//커넥션 생성
		Connection conn=getConnection();
		//DAO 메서드 호출 후 결과 반환 
		int result=dao.secession(conn,memberPw,memberNo);
		//트랜잭션 제어 처리
		if(result>0) commit(conn);
		else		 rollback(conn);
		//커넥션 반환
		close(conn);
		//수정 결과 반환
		return result;
	}
	
	/**판매권한 신청 서비스
	 * @param sr
	 * @return result
	 * @throws Exception
	 */
	public int sellerEnroll(SRBoard sr) throws Exception {
		Connection conn= getConnection();
		
		int result=dao.sellerEnroll(conn,sr);
		if(result>0) commit(conn);
		else		 rollback(conn);
		
		close(conn);
		
		return result;
	}
	
	/**관리자 회원탈퇴 서비스
	 * @param memberNo
	 * @return result
	 * @throws Exception
	 */
	public int secession(int memberNo)throws Exception {

		Connection conn = getConnection();

		int result = dao.secession(conn, memberNo);

		if (result > 0)
			commit(conn);
		else
			rollback(conn);

		close(conn);

		return result;
	}
	
	/**불량회원 신고
	 * @param rb
	 * @return result
	 * @throws Exception
	 */
	public int badMember(ReportBoard rb) throws Exception {
		Connection conn= getConnection();
		
		int result=dao.badMember(conn,rb);
		if(result>0) commit(conn);
		else		 rollback(conn);
		
		close(conn);
		
		return result;
	}
}
