package edu.kh.jdbc.board.model.vo;

import java.util.List;

public class Board {
	private int goodsNo;
	private String goodsName;
	private int goodsPrice;
	private String goodsContent;
	private String enrollDate;
	private int readCount;
	private int memberNo;
	private String memberId;
	private String deleteFlag;
	private String soldOut;
	private String dealPlace;
	private int commentCount;
	private List<Comment>commentList;
	
	
	
	public Board() {}

	public int getGoodsNo() {
		return goodsNo;
	}

	public void setGoodsNo(int goodsNo) {
		this.goodsNo = goodsNo;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public int getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(int goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public String getGoodsContent() {
		return goodsContent;
	}

	public void setGoodsContent(String goodsContent) {
		this.goodsContent = goodsContent;
	}

	public String getEnrollDate() {
		return enrollDate;
	}

	public void setEnrollDate(String enrollDate) {
		this.enrollDate = enrollDate;
	}

	public int getReadCount() {
		return readCount;
	}

	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}

	public int getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(int memberNo) {
		this.memberNo = memberNo;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	public List<Comment> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<Comment> commentList) {
		this.commentList = commentList;
	}
	
	
	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getSoldOut() {
		return soldOut;
	}

	public void setSoldOut(String soldOut) {
		this.soldOut = soldOut;
	}
	
	public String getDealPlace() {
		return dealPlace;
	}

	public void setDealPlace(String dealPlace) {
		this.dealPlace = dealPlace;
	}

	
	
}
