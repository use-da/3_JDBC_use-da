package edu.kh.jdbc.board.model.dao;

import static edu.kh.jdbc.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.kh.jdbc.board.model.vo.Board;

public class BoardDAO {
	
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private Properties prop;
	
	public BoardDAO() {
		
		try {
			prop=new Properties();
			prop.loadFromXML(new FileInputStream("board-query.xml"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**게시글 목록 조회 DAO
	 * @param conn
	 * @return boardList
	 * @throws Exception
	 */
	public List<Board> selectAllBoard(Connection conn) throws Exception {
		
		List<Board>boardList=new ArrayList<>(); 
		try {
			String sql=prop.getProperty("selectAllBoard");
			
			stmt=conn.createStatement();
			
			rs=stmt.executeQuery(sql);
			
			while(rs.next()) {
				
				int boardNo=rs.getInt("BOARD_NO");
				String boardTitle=rs.getString("BOARD_TITLE");
				String memberName=rs.getString("MEMBER_NM");
				int readCount=rs.getInt("READ_COUNT");
				String createDate=rs.getString("CREATE_DT");
				int commentCount=rs.getInt("COMMENT_COUNT");
						
				Board board=new Board();
				board.setBoardNo(boardNo);
				board.setBoardTitle(boardTitle);
				board.setMemberName(memberName);
				board.setReadCount(readCount);
				board.setCreateDate(createDate);
				board.setCommentCount(commentCount);
				
				boardList.add(board);
			}
		} finally {
			close(rs);
			close(stmt);
		}
		return boardList;
	}

	/**게시글 상세 조회DAO
	 * @param conn
	 * @param boardNo
	 * @return board
	 * @throws Exception
	 */
	public Board selectBoard(Connection conn, int boardNo) throws Exception{
		//결과 저장용 변수 선언
		Board board=null;//Service if문 고려
		
		try {
			String sql=prop.getProperty("selectBoard");
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			rs=pstmt.executeQuery();
			
			if(rs.next()) { //조회 결과 있을 경우
				board = new Board(); //Board 객체 생성 board !=null
				board.setBoardNo(		rs.getInt("BOARD_NO"));
				board.setBoardTitle(	rs.getString("BOARD_TITLE"));
				board.setBoardContent(	rs.getString("BOARD_CONTENT"));
				board.setMemberNo(		rs.getInt("MEMBER_NO"));
				board.setMemberName(	rs.getString("MEMBER_NM"));
				board.setReadCount(		rs.getInt("READ_COUNT"));
				board.setCreateDate(	rs.getString("CREATE_DT"));
			}
		} finally {
			close(rs);
			close(pstmt);
		}
		return board;
	}

	public int increaseReadCount(Connection conn, int boardNo) throws Exception{
		int result=0;
		try {
			String sql=prop.getProperty("increaseReadCount");
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			result=pstmt.executeUpdate();
			
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	/**게시글 수정 DAO
	 * @param conn
	 * @param board
	 * @return result
	 * @throws Exception
	 */
	public int updateBoard(Connection conn, Board board) throws Exception {
		int result=0; 
		try {
			String sql=prop.getProperty("updateBoard");
			
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, board.getBoardTitle() );
			pstmt.setString(2, board.getBoardContent() );
			pstmt.setInt(3, board.getBoardNo());
			
			result=pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
		return result;
	}

	/**게시글 삭제 DAO
	 * @param conn
	 * @param boardNo
	 * @return result
	 * @throws Exception
	 */
	public int deleteBoard(Connection conn, int boardNo) throws Exception {
		int result=0; 
		try {
			String sql=prop.getProperty("deleteBoard");
			
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setInt(1, boardNo );

			result=pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
}
