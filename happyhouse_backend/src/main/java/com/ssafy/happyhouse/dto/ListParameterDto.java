package com.ssafy.happyhouse.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "ParamterDto (인자 정보)", description = "검색 키, 검색 단어, 검색 법정동 코드, 시작 페이지 번호, 페이지 당 원소 개수, 현재 위치의 위경도, 아파트 코드를 가진   Domain Class")
public class ListParameterDto {
	@ApiModelProperty(value = "검색 키")
	private String key;
	@ApiModelProperty(value = "검색 단어")
	private String word;
	@ApiModelProperty(value = "순위 키")
	private String rankKey;
	@ApiModelProperty(value = "검색 법정동 코드")
	private String dongCode;
	@ApiModelProperty(value = "시작 페이지 번호")
	private int start;
	@ApiModelProperty(value = "페이지 당 원소 개수")
	private int countPerPage;
	@ApiModelProperty(value = "현재 위치의 위도")
	private double lat;
	@ApiModelProperty(value = "현재 위치의 경도")
	private double lng;
	@ApiModelProperty(value = "아파트 코드")
	private long aptCode;
	@ApiModelProperty(value = "평점")
	private int rating;
	@ApiModelProperty(value = "상점 코드")
	private String scode;
	
	
	public String getScode() {
		return scode;
	}
	public void setScode(String scode) {
		this.scode = scode;
	}
	public String getRankKey() {
		return rankKey;
	}
	public void setRankKey(String rankKey) {
		this.rankKey = rankKey;
	}
	public String getDongCode() {
		return dongCode;
	}
	public void setDongCode(String dongCode) {
		this.dongCode = dongCode;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	public ListParameterDto() {
		super();
	}
	public ListParameterDto(String key, String word) {
		super();
		this.key = key;
		this.word = word;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	@Override
	public String toString() {
		return "ListParameterDto [key=" + key + ", word=" + word + "]";
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getCountPerPage() {
		return countPerPage;
	}
	public void setCountPerPage(int countPerPage) {
		this.countPerPage = countPerPage;
	}
	public long getAptCode() {
		return aptCode;
	}
	public void setAptCode(long aptCode) {
		this.aptCode = aptCode;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	
}	
