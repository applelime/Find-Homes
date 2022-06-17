package com.pearldonut.findhomes.model;

import lombok.Data;

@Data
public class NewsDto {
	private int num;
	private String title;
	private String content;
	private String link;
	private String newDate;
	private String company;
}
