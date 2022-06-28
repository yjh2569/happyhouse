package com.ssafy.happyhouse.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "QnA (QnA 정보)", description = "QnA 번호, 제목, 내용, 작성자, 작성시간, 답변여부를 가진   Domain Class")
public class QnaDto {
	
	@ApiModelProperty(value = "QnA 번호")
	private Long no;
	@ApiModelProperty(value = "QnA 제목")
	private String title;
	@ApiModelProperty(value = "QnA 내용")
	private String content;
	@ApiModelProperty(value = "QnA 작성자")
	private String userid;
	@ApiModelProperty(value = "QnA 작성시간")
	private String regtime;
	@ApiModelProperty(value = "QnA 답변여부")
	private int anscheck;

	public QnaDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public QnaDto(Long no, String title, String content, String userid, String regtime, int anscheck) {
		super();
		this.no = no;
		this.title = title;
		this.content = content;
		this.userid = userid;
		this.regtime = regtime;
		this.anscheck = anscheck;
	}

	public Long getNo() {
		return no;
	}

	public void setNo(Long no) {
		this.no = no;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getRegtime() {
		return regtime;
	}

	public void setRegtime(String regtime) {
		this.regtime = regtime;
	}

	public int getAnscheck() {
		return anscheck;
	}

	public void setAnscheck(int anscheck) {
		this.anscheck = anscheck;
	}

	@Override
	public String toString() {
		return "QnaDto [no=" + no + ", title=" + title + ", content=" + content + ", userid=" + userid + ", regtime="
				+ regtime + ", anscheck=" + anscheck + "]";
	}

}
