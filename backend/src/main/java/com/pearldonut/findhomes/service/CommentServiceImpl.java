package com.pearldonut.findhomes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pearldonut.findhomes.mapper.CommentMapper;
import com.pearldonut.findhomes.model.CommentDto;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentMapper commentMapper;

	@Override
	public void createComment(CommentDto comment) throws Exception {
		commentMapper.createComment(comment);
	}

	@Override
	public void updateComment(CommentDto comment) throws Exception {
		commentMapper.updateComment(comment);
	}
	

	@Override
	public void deleteComment(String num) throws Exception {
		commentMapper.deleteComment(num);
	}

	@Override
	public List<CommentDto> selectAll() throws Exception {
		return commentMapper.selectAll();
	}
}
