package com.ssafy.happyhouse.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Review (리뷰 정보)", description = "리뷰글번호, 리뷰내용, 별점, 리뷰작성일, 리뷰작성자, 아파트코드를 가진   Domain Class")
public class ReviewDto {
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
	@ApiModelProperty(value = "아파트코드")
	private String aptcode;
//	private double avgrate;
	
	public String getAptcode() {
		return aptcode;
	}
	public void setAptcode(String aptcode) {
		this.aptcode = aptcode;
	}
//	public double getAvgRate() {
//		return avgrate;
//	}
//	public void setAvgRate(double avgRate) {
//		this.avgrate = avgRate;
//	}
	public ReviewDto() {}
	public ReviewDto(int no, String content, int rating, String regtime, String userid, String aptcode) {
		super();
		this.no = no;
		this.content = content;
		this.rating = rating;
		this.regtime = regtime;
		this.userid = userid;
		this.aptcode = aptcode;
	}
	
//	public ReviewDto(int no, String content, int rating, String regtime, String userid, String aptcode,
//			double avgrate) {
//		super();
//		this.no = no;
//		this.content = content;
//		this.rating = rating;
//		this.regtime = regtime;
//		this.userid = userid;
//		this.aptcode = aptcode;
//		this.avgrate = avgrate;
//	}
//	public ReviewDto(String content, int rating) {
//		super();
//		this.content = content;
//		this.rating = rating;
//	}
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
	public String getAptCode() {
		return aptcode;
	}
	public void setAptCode(String aptCode) {
		this.aptcode = aptCode;
	}
	@Override
	public String toString() {
		return "ReviewDto [no=" + no + ", content=" + content + ", rating=" + rating + ", regtime=" + regtime
				+ ", userid=" + userid + ", aptcode=" + aptcode + ", " + "]";
	}
	
	
}
