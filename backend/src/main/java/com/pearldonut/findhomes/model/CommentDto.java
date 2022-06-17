package com.pearldonut.findhomes.model;

import lombok.Data;

@Data
public class CommentDto {
	private int num;
	private int boardNum;
	private int count;
	private String writeDate;
	private String lastModifyDate;
	private String content;
	private int likeCount;
	private String userId;
}
