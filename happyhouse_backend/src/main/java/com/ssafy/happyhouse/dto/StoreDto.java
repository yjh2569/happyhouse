package com.ssafy.happyhouse.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Store (주변 상권 정보)", description = "상권코드, 상권이름, 상권분류, 동코드, 동이름, 경도, 위도, 거리를 가진   Domain Class")
public class StoreDto {
	@ApiModelProperty(value = "상권코드")
	private String scode;
	@ApiModelProperty(value = "상권이름")
	private String sname;
	@ApiModelProperty(value = "상권분류")
	private String type;
	@ApiModelProperty(value = "동코드")
	private String dongcode;
	@ApiModelProperty(value = "동이름")
	private String dongName;
	@ApiModelProperty(value = "경도")
	private String lng;
	@ApiModelProperty(value = "위도")
	private String lat;
	@ApiModelProperty(value = "거리")
	private double dist;
	
	public double getDist() {
		return dist;
	}
	public void setDist(double dist) {
		this.dist = dist;
	}
	public StoreDto() {	}
	public StoreDto(String scode, String sname, String type, String dongcode, String dongName, String lng, String lat) {
		super();
		this.scode = scode;
		this.sname = sname;
		this.type = type;
		this.dongcode = dongcode;
		this.dongName = dongName;
		this.lng = lng;
		this.lat = lat;
	}
	public StoreDto(String sname, String type, String dongName) {
		super();
		this.sname = sname;
		this.type = type;
		this.dongName = dongName;
	}
	public String getScode() {
		return scode;
	}
	public void setScode(String scode) {
		this.scode = scode;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDongcode() {
		return dongcode;
	}
	public void setDongcode(String dongcode) {
		this.dongcode = dongcode;
	}
	public String getDongName() {
		return dongName;
	}
	public void setDongName(String dongName) {
		this.dongName = dongName;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	@Override
	public String toString() {
		return "StoreDto [scode=" + scode + ", sname=" + sname + ", type=" + type + ", dongcode=" + dongcode
				+ ", dongName=" + dongName + ", lng=" + lng + ", lat=" + lat + "]";
	}
	
}
