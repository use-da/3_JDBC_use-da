package edu.kh.jdbc.main.model.service;

import static edu.kh.jdbc.common.JDBCTemplate.*;

import java.sql.Connection;

import edu.kh.jdbc.common.JDBCTemplate;
import edu.kh.jdbc.main.model.dao.MainDAO;
import edu.kh.jdbc.manager.vo.Manager;
import edu.kh.jdbc.member.vo.Member;

//Service : 
public class MainService {

	private MainDAO dao=new MainDAO();

	
	/**아이디 중복 검사
	 * @param memberId
	 * @return result
	 * @throws Exception
	 */
	public int idDupCheck(String memberId) throws Exception {
		
		// 1. Connection 생성
		Connection conn= JDBCTemplate.getConnection();
		
		// 2. DAO 메서드 호출 후 결과 반환 받기
		int result = dao.idDupCheck(conn,memberId);
		
		// 3. Connection 반환 (SELECT 구문은 트랜잭션 제어 필요 X)
		close(conn);
		
		// 4. 결과 반환
		return result;
	}


	/** 회원 가입 서비스
	 * @param member
	 * @return result
	 * @throws Exception
	 */
	public int memberSignUp(Member member) throws Exception{
		// 1. Connection 생성
		Connection conn= getConnection();
		
		// 2. DAO 메서드 호출 후 결과 반환 받기
		int result = dao.memberSignUp(conn,member);
		
		// 3. 트랜잭션 제어 처리
		if(result>0) commit(conn);
		else		 rollback(conn);
		
		// 4. Connection 반환
		close(conn);
		
		// 5. 삽입 결과 반환
		return result;
	}

	/** 회원 로그인 서비스
	 * @param memberId
	 * @param memberPw
	 * @return loginMember
	 * @throws Exception
	 */
	public Member memberLogin(String memberId, String memberPw) throws Exception{
		
		// 1. Connection 생성
		Connection conn= getConnection();
				
		// 2. DAO 메서드 호출 후 결과 반환 받기
		Member loginMember=dao.memberLogin(conn,memberId,memberPw);
				
		// 3. Connection 반환
		close(conn);
		
		// 4. 조회 결과 반환
		return loginMember;
	
	}


	public Manager managerLogin(String managerId, String managerPw) throws Exception {
		
		Connection conn= getConnection();
		
		Manager loginManager=dao.managerLogin(conn,managerId,managerPw);
				
		close(conn);
		
		return loginManager;
	}
	
}
