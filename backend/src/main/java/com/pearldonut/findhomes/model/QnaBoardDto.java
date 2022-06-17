package com.pearldonut.findhomes.model;

import lombok.Data;

@Data
public class QnaBoardDto {
	private int num;
	private String userId;
	private String title;
	private String content;
	private int count;
	private String writeDate;
	private String lastModifyDate;
}
