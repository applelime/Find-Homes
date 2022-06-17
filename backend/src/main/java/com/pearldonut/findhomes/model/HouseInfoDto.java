package com.pearldonut.findhomes.model;

import lombok.Data;

@Data
public class HouseInfoDto {
	private int houseId;
	private String sigungu;
	private String roadName;
	private String dongCode;
	private String houseName;
	private String houseType;
	private int buildYear;
	private String lat;
	private String lng;
	private String img;
}
