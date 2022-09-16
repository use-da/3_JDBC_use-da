package edu.kh.jdbc.model.vo;

public class TestVO {
	private int testNo;
	private String testTitle;
	private String testContent;
	
	/*
	 	VO
	 	컬럼 변수선언
	 	기본생성자, 매개변수생성자, getter/setter, toString() 오버라이딩
	 	
	 	Run - Service
	 	메인 메서드
	 	Service호출
	 	
	 	Service - DAO
	 	DAO호출
	 	JDBCTemplate사용 import static edu.kh.jdbc.common.JDBCTemplate.*;시 JDBCTemplate. 생략가능
	 	
	 	DAO
	 	insert메서드
	 	
	 	실행 -> TestService객체 DAO필드->TestDAO객체 prop(SQL) -> TestVO객체(1,제목1,내용1) 
	 	-> Service insert메서드 커넥션생성->DAO 순서대로 시행
	 */
	
	
	//기본생성자
	public TestVO() {}
	
	//매개변수생성자
	public TestVO(int testNo, String testTitle, String testContent) {
		super();
		this.testNo = testNo;
		this.testTitle = testTitle;
		this.testContent = testContent;
	}
	
	//getter/setter
	public int getTestNo() {
		return testNo;
	}
	public void setTestNo(int testNo) {
		this.testNo = testNo;
	}
	public String getTestTitle() {
		return testTitle;
	}
	public void setTestTitle(String testTitle) {
		this.testTitle = testTitle;
	}
	public String getTestContent() {
		return testContent;
	}
	public void setTestContent(String testContent) {
		this.testContent = testContent;
	}
	
	//toString() 오버라이딩
	@Override
	public String toString() {
		return "TestVO [testNo=" + testNo + ", testTitle=" + testTitle + ", testContent=" + testContent + "]";
	}
	
	
}
