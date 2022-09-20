package edu.kh.jdbc.main.run;

import edu.kh.jdbc.main.view.MainView;

// 실행용 클래스
public class MainRun {
	public static void main(String[] args) {
		
		new MainView().mainMenu();
		
		// System.exit(0); 내부적으로 존재(컴파일러가 자동 추가)
		
		/*
		 
		 회원기능 (Member View, Service, DAO, member-query.xml)
    	 1. 내 정보 조회
    	 2. 회원 목록 조회(아이디, 이름, 성별)
   	 	 3. 내 정보 수정(이름, 성별)
    	 4. 비밀번호 변경(현재 비밀번호, 새 비밀번호, 새 비밀번호 확인)
      	 5. 회원 탈퇴
		    
		 게시판 기능 (Board View, Service, DAO, board-query.xml)   
		 1. 게시글 목록 조회(작성일 내림차순)
		   (게시글 번호, 제목, 작성자명, 작성일, 조회수, 댓글 수)
		 2. 게시글 상세 조회(게시글 번호 입력 받음)
		   (게시글 번호, 제목, 내용, 작성자명, 작성일, 조회수, 댓글 목록(작성일 오름차순))
		    2-1. 게시글 수정 (자신의 게시글만)
		    2-2. 게시글 삭제 (자신의 게시글만)
		    2-3. 댓글 작성
		    2-4. 댓글 수정 (자신의 댓글만)
		    2-5. 댓글 삭제 (자신의 댓글만)
		 3. 게시글 작성(제목, 내용 INSERT) -> 작성 성공 시 상세 조회 수행
		 4. 게시글 검색(제목, 내용, 제목+내용, 작성자)
		     
		 */
		
		
		
		
		
		
		
		
		
	}
	
	
	
}
