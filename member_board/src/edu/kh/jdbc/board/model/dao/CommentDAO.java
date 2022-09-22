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

import edu.kh.jdbc.board.model.vo.Comment;

public class CommentDAO {
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private Properties prop;
	
	public CommentDAO() {
		
		try {
			prop=new Properties();
			prop.loadFromXML(new FileInputStream("comment-query.xml"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**댓글 목록 조회DAO
	 * @param conn
	 * @param boardNo
	 * @return commentList
	 * @throws Exception
	 */
	public List<Comment> selectCommentList(Connection conn, int boardNo) throws Exception {
		
		List<Comment>commentList=new ArrayList<>();
		
		try {
			String sql=prop.getProperty("selectCommentList");
			
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1,boardNo);
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				int commentNo=rs.getInt("COMMENT_NO");
				String commentContent=rs.getString("COMMENT_CONTENT");
				int memberNo=rs.getInt("MEMBER_NO");
				String memberName=rs.getString("MEMBER_NM");
				String createDate=rs.getString("CREATE_DT");
				
				Comment comment=new Comment();
				comment.setCommentNo(commentNo);
				comment.setCommentContent(commentContent);
				comment.setMemberNo(memberNo);
				comment.setMemberName(memberName);
				comment.setCreateDate(createDate);
				
				commentList.add(comment);
			}
		} finally {
			close(rs);
			close(pstmt);
		}

		return commentList;
	}
}
