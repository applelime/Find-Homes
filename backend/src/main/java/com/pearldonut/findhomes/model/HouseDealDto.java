package com.pearldonut.findhomes.model;

import lombok.Data;

@Data
public class HouseDealDto {
	private int dealId;
	private String sigungu;
	private String houseName;
	private int dealYear;
	private int dealMonth;
	private int dealDay;
	private String area;
	private String dealType;
	private int floor;
	private long saleAmount;
	private long jeonseAmount;
	private long monthlyDeposit;
	private long monthlyLease;
}
