package edu.kh.jdbc.board.view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc.board.model.service.BoardService;
import edu.kh.jdbc.board.model.service.CommentService;
import edu.kh.jdbc.board.model.vo.Board;
import edu.kh.jdbc.board.model.vo.Comment;
import edu.kh.jdbc.main.view.MainView;
import edu.kh.jdbc.member.vo.Member;

public class BoardView {
	private Scanner sc = new Scanner(System.in);
	private BoardService bService= new BoardService();
	private CommentService cService= new CommentService();
	
	public void boardMenu() {
		
		int input= -1;
		do {
			try {
				System.out.println("\n*****게시판 기능*****\n");
				System.out.println("1. 상품 목록 조회");
				System.out.println("2. 상품 상세 조회(+ 댓글 기능)");
				System.out.println("3. 상품 등록");
				System.out.println("4. 상품 검색");
				System.out.println("5. 매진 상품 검색");
				System.out.println("0. 로그인 메뉴로 이동");
				
				System.out.print("\n메뉴 선택 : ");
				input=sc.nextInt();
				sc.nextLine();
				
				System.out.println();
				switch (input) {
				case 1: selectAllBoard(); break;
				case 2: selectBoard(); 	  break;
				case 3: insertBoard();	  break;
				case 4: searchBoard();    break;
				case 5: searchSoldOut();  break;
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
	


	/**
	 * 상품 목록 조회
	 */
	private void selectAllBoard() {
		System.out.println("\n[상품 목록 조회]\n");
		
		try {
			List<Board>boardList=bService.selectAllBoard();
			//DAO에서 new ArrayList<>(); 구문으로 인해 반환되는 조회 결과는 null이 될 수 없다
			
			if(boardList.isEmpty()) { 
				System.out.println("게시글이 존재하지 않습니다");
			} else {
				for (Board b : boardList) {
					System.out.println("상품번호 | 판매상품  |  가격  |   작성자   |    등록일    | 거래지역 | 조회수 ");
					System.out.printf("%5d | %3s[%d] | %2d원 |  %s | %s | %s | %d\n", 
							b.getGoodsNo(), b.getGoodsName(), b.getCommentCount(), b.getGoodsPrice(),
							b.getMemberId(), b.getEnrollDate(), b.getDealPlace(), b.getReadCount());
				}
			}
		} catch (Exception e) {
			System.out.println("\n<<게시글 목록 조회 중 예외 발생>>\n");
			e.printStackTrace();
		}

	}


	/**
	 * 게시글 상세 조회
	 */
	private void selectBoard() {
		System.out.println("\n[상품 상세 조회]\n");
		try {
			System.out.print("상품 번호 입력 : ");
			int goodsNo=sc.nextInt();
			sc.nextLine();
			//게시글 상세 조회 서비스 호출 후 결과 반환 받기
			Board board=bService.selectBoard(goodsNo,MainView.loginMember.getMemberNo());
												 	//로그인한 회원번호 -> 자신의 글 조회수 증가x
			if (board != null) {
		            System.out.println(" --------------------------------------------------------");
		            System.out.printf("상품번호 : %d | 상품 : %s | 가격 : %d원 \n",
		            		board.getGoodsNo(), board.getGoodsName(), board.getGoodsPrice());
		            System.out.printf("판매자ID : %s | 거래지역 : %s | 작성일 : %s | 조회수 : %d\n", 
		                  board.getMemberId(),board.getDealPlace(),  board.getEnrollDate().toString(), board.getReadCount());
		            System.out.println(" --------------------------------------------------------");
		            System.out.println(board.getGoodsContent());
		            System.out.println(" --------------------------------------------------------");
		            // 댓글 목록
		            if(!board.getCommentList().isEmpty()) {
		               for(Comment c : board.getCommentList()) {
		                  System.out.printf("댓글번호: %d   작성자: %s  작성일: %s\n%s\n",
		                        c.getCommentNo(), c.getMemberId(), c.getCreateDate(), c.getCommentContent());
		                  System.out.println(" --------------------------------------------------------");
		               }
		            }
		            // 댓글 등록, 수정, 삭제
		            // 게시글 수정/삭제 메뉴
		            subBoardMenu(board);

			}else {
			     System.out.println("해당 번호의 상품이 존재하지 않습니다.");
			}
		} catch (Exception e) {
			System.out.println("\n<<상품 상세 조회중 예외 발생>>\n");
		}
	}

	/**게시글 상세 조회 시 출력되는 서브메뉴
	 * @param board(상세조회된 게시글 + 작성자 번호 + 댓글 목록)
	 */
	private void subBoardMenu(Board board) {
		
		try {
			System.out.println("1) 댓글 등록");     //1,2,3번은 재귀호출이 필요
			System.out.println("2) 댓글 수정");
			System.out.println("3) 댓글 삭제");
			
			//로그인한 회원과 게시글 작성자가 같은 경우에 출력되는 메뉴
			if(MainView.loginMember.getMemberNo()==board.getMemberNo()) {
				System.out.println("4) 가격, 거래지, 상품내용 수정");
				System.out.println("5) 게시글 삭제");
				System.out.println("6) 판매완료 설정");
			}
			
			System.out.println("0) 게시판 메뉴로 돌아가기");
			
			System.out.print("\n서브 메뉴 선택 : ");
			int input=sc.nextInt();
			sc.nextLine();
			
			//로그인멤버 필요 
			int memberNo=MainView.loginMember.getMemberNo();
			
			switch (input) {
			case 1: insertComment(board.getGoodsNo(), memberNo); break;
			case 2: updateComment(board.getCommentList(),memberNo); break;
			case 3: deleteComment(board.getCommentList(),memberNo); break;
			case 0: System.out.println("\n[게시판 메뉴로 돌아갑니다]\n"); break;
			
			case 4: case 5: case 6:
				if(MainView.loginMember.getMemberNo()==board.getMemberNo()) {
					
					if(input==4) {
						updateBoard(board.getGoodsNo());
					}
					
					if(input==5) {
						deleteBoard(board.getGoodsNo());
						input=0; 
					}
					if(input==6) {
						soldOut(board.getGoodsName(), board.getGoodsNo());
					}
					
					break; 
				}
			
			default: System.out.println("\n[메뉴에 작성된 번호만 입력해주세요]\n");
			}
			
			//댓글 등록,수정,삭제 선택 시 각각의 서비스 메서드 종료 후 다시 서브메뉴 메서드 호출==재귀호출
			if(input>0) {
				try {
					board = bService.selectBoard(board.getGoodsNo(), MainView.loginMember.getMemberNo());

					System.out.println(" --------------------------------------------------------");
					System.out.printf("상품번호 : %d | 상품 : %s | 가격 : %d원 \n",
							board.getGoodsNo(), board.getGoodsName(), board.getGoodsPrice());
					System.out.printf("판매자ID : %s | 거래지역 : %s | 작성일 : %s | 조회수 : %d\n", 
							board.getMemberId(),board.getDealPlace(), board.getEnrollDate().toString(), board.getReadCount());
					System.out.println(" --------------------------------------------------------");
					System.out.println(board.getGoodsContent());
					System.out.println(" --------------------------------------------------------");

					// 댓글 목록
					if (!board.getCommentList().isEmpty()) {
						for (Comment c : board.getCommentList()) {
							System.out.printf("댓글번호: %d   작성자: %s  작성일: %s\n%s\n", c.getCommentNo(), c.getMemberId(),
									c.getCreateDate(), c.getCommentContent());
							System.out.println(" --------------------------------------------------------");
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				subBoardMenu(board);//자기가 자기 호출 == 재귀 호출
			}
		} catch (InputMismatchException e) {
			System.out.println("\n<<입력 형식이 올바르지 않습니다>>\n");
			sc.nextLine();
		}
	}


	/**댓글 등록
	 * @param bNo
	 * @param mNo
	 */
	private void insertComment(int bNo, int mNo) {
		try {
			System.out.println("\n[댓글 등록]\n");
			
			String content =inputContent(); //내용 입력
			
			Comment comment=new Comment();
			comment.setCommentContent(content);
			comment.setGoodsNo(bNo);
			comment.setMemberNo(mNo);
			
			// 댓글 삽입 서비스 호출 후 결과 반환 받기
			int result=cService.insertComment(comment);
			
			if(result>0) {
				System.out.println("\n[댓글 등록 성공]\n");
			}else{	
				System.out.println("\n[댓글 등록 실패]\n");	
			}
			
		} catch (Exception e) {
			System.out.println("\n<<댓글 등록 중 예외 발생>>\n");
			e.printStackTrace();
		}
		
	}


	/**
	 * 내용 입력
	 */
	public String inputContent() {
		String content =""; //빈 문자열
		String input=null;
		System.out.println("입력 종료 시 // 입력");
		while (true){
			input = sc.nextLine();
			
			if(input.equals("//")) {
				break;
			}
			//입력된 내용을 content에 누적
			content += input ;
		}
		
		return content;
	}

	/** 댓글 수정
	 * @param commentList
	 * @param memberNo
	 */
	private void updateComment(List<Comment> commentList, int memberNo) {
		
		// 댓글 번호를 입력 받아 
		// 1) 해당 댓글이 commentList에 있는지 검사
		// 2) 있다면 해당 댓글이 로그인한 회원이 작성한 글인지 검사
		
		try {
			System.out.println("\n[댓글 수정]\n");
			System.out.println("수정할 댓글 번호 입력 : ");
			int commentNo=sc.nextInt();
			sc.nextLine();
			
			boolean flag=true; //번호 일치 검사
			for(Comment c : commentList) {
				if(c.getCommentNo()==commentNo) { //댓글 번호 일치
					flag=false;
					if(c.getMemberNo()==memberNo) { //회원 번호 일치
						
						//수정할 댓글 내용 입력 받기
						String content = inputContent();

						//댓글 수정 서비스 호출
						int result=cService.updateComment(commentNo,content);
						
						if(result>0) {
							System.out.println("\n[댓글 수정 성공]\n");
						}else {
							System.out.println("\n[댓글 수정 실패]\n");
						}
						
					} else {
						System.out.println("\n[자신의 댓글만 수정할 수 있습니다]\n");
					}
					break; //더 이상의 검사 불필요
				}
			}
			if(flag) { //for문 실행 후에 flag가 true면 댓글x
				System.out.println("\n[번호가 일치하는 댓글이 없습니다]\n");
			}
			
		} catch (Exception e) {
			System.out.println("\n<<댓글 수정중 예외 발생>>\n");
		}
		
		
	}

	/** 댓글 삭제
	 * @param commentList
	 * @param memberNo
	 */
	private void deleteComment(List<Comment> commentList, int memberNo) {
		try {
			System.out.println("\n[댓글 삭제]\n");
			System.out.println("삭제할 댓글 번호 입력 : ");
			int commentNo=sc.nextInt();
			sc.nextLine();
			
			boolean flag=true; //번호 일치 검사
			for(Comment c : commentList) {
				if(c.getCommentNo()==commentNo) { //댓글 번호 일치
					flag=false;
					if(c.getMemberNo()==memberNo) { //회원 번호 일치
						//정말 삭제? y/n -> y인 경우 댓글 삭제 서비스 호출
						System.out.print("정말 삭제 하시겠습니까? (Y/N)");
						char ch=sc.next().toUpperCase().charAt(0);
						
						if(ch=='Y') {
							int result = cService.deleteComment(commentNo);
							
							if(result>0) {
								System.out.println("\n[댓글 삭제 성공]\n");
							}else {
								System.out.println("\n[댓글 삭제 실패]\n");
							}	
						}else {
							System.out.println("\n[취소 되었습니다]\n");
						}

					} else {
						System.out.println("\n[자신의 댓글만 삭제할 수 있습니다]\n");
					}
					break; //더 이상의 검사 불필요
				}
			}
			if(flag) { //for문 실행 후에 flag가 true면 댓글x
				System.out.println("\n[번호가 일치하는 댓글이 없습니다]\n");
			}
		} catch (Exception e) {
			System.out.println("\n<<댓글 삭제중 예외 발생>>\n");
		}
	}


	/**게시글 수정
	 * @param boardNo
	 */
	private void updateBoard(int goodsNo) {
		try {
			System.out.println("\n[게시글 수정]\n");
			System.out.print("수정할 가격 입력 : ");
			int goodsPrice=sc.nextInt();
			
			System.out.print("수정할 거래지 입력 : ");
			String dealPlace=inputContent();
			
			System.out.print("수정할 내용 입력 : ");
			String goodsContent=inputContent();
			
			Board board=new Board();
			board.setGoodsNo(goodsNo);
			board.setGoodsPrice(goodsPrice);
			board.setDealPlace(dealPlace);
			board.setGoodsContent(goodsContent);
			
			int result=bService.updateBoard(board);
			
			if(result>0) {
				System.out.println("\n[게시글 수정 성공]\n");
			}else {
				System.out.println("\n[게시글 수정 실패]\n");
			}
		} catch (Exception e) {
			System.out.println("\n<<게시글 수정중 예외 발생>>\n");
		}
	}
	
	
	
	/** 게시글 삭제
	 * @param boardNo
	 */
	private void deleteBoard(int goodsNo) {
		try {
			System.out.println("\n[게시글 삭제]\n");
			System.out.print("정말 삭제 하시겠습니까?(Y/N)");
			char ch=sc.next().toLowerCase().charAt(0);
			
			if(ch=='y') {
				//삭제서비스 호출
				int result=bService.deleteBoard(goodsNo);
				
				if(result>0) {
					System.out.println("\n[게시글 삭제 성공]\n");
				}else {
					System.out.println("\n[게시글 삭제 실패]\n");
				}
			}else {
				System.out.println("\n[삭제 취소]\n");
			}
		} catch (Exception e) {
			System.out.println("\n<<게시글 삭제중 예외 발생>>\n");
		}
	}
	
	/**
	 * 판매완료 설정
	 * @param goodsNo 
	 */
	private void soldOut(String goodsName, int goodsNo) {
		try {
			System.out.println("\n[판매완료 설정]\n");
			System.out.print("판매완료 설정을 원하십니까? (Y/N)");
			char ch = sc.next().toLowerCase().charAt(0);

			if (ch == 'y') {
				
				int result = bService.soldOut(goodsName, goodsNo);

				if (result > 0) {
					System.out.println("\n[판매완료 설정 완료]\n");
				} else {
					System.out.println("\n[판매완료 설정 실패]\n");
				}
			} else {
				System.out.println("\n[설정 취소]\n");
			}
		} catch (Exception e) {
			System.out.println("\n<<판매완료 설정 중 예외 발생>>\n");
			e.printStackTrace();
			
		}

	}
	
	/**
	 * 게시글 등록
	 * 
	 */
	private void insertBoard() {
		try {
			System.out.println("\n[상품 등록]\n");
			if (MainView.loginMember.getSellerRight().equals("Y")) {

			System.out.print("상품 : ");
			String goodsName = sc.nextLine();

			System.out.print("가격 : ");
			int goodsPrice = sc.nextInt();
			
			System.out.print("거래지역(OO구 OO동) : ");
			String dealPlace = inputContent();

			System.out.print("내용 : ");
			String goodsContent = inputContent();

			
				// Board객체에 제목, 내용, 회원 번호를 담아서 서비스에 전달
				Board board = new Board();
				board.setGoodsName(goodsName);
				board.setGoodsPrice(goodsPrice);
				board.setDealPlace(dealPlace);
				board.setGoodsContent(goodsContent);
				board.setMemberNo(MainView.loginMember.getMemberNo());

				int result = bService.insertBoard(board);
				// 0또는 생성된 게시글 번호(0초과)

				if (result > 0) {
					System.out.println("\n[게시글이 등록되었습니다]\n");

					// 게시글 상세 조회 서비스 호출 후 결과 반환 받기
					Board b = bService.selectBoard(result, MainView.loginMember.getMemberNo());
					// 로그인한 회원번호 -> 자신의 글 조회수 증가x
					if (b != null) {
						System.out.println(" --------------------------------------------------------");
						System.out.printf("상품번호 : %d | 상품 : %s | 가격 : %d원  \n",
								b.getGoodsNo(), b.getGoodsName(),b.getGoodsPrice());
						System.out.printf("판매자ID : %s | 거래지역 : %s | 작성일 : %s | 조회수 : %d\n",
								b.getMemberId(),b.getDealPlace() ,b.getEnrollDate().toString(), b.getReadCount());
						System.out.println(" --------------------------------------------------------");
						System.out.println(board.getGoodsContent());
						System.out.println(" --------------------------------------------------------");
						// 댓글 목록
						if (!b.getCommentList().isEmpty()) {
							for (Comment c : b.getCommentList()) {
								System.out.printf("댓글번호: %d   작성자: %s  작성일: %s\n%s\n", c.getCommentNo(),
										c.getMemberId(), c.getCreateDate(), c.getCommentContent());
								System.out.println(" --------------------------------------------------------");
							}
						}
						// 댓글 등록, 수정, 삭제
						// 게시글 수정/삭제 메뉴
						subBoardMenu(b);

					} else {
						System.out.println("해당 번호의 상품이 존재하지 않습니다.");
					}
				} else {
					System.out.println("\n[게시글 등록 실패]\n");
				}
			} else {
				System.out.println("\n판매권한이 없습니다\n");
				System.out.println("\n상품등록을 원하시면 회원기능->5.판매자 신청을 해주세요\n");
			}

		} catch (Exception e) {
			System.out.println("\n<<게시글 등록 중 예외 발생>>\n");
			e.printStackTrace();
		}

	}
	

	/**
	 * 게시글 검색
	 */
	private void searchBoard() {
		try {
			System.out.println("\n[게시글 검색]\n");
			System.out.println("1) 상품");
			System.out.println("2) 상품 + 가격");
			System.out.println("3) 상품 + 거래지역");
			System.out.println("4) 상품 + 내용");			
			System.out.println("5) 작성자");
			System.out.print("검색 조건 선택 : ");
			int condition=sc.nextInt();
			sc.nextLine();
			
			
			if (condition >= 1 && condition <= 5) { // 정상 입력
				int query2=0;
				if (condition == 2) {
					System.out.print("상품 입력 : ");
					String query = sc.nextLine();
					System.out.print("가격 입력 : ");
					query2 = sc.nextInt();
					
					// 검색 서비스 호출 후 결과 반환 받기
					List<Board> boardList = bService.searchBoard(condition, query, query2);
					if (boardList.isEmpty()) {
						System.out.println("\n[검색 결과가 없습니다]\n");
					} else {
						for (Board b : boardList) {
							System.out.println("상품번호 | 판매상품  |  가격  |   판매자   |    등록일    | 거래지역 | 조회수");
							System.out.printf("%5d | %3s[%d] | %2d원 |  %s | %s | %s \n", b.getGoodsNo(),
									b.getGoodsName(), b.getCommentCount(), b.getGoodsPrice(), b.getMemberId(),
									b.getEnrollDate(), b.getDealPlace(), b.getReadCount());
						}
					}
				} else {
					System.out.print("검색어 입력 : ");
					String query = sc.nextLine();

					// 검색 서비스 호출 후 결과 반환 받기
					List<Board> boardList = bService.searchBoard(condition, query, query2);
					if (boardList.isEmpty()) {
						System.out.println("\n[검색 결과가 없습니다]\n");
					} else {
						for (Board b : boardList) {
							System.out.println("상품번호 | 판매상품  |  가격  |   판매자   |    등록일    | 거래지역 | 조회수 ");
							System.out.printf("%5d | %3s[%d] | %2d원 |  %s | %s | %s | %d \n", 
									b.getGoodsNo(), b.getGoodsName(), b.getCommentCount(), b.getGoodsPrice(),
									b.getMemberId(),b.getEnrollDate(), b.getDealPlace(), b.getReadCount());
						}
					}
				}
			}else { //비정상 입력
				System.out.println("\n[1~5번 사이의 숫자를 입력해주세요]\n");
			}
		} catch (Exception e) {
			System.out.println("\n<<상품 검색 중 예외 발생>>\n");
			e.printStackTrace();
		}
	}

	/**
	 * 매진상품 검색
	 */
	private void searchSoldOut() {
		try {
			System.out.println("\n[매진 상품 검색]\n");
			System.out.print("상품 입력 : ");
			String query = sc.nextLine();

			List<Board> boardList = bService.searchSoldOut(query);
			if (boardList.isEmpty()) {
				System.out.println("\n[검색 결과가 없습니다]\n");
			} else {
				for (Board b : boardList) {
					System.out.println("상품번호 |   매진 상품    |  가격  |   판매자   |    등록일    | 거래지역 ");
					System.out.printf("%5d | %3s | %2d원 |  %s | %s | %s \n",
							b.getGoodsNo(), b.getGoodsName(),  b.getGoodsPrice(),
							b.getMemberId(), b.getEnrollDate(),b.getDealPlace());
				}
			}
		} catch (Exception e) {
			System.out.println("\n<<상품 검색 중 예외 발생>>\n");
			e.printStackTrace();
		}

	}



	
}
