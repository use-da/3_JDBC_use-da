package edu.kh.jdbc.member.vo;

// VO(Value Object) : 값을 저장하는 용도의 객체
// -JDBC에서는 테이블의 한 행의 조회 결과 또는 삽입, 수정을 위한 데이터를 저장하는 용도의 객체
public class Member {
	
	private int memberNo;
	private String memberId;
	private String memberPw;
	private String memberName;
	private String memberGender;
	private String enrollDate;
	private String secessionFlag;
	
	//기본 생성자
	public Member() {}
	//매개변수 생성자
	//회원가입 DAO
	public Member(String memberId, String memberPw, String memberName, String memberGender) {
		super();
		this.memberId = memberId;
		this.memberPw = memberPw;
		this.memberName = memberName;
		this.memberGender = memberGender;
	}
	//로그인 DAO
	public Member(int memberNo, String memberId, String memberName, String memberGender, String enrollDate) {
		super();
		this.memberNo = memberNo;
		this.memberId = memberId;
		this.memberName = memberName;
		this.memberGender = memberGender;
		this.enrollDate = enrollDate;
	}
	
	//회원목록 조회
	public Member(String memberId, String memberName, String memberGender) {
		super();
		this.memberId = memberId;
		this.memberName = memberName;
		this.memberGender = memberGender;
	}
	//getter/setter
	public int getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(int memberNo) {
		this.memberNo = memberNo;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getMemberPw() {
		return memberPw;
	}
	public void setMemberPw(String memberPw) {
		this.memberPw = memberPw;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getMemberGender() {
		return memberGender;
	}
	public void setMemberGender(String memberGender) {
		this.memberGender = memberGender;
	}
	public String getEnrollDate() {
		return enrollDate;
	}
	public void setEnrollDate(String enrollDate) {
		this.enrollDate = enrollDate;
	}
	public String getSecessionFlag() {
		return secessionFlag;
	}
	public void setSecessionFlag(String secessionFlag) {
		this.secessionFlag = secessionFlag;
	}
	
	//to String
//	@Override
//	public String toString() {
//		return "Member [memberNo=" + memberNo + ", memberId=" + memberId + ", memberPw=" + memberPw + ", memberName="
//				+ memberName + ", memberGender=" + memberGender + ", enrollDate=" + enrollDate + ", secessionFlag="
//				+ secessionFlag + "]";
//	}


	
	
	
	
}
