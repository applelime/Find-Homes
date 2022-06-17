package com.pearldonut.findhomes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pearldonut.findhomes.mapper.QnaBoardMapper;
import com.pearldonut.findhomes.model.QnaBoardDto;

@Service
public class QnaBoardServiceImpl implements QnaBoardService {
	
	@Autowired
	private QnaBoardMapper qnaBoardMapper;

	@Override
	public void createQnaBoard(QnaBoardDto qnaboard) throws Exception {
		qnaBoardMapper.createQnaBoard(qnaboard);
	}

	@Override
	public void updateQnaBoard(QnaBoardDto qnaboard) throws Exception {
		qnaBoardMapper.updateQnaBoard(qnaboard);
	}
	
	@Override
	public void updateViewCount(String num) throws Exception {
		qnaBoardMapper.updateViewCount(num);
	}

	@Override
	public void deleteQnaBoard(String num) throws Exception {
		qnaBoardMapper.deleteQnaBoard(num);
	}

	@Override
	public List<QnaBoardDto> selectAll() throws Exception {
		return qnaBoardMapper.selectAll();
	}

	@Override
	public QnaBoardDto selectOne(String num) throws Exception {
		return qnaBoardMapper.selectOne(num);
	}
}
