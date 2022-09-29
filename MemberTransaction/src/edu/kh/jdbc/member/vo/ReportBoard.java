package edu.kh.jdbc.member.vo;

public class ReportBoard {
	private int rbNo;
	private int memberNo;
	private String badMember;
	private String reportContent;
	
	public ReportBoard() {}

	public int getRbNo() {
		return rbNo;
	}

	public void setRbNo(int rbNo) {
		this.rbNo = rbNo;
	}

	public int getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(int memberNo) {
		this.memberNo = memberNo;
	}

	public String getBadMember() {
		return badMember;
	}

	public void setBadMember(String badMember) {
		this.badMember = badMember;
	}

	public String getReportContent() {
		return reportContent;
	}

	public void setReportContent(String reportContent) {
		this.reportContent = reportContent;
	}
	
	
	
}
