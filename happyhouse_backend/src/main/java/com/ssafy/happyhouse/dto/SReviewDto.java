package com.ssafy.happyhouse.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "SReview (리뷰 정보)", description = "리뷰글번호, 리뷰내용, 별점, 리뷰작성일, 리뷰작성자, 상권코드 가진   Domain Class")
public class SReviewDto {
	@ApiModelProperty(value = "리뷰글번호")
	private int no;
	@ApiModelProperty(value = "리뷰내용")
	private String content;
	@ApiModelProperty(value = "별점")
	private int rating;
	@ApiModelProperty(value = "리뷰작성일")
	private String regtime;
	@ApiModelProperty(value = "리뷰작성자")
	private String userid;
	@ApiModelProperty(value = "상권코드")
	private String scode;


	public SReviewDto() {}


	public SReviewDto(int no, String content, int rating, String regtime, String userid, String scode) {
		super();
		this.no = no;
		this.content = content;
		this.rating = rating;
		this.regtime = regtime;
		this.userid = userid;
		this.scode = scode;
	}


	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String getRegtime() {
		return regtime;
	}
	public void setRegtime(String regtime) {
		this.regtime = regtime;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
		
	public String getScode() {
		return scode;
	}
	public void setScode(String scode) {
		this.scode = scode;
	}


	@Override
	public String toString() {
		return "SReviewDto [no=" + no + ", content=" + content + ", rating=" + rating + ", regtime=" + regtime
				+ ", userid=" + userid + ", scode=" + scode + "]";
	}

	
	
}
