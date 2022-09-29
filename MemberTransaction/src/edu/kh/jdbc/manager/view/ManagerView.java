package edu.kh.jdbc.manager.view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc.board.model.service.BoardService;
import edu.kh.jdbc.main.view.MainView;
import edu.kh.jdbc.manager.model.service.ManagerService;
import edu.kh.jdbc.manager.vo.Manager;
import edu.kh.jdbc.member.model.service.MemberService;
import edu.kh.jdbc.member.vo.Member;
import edu.kh.jdbc.member.vo.ReportBoard;
import edu.kh.jdbc.member.vo.SRBoard;

public class ManagerView {
	private static Scanner sc = new Scanner(System.in);
	
	private static ManagerService mService=new ManagerService();
	
	public static Member loginMember=null;
	
	public static Manager loginManager = null;
	
	private static int input=-1;
	
	private static BoardService bService=new BoardService();
	
	private static MemberService service=new MemberService();
	
	public static void managerMenu(Manager loginManager) {


		do {
			try {
				System.out.println("\n*****관리자 기능*****\n");
				System.out.println("1. 판매 신청 조회");
				System.out.println("2. 판매 권한 부여");
				System.out.println("3. 중복 신청 제거");	
				System.out.println("4. 신고 조회");	
				System.out.println("5. 회원 게시글 삭제");
				System.out.println("6. 회원 탈퇴");
				System.out.println("0. 프로그램 종료");
				
				System.out.print("\n메뉴 선택 : ");
				input=sc.nextInt();
				sc.nextLine();
				
				System.out.println();				
				switch (input) {
				case 1: selectSRBoard();     break;
				case 2:	sellerRightUpdate(); break;
				case 3: deleteSRBoard();	 break;
				case 4: selectBadMember();	 break;
				case 5:	deleteBoard();		 break;
				case 6:	secession();	     break;
				case 0:
					
					System.out.println("[프로그램 종료]");
					System.exit(0);
					break;
				default:
					System.out.println("메뉴에 작성된 번호만 입력해주세요");

				}

			} catch (InputMismatchException e) {
				System.out.println("\n<<입력형식이 올바르지 않습니다>>");
				sc.nextLine();
			}
		} while (input != 0);

	}



	/**
	 * 신고 조회
	 */
	private static void selectBadMember() {
		System.out.println("\n[신고 조회]\n");

		try {
			List<ReportBoard> reportBoardList = mService.selectBadMember();

			if (reportBoardList.isEmpty()) {
				System.out.println("게시글이 존재하지 않습니다");
			} else {
				// 향상 for문( 저장할 변수 : Array,List )
				for (ReportBoard rb : reportBoardList) {
					System.out.println("-----------------------");
					System.out.println("신고번호 | 불량회원 ID | 신고내용 ");
					System.out.printf("%5d  | %5s   | %s    \n",
							rb.getRbNo(), rb.getBadMember(), rb.getReportContent());
				}
			}

		} catch (Exception e) {
			System.out.println("\n<<판매 신청 조회 중 예외 발생>>\n");
			e.printStackTrace();
		}

	}


