package com.ssafy.happyhouse.dto;

public class NewsDto {
	private String title;
	private String link;
	private String pubDate;

	
	
	public NewsDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NewsDto(String title, String link, String pubDate) {
		super();
		this.title = title;
		this.link = link;
		this.pubDate = pubDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getPubDate() {
		return pubDate;
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	@Override
	public String toString() {
		return "NewsDto [title=" + title + ", link=" + link + ", pubDate=" + pubDate + "]";
	}

}
