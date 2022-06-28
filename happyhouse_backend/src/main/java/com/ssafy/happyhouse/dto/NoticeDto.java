package com.ssafy.happyhouse.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Notice (게시글정보)", description = "글번호, 제목, 내용, 작성자아이디, 작성일을 가진   Domain Class")
public class NoticeDto {
	@ApiModelProperty(value = "글번호")
	private int articleno;
	@ApiModelProperty(value = "작성자아이디")
	private String userid;
	@ApiModelProperty(value = "제목")
	private String title;
	@ApiModelProperty(value = "내용")
	private String content;
//	@ApiModelProperty(value = "조회수")
//	private int hit;
	@ApiModelProperty(value = "작성일")
	private String regtime;

	public int getArticleno() {
		return articleno;
	}

	public void setArticleno(int articleno) {
		this.articleno = articleno;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getSubject() {
		return title;
	}

	public void setSubject(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

//	public int getHit() {
//		return hit;
//	}
//
//	public void setHit(int hit) {
//		this.hit = hit;
//	}

	public String getRegtime() {
		return regtime;
	}

	public void setRegtime(String regtime) {
		this.regtime = regtime;
	}

	public NoticeDto(int articleno, String userid, String title, String content, String regtime) {
		super();
		this.articleno = articleno;
		this.userid = userid;
		this.title = title;
		this.content = content;
//		this.hit = hit;
		this.regtime = regtime;
	}

}