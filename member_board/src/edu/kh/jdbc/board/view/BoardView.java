package edu.kh.jdbc.board.view;

import java.util.InputMismatchException;
import java.util.Scanner;

import edu.kh.jdbc.board.model.service.BoardService;
import edu.kh.jdbc.board.model.service.CommentService;

public class BoardView {
	private Scanner sc = new Scanner(System.in);
	private BoardService bService= new BoardService();
	private CommentService cService= new CommentService();

	/**
	 * 게시판 기능 메뉴화면
	 */
	public void BoardMenu() {
		
		int input= -1;
		
		do {
			try {
				System.out.println("\n*****게시판 기능*****\n");
				System.out.println("1. 게시글 목록 조회");
				System.out.println("2. 게시글 상세 조회(+ 댓글 기능)");
				System.out.println("3. 게시글 작성");
				System.out.println("4. 게시글 검색");
				System.out.println("0. 메인 메뉴로 이동");
				
				System.out.println("\n메뉴 선택 : ");
				input=sc.nextInt();
				sc.nextLine();
				
				System.out.println();
				switch (input) {
				case 1: break;
				case 2: break;
				case 3: break;
				case 4: break;
				case 0: System.out.println("[로그인 메뉴로 이동합니다]"); break;

				default: System.out.println("메뉴에 작성된 번호만 입력해주세요");
					
				}
				System.out.println();
				
				
			} catch (InputMismatchException e) {
				System.out.println("\n<<입력형식이 올바르지 않습니다>>\n");
				sc.nextLine();
			}
			
			
			
		} while (input!=0);
		
		
	}










}
