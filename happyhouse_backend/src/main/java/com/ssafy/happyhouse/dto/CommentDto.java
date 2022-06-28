package com.ssafy.happyhouse.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Comment (댓글 정보)", description = "댓글 번호, 댓글 작성자, 댓글 내용, QNA 번호, 댓글 작성시간을 가진   Domain Class")
public class CommentDto {
	
	@ApiModelProperty(value = "댓글 번호")
	private Long commentid;
	@ApiModelProperty(value = "댓글 작성자")
	private String userid;
	@ApiModelProperty(value = "댓글 내용")
	private String content;
	@ApiModelProperty(value = "QNA 번호")
	private Long qnano;
	@ApiModelProperty(value = "댓글 작성시간")
	private String regtime;

	public CommentDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CommentDto(Long commentid, String userid, String content, Long qnano, String regtime) {
		super();
		this.commentid = commentid;
		this.userid = userid;
		this.content = content;
		this.qnano = qnano;
		this.regtime = regtime;
	}

	public Long getCommentid() {
		return commentid;
	}

	public void setCommentid(Long commentid) {
		this.commentid = commentid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getQnano() {
		return qnano;
	}

	public void setQnano(Long qnano) {
		this.qnano = qnano;
	}

	public String getRegtime() {
		return regtime;
	}

	public void setRegtime(String regtime) {
		this.regtime = regtime;
	}

	@Override
	public String toString() {
		return "CommentDto [commentid=" + commentid + ", userid=" + userid + ", content=" + content + ", qnano=" + qnano
				+ ", regtime=" + regtime + "]";
	}

}
