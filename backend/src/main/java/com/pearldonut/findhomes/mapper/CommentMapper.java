package com.pearldonut.findhomes.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.pearldonut.findhomes.model.CommentDto;

@Mapper
public interface CommentMapper {
	public void createComment(CommentDto comment)throws Exception;
	public void updateComment(CommentDto comment)throws Exception;
	public void deleteComment(String num)throws Exception;
	public List<CommentDto> selectAll()throws Exception;
}