	/**
	 * 중복 신청 제거
	 */
	private static void deleteSRBoard() {
		System.out.println("\n[중복 신청 제거]\n");
		try {
			System.out.print("삭제할 신청 번호 : ");
			int srBoardNo=sc.nextInt();
			
			System.out.println("중복된 신청글을 삭제 하시겠습니까?(Y/N)");
			char ch=sc.next().toUpperCase().charAt(0);
			while (true) {
				if (ch=='Y') {
					int result = mService.deleteSRBoard(srBoardNo);
					
					if(result>0) {
						System.out.println("\n[중복 제거 성공]\n");
						break;
					}else {
						System.out.println("\n[중복 제거 실패]\n");
						break;
					}
				}else {
					System.out.println("잘못 입력하셨습니다");
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("<<중복 신청글 삭제 중 예외 발생>>");
			e.printStackTrace();
		}
		
		
	}



	/**
	 * 판매 신청 조회
	 */
	private static void selectSRBoard() {
		System.out.println("\n[판매 신청 조회]\n");
		
		try {
			List<SRBoard>srBoardList=mService.selectSRBoard();
			
			if(srBoardList.isEmpty()) { 
				System.out.println("게시글이 존재하지 않습니다");
			} else { 
				//향상 for문( 저장할 변수 : Array,List )
				for(SRBoard sr : srBoardList) {
					System.out.println("-----------------------");
					System.out.println("판매신청 번호 | 회원번호 |    전화번호    | 승인");
					System.out.printf("%5d     | %3d    | %s | %3s \n",
					sr.getSrBoardNo(), sr.getMemberNo(), sr.getPhone(), sr.getSellerRight());
				}
			}
			
		}catch (Exception e) {
			System.out.println("\n<<판매 신청 조회 중 예외 발생>>\n");
			e.printStackTrace();
		}
		
	}

	/**
	 * 판매 권한 부여
	 */
	private static void sellerRightUpdate() {
		System.out.println("\n[판매 권한 승인]\n");
		try {
			System.out.print("승인할 회원 번호 : ");
			int memberNo=sc.nextInt();
			
			System.out.println("판매 권한을 승인하시겠습니까?(Y/N)");
			String sellerRight=sc.next().toUpperCase();
			while (true) {
				if (sellerRight.equals("Y") || sellerRight.equals("N")) {
					break;
				} else {
					System.out.println("잘못 입력하셨습니다");
					break;
				}
			}
			
			Member member=new Member();
			member.setMemberNo(memberNo);
			member.setSellerRight(sellerRight);
			
			int result=mService.sellerRightUpdate(member);
			if(result>0) {
				member.setSellerRight(sellerRight);
				System.out.println("\n[판매 권한을 승인했습니다]\n");
			}else {
				System.out.println("\n[승인 실패]\n");
			}
		} catch (Exception e) {
			System.out.println("<<판매권한 승인 중 예외 발생>>");
			e.printStackTrace();
		}

	}

	/**
	 * 게시글 삭제
	 */
	private static void deleteBoard() {
		System.out.println("\n[회원 게시글 삭제]\n");
		try {
			System.out.print("삭제할 게시글 번호 : ");
			int goodsNo=sc.nextInt();
			
			System.out.println("게시글을 삭제 하시겠습니까?(Y)");
			char ch=sc.next().toUpperCase().charAt(0);
			while (true) {
				if (ch=='Y') {
					int result = bService.deleteBoard(goodsNo);
					
					if(result>0) {
						System.out.println("\n[게시글 삭제 성공]\n");
						break;
					}else {
						System.out.println("\n[게시글 삭제 실패]\n");
						break;
					}
				}else {
					System.out.println("잘못 입력하셨습니다");
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("<<게시글 삭제 중 예외 발생>>");
			e.printStackTrace();
		}
		
	}
	

	/**
	 * 회원 탈퇴
	 */
	private static void secession() {
		System.out.println("\n[회원 탈퇴]\n");
		
		try {
			System.out.println("회원 번호 입력 : ");
			int memberNo=sc.nextInt();
			
			while(true) {
				System.out.print("정말 탈퇴하시겠습니까? (Y/N)");
				char ch=sc.next().toUpperCase().charAt(0);
				
				if(ch=='Y') {
					int result=service.secession(memberNo);
				
					if(result>0) {
						System.out.println("\n[탈퇴되었습니다]\n");

					}else {
						System.out.println("\n[탈퇴 실패]\n");
					}
					break;
				} else if(ch=='N') {
					System.out.println("\n[취소 되었습니다]\n");
					break;
				} else {
					System.out.println("\n[Y 또는 N만 입력해주세요]\n");
				}
			}
			
		} catch (Exception e) {
			System.out.println("\n<<회원 탈퇴 중 예외 발생>>\n");
			e.printStackTrace();
		}
		
	}

	
}
