package edu.kh.jdbc.board.model.service;

import static edu.kh.jdbc.common.JDBCTemplate.close;
import static edu.kh.jdbc.common.JDBCTemplate.commit;
import static edu.kh.jdbc.common.JDBCTemplate.getConnection;
import static edu.kh.jdbc.common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.List;

import edu.kh.jdbc.board.model.dao.BoardDAO;
import edu.kh.jdbc.board.model.dao.CommentDAO;
import edu.kh.jdbc.board.model.vo.Board;
import edu.kh.jdbc.board.model.vo.Comment;

public class BoardService {

	private BoardDAO dao= new BoardDAO();
	
	private CommentDAO cDAO=new CommentDAO();
	
	/**상품목록 전체조회 서비스
	 * @return boardList
	 * @throws Exception
	 */
	public List<Board> selectAllBoard() throws Exception {
		Connection conn=getConnection();
		List<Board>boardList=dao.selectAllBoard(conn);
		
		close(conn);
		
		return boardList;
	}

	/**게시글 상세조회 서비스
	 * @param goodsNo
	 * @param memberNo
	 * @return
	 * @throws Exception
	 */
	public Board selectBoard(int goodsNo, int memberNo) throws Exception {
		Connection conn=getConnection();
		
		// 1. 게시글 상세 조회 DAO 호출
		Board board=dao.selectBoard(conn,goodsNo);
		// -> 조회결과가 없으면 null, 있으면 null x
		
		if(board != null) { //게시글이 존재
			//2. 댓글 목록 조회 DAO 호출
			List<Comment>commentList=cDAO.selectCommentList(conn,goodsNo);
			// 조회된 댓글 목록을 board에 저장
			board.setCommentList(commentList);
			
			//3. 조회수 증가(로그인 회원 != 게시글 작성자)
			if(memberNo != board.getMemberNo()) {
				int result=dao.increaseReadCount(conn,goodsNo);
				
				if(result>0) { commit(conn); //미리 조회된 board의 조회수를 증가된 DB의 조회수와
										   //동일한 값을 가지도록 동기화
							 board.setReadCount(board.getReadCount()+1);
				}else  	   	 rollback(conn);
			}
		}
		close(conn);
		
		return board;
	}


	/**게시글 수정 서비스
	 * @param board
	 * @return result
	 * @throws Exception
	 */
	public int updateBoard(Board board) throws Exception {
		Connection conn=getConnection();
		
		int result=dao.updateBoard(conn, board);
		
		if(result>0) commit(conn);
		else		 rollback(conn);
		
		close(conn);
		
		return result;
	}

	/** 게시글 삭제 서비스
	 * @param goodsNo
	 * @return result
	 * @throws Exception
	 */
	public int deleteBoard(int goodsNo)  throws Exception {
		Connection conn=getConnection();
		
		int result=dao.deleteBoard(conn, goodsNo);
		
		if(result>0) commit(conn);
		else		 rollback(conn);
		
		close(conn);
		
		return result;
	}

	/**게시글 등록 서비스
	 * WEB에서 CURRVAL 사용시 오류가 생길 수 있다 
	 * @param board
	 * @return board
	 * @throws Exception
	 */
	public int insertBoard(Board board) throws Exception {
		Connection conn=getConnection();
		
		// 게시글 번호 생성 dao 호출
		// 왜? 동시에 여러 사림이 게시글을 등록하면 시퀀스가 한번에 증가하여 CURRVAL 구문을 이용하면 문제가 발생
		// -> 게시글 등록 서비스를 호출한 순서대로 미리 게시글 번호를 생성해 얻어와 insert한다
		
		int goodsNo=dao.nextGoodsNo(conn);
		
		board.setGoodsNo(goodsNo);
		
		
		int result=dao.insertBoard(conn, board);
		
		if(result>0) { 
			commit(conn);
			result=goodsNo;	
		
		}
		else		 rollback(conn);
		
		close(conn);
		
		return result;
	}
	
	/**게시글 검색
	 * @param condition
	 * @param query
	 * @return boardList
	 * @throws Exception
	 */
	public List<Board> searchBoard(int condition, String query, int query2) throws Exception {
		Connection conn=getConnection();
		
		List<Board>boardList=dao.searchBoard(conn, condition, query, query2);
		
		close(conn);
		
		return boardList;
	}

	public int soldOut(String goodsName, int goodsNo) throws Exception {
		Connection conn = getConnection();

		int result = dao.soldOut(conn, goodsName, goodsNo);

		if (result > 0)
			commit(conn);
		else
			rollback(conn);

		close(conn);

		return result;
	}

	/**매진 상품 검색 서비스
	 * @param query
	 * @return boardList
	 * @throws Exception
	 */
	public List<Board> searchSoldOut(String query) throws Exception {
		Connection conn=getConnection();
		
		List<Board>boardList=dao.searchSoldOut(conn,  query);
		
		close(conn);
		
		return boardList;
	}
	
	
	
	
}
