package com.pearldonut.findhomes.service;

import java.util.List;

import com.pearldonut.findhomes.model.CommentDto;

public interface CommentService {
	public void createComment(CommentDto comment)throws Exception;
	public void updateComment(CommentDto comment)throws Exception;
	public void deleteComment(String num)throws Exception;
	public List<CommentDto> selectAll()throws Exception;
}
