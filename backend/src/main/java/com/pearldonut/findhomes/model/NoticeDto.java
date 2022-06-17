package com.pearldonut.findhomes.model;

import lombok.Data;

@Data
public class NoticeDto {
	private int num;
	private String userId;
	private String writeDate;
	private String lastModifyDate;
	private String title;
	private String content;
	private int count;
}
