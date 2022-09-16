package edu.kh.jdbc.model.service;

// import static 구문
// -> static이 붙은 필드, 메서드를 호출할 때 클래스명을 생략할 수 있게 하는 구문
import static edu.kh.jdbc.common.JDBCTemplate.*;

import java.sql.Connection;
import java.sql.SQLException;

import edu.kh.jdbc.common.JDBCTemplate;
import edu.kh.jdbc.model.dao.TestDAO;
import edu.kh.jdbc.model.vo.TestVO;

// Service : 비즈니스 로직(데이터 가공, 트랜잭션 제어) 처리 
// -> 실제 프로그램이 제공하는 기능을 모아놓은 클래스

// 하나의 Service메서드에서 n개의 DAO 메서드(지정된 SQL수행)를 호출하여 이를(DML INSERT,DELETE,UPDATE묶음을)하나의 트랜잭션 단위로 취급하여
// 한번에 commit, rollback을 수행할 수 있다

// 여러 DML을 수행하지 않는 경우(단일 DML, SELECT)라도 코드의 통일성을 위해 Service에서 Connection객체를 생성한다
// Connection 객체가 commit, rollback제공
public class TestService {

	private TestDAO dao= new TestDAO();
	
	
	/** 1행 삽입 서비스
	 * @param vo1
	 * @return result
	 * @throws SQLException 
	 */
	public int insert(TestVO vo1) throws SQLException {
		
		//커넥션 생성
		Connection conn= JDBCTemplate.getConnection();
		
		//INSERT DAO메서드를 호출해 수행 후 결과 반환
		// -> Service에서 생성한 Connection 객체를 반드시 같이 전달해야한다
		int result = dao.insert(conn,vo1);
		//result==SQL 수행 후 반영된 결과행의 개수
		
		//트랜잭션 제어
		if(result>0) JDBCTemplate.commit(conn);
		else		 JDBCTemplate.rollback(conn);
		
		//커넥션 반환(close)
		JDBCTemplate.close(conn);
		
		//결과 반환
		return result;
	}


	/** 3행 INSERT 서비스
	 * @param vo1
	 * @param vo2
	 * @param vo3
	 * @return result
	 */
	public int insert(TestVO vo1, TestVO vo2, TestVO vo3) throws Exception {
											//throws Exception -> catch문에서 강제 발생된 예외를 호출부로 던진다
											//Run2에서 예외 상황에 대한 다른 결과를 출력하기 위해
		
		//1. Connection 생성
		Connection conn=getConnection();
		
		int res=0; // 3회 모두 성공 시 1 아니면 0
		
		try {
			//insert 중 오류가 발생하면 모든 insert내용 rollback
			//-> try-catch로 예외가 발생했다는 것을 인지
			int result1 = dao.insert(conn, vo1);
			int result2 = dao.insert(conn, vo2);
			int result3 = dao.insert(conn, vo3);
			
			//트랜잭션 제어
			if(result1+result2+result3==3) { //모두 insert성공
				commit(conn);
				res = 1;
			}else {
				rollback(conn);
//				res = 0;
			}
		} catch (SQLException e) {  //dao수행 중 예외 발생시
			rollback(conn);
			//-> 실패된 데이터를 DB에 삽입하지 않음
			//-> DB에는 성공된 데이터만 저장이 된다 == DB에 저장된 데이터의 신뢰도가 상승한다
			e.printStackTrace();
			
			//Run2클래스로 예외를 전달할 수 있도록 예외 강제 발생
			throw new Exception("DAO수행 중 예외 발생");
			
		} finally {  //무조건 conn 반환
			close(conn);
		}
		return res;
	}


	/** UPDATE서비스
	 * @param vo1
	 * @return result
	 * @throws SQLException 
	 */
	public int update(TestVO vo) throws SQLException {
		
		Connection conn= JDBCTemplate.getConnection();
				
		int result = dao.update(conn,vo);
		
		if(result>0) JDBCTemplate.commit(conn);
		else		 JDBCTemplate.rollback(conn);
				
		JDBCTemplate.close(conn);
				
		return result;
	}
}
