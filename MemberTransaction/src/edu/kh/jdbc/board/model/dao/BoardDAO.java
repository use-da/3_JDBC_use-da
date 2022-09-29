package edu.kh.jdbc.board.model.dao;

import static edu.kh.jdbc.common.JDBCTemplate.close;

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
	/**상품목록 전체조회 DAO
	 * @param conn
	 * @return boardList
	 * @throws Exception
	 */
	public List<Board> selectAllBoard(Connection conn)throws Exception {
		List<Board>boardList=new ArrayList<>(); 
		try {
			String sql=prop.getProperty("selectAllBoard");
			
			stmt=conn.createStatement();
			
			rs=stmt.executeQuery(sql);
			
			while(rs.next()) {
				
				int goodsNo=rs.getInt("GOODS_NO");
				String goodsName=rs.getString("GOODS_NM");
				int goodsPrice=rs.getInt("GOODS_PRICE");
				String memberId=rs.getString("MEMBER_ID");
				String dealPlace=rs.getString("DEAL_PLACE");
				String enrollDate=rs.getString("ENROLL_DT");
				int readCount=rs.getInt("READ_COUNT");
				int commentCount=rs.getInt("COMMENT_COUNT");
						
				Board board=new Board();
				board.setGoodsNo(goodsNo);
				board.setGoodsName(goodsName);
				board.setGoodsPrice(goodsPrice);
				board.setMemberId(memberId);
				board.setDealPlace(dealPlace);
				board.setEnrollDate(enrollDate);
				board.setReadCount(readCount);
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
	 * @param goodsNo
	 * @return board
	 * @throws Exception
	 */
	public Board selectBoard(Connection conn, int goodsNo) throws Exception{
		//결과 저장용 변수 선언
		Board board=null;//Service if문 고려
		
		try {
			String sql=prop.getProperty("selectBoard");
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, goodsNo);
			rs=pstmt.executeQuery();
			
			if(rs.next()) { //조회 결과 있을 경우
				board = new Board(); //Board 객체 생성 board !=null
				board.setGoodsNo(		rs.getInt("GOODS_NO"));
				board.setGoodsName(	rs.getString("GOODS_NM"));
				board.setGoodsPrice(rs.getInt("GOODS_PRICE"));
				board.setDealPlace(rs.getString("DEAL_PLACE"));
				board.setGoodsContent(	rs.getString("GOODS_CONTENT"));
				board.setMemberNo(		rs.getInt("MEMBER_NO"));
				board.setMemberId(	rs.getString("MEMBER_ID"));
				board.setReadCount(		rs.getInt("READ_COUNT"));
				board.setEnrollDate(	rs.getString("ENROLL_DT"));
			}
		} finally {
			close(rs);
			close(pstmt);
		}
		return board;
	}

	public int increaseReadCount(Connection conn, int goodsNo) throws Exception{
		int result=0;
		try {
			String sql=prop.getProperty("increaseReadCount");
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, goodsNo);
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
			
			pstmt.setInt(1, board.getGoodsPrice() );
			pstmt.setString(2, board.getDealPlace());
			pstmt.setString(3, board.getGoodsContent() );
			pstmt.setInt(4, board.getGoodsNo());
			
			result=pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
		return result;
	}

	/**게시글 삭제 DAO
	 * @param conn
	 * @param goodsNo
	 * @return result
	 * @throws Exception
	 */
	public int deleteBoard(Connection conn, int goodsNo) throws Exception {
		int result=0; 
		try {
			String sql=prop.getProperty("deleteBoard");
			
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setInt(1, goodsNo );

			result=pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
		return result;
	}

	/**게시글 등록 DAO
	 * @param conn
	 * @param board
	 * @return result
	 * @throws Exception
	 */
	public int insertBoard(Connection conn, Board board) throws Exception  {
		int result=0; 
		try {
			String sql=prop.getProperty("insertBoard");
			
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setInt(1, board.getGoodsNo() );
			pstmt.setString(2, board.getGoodsName()  );
			pstmt.setInt(3, board.getGoodsPrice() );
			pstmt.setString(4, board.getDealPlace() );			
			pstmt.setString(5, board.getGoodsContent() );
			pstmt.setInt(6, board.getMemberNo() );

			result=pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
		return result;
	}

	/**다음 게시글 번호 생성 DAO
	 * @param conn
	 * @return goodsNo
	 * @throws Exception
	 */
	public int nextGoodsNo(Connection conn) throws Exception {
		int goodsNo=0;
		try {
			String sql=prop.getProperty("nextGoodsNo");
			
			pstmt=conn.prepareStatement(sql);
			
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				goodsNo=rs.getInt(1); //첫번째 컬럼값을 얻어와 goodsNo에 저장
			}
			
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return goodsNo;
	}
	/**게시글 검색 DAO
	 * @param conn
	 * @param condition
	 * @param query
	 * @return boardList;
	 * @throws Exception
	 */
	public List<Board> searchBoard(Connection conn, int condition, String query, int query2) throws Exception {
		
		List<Board> boardList = new ArrayList<>();
		
		try {
			String sql=prop.getProperty("searchBoard1")
						+prop.getProperty("searchBoard2_"+condition)
						+prop.getProperty("searchBoard3");
			
			pstmt=conn.prepareStatement(sql);
		
			pstmt.setString(1, query);
			
			if(condition==2) {
				pstmt.setInt(2,query2);
			}
			if(condition==3 || condition==4) {
				pstmt.setString(2,query);
			}
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				
				int goodsNo=rs.getInt("GOODS_NO");
				String goodsName=rs.getString("GOODS_NM");
				int goodsPrice=rs.getInt("GOODS_PRICE");
				String memberId=rs.getString("MEMBER_ID");
				String dealPlace=rs.getString("DEAL_PLACE");
				int readCount=rs.getInt("READ_COUNT");
				String enrollDate=rs.getString("ENROLL_DT");
				int commentCount=rs.getInt("COMMENT_COUNT");
						
				Board board=new Board();
				board.setGoodsNo(goodsNo);
				board.setGoodsName(goodsName);
				board.setGoodsPrice(goodsPrice);
				board.setMemberId(memberId);
				board.setDealPlace(dealPlace);
				board.setReadCount(readCount);
				board.setEnrollDate(enrollDate);
				board.setCommentCount(commentCount);
				
				boardList.add(board);
			}
		} finally {
			close(rs);
			close(pstmt);
			
		}
		return boardList;
	}
	public int soldOut(Connection conn,String goodsName, int goodsNo) throws Exception {
		int result=0; 
		try {
			String sql=prop.getProperty("soldOut");
			
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, goodsName);
			pstmt.setInt(2, goodsNo );

			result=pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
		return result;
		
	}
	
	
	public List<Board> searchSoldOut(Connection conn, String query) throws Exception {

		List<Board> boardList = new ArrayList<>();
		
		try {
			String sql=prop.getProperty("searchSoldOut");
	
			pstmt=conn.prepareStatement(sql);
		
			pstmt.setString(1, query);
			
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				
				int goodsNo=rs.getInt("GOODS_NO");
				String goodsName=rs.getString("GOODS_NM");
				int goodsPrice=rs.getInt("GOODS_PRICE");
				String memberId=rs.getString("MEMBER_ID");
				String dealPlace=rs.getString("DEAL_PLACE");
				int readCount=rs.getInt("READ_COUNT");
				String enrollDate=rs.getString("ENROLL_DT");
				int commentCount=rs.getInt("COMMENT_COUNT");
						
				Board board=new Board();
				board.setGoodsNo(goodsNo);
				board.setGoodsName(goodsName);
				board.setGoodsPrice(goodsPrice);
				board.setMemberId(memberId);
				board.setDealPlace(dealPlace);
				board.setReadCount(readCount);
				board.setEnrollDate(enrollDate);
				board.setCommentCount(commentCount);
				
				boardList.add(board);
			}
		} finally {
			close(rs);
			close(pstmt);
			
		}
		return boardList;
	}
	
	
	
	
	
}
