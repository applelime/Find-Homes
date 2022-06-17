package com.pearldonut.findhomes.service;

import java.util.List;

import com.pearldonut.findhomes.model.QnaBoardDto;

public interface QnaBoardService {
	public void createQnaBoard(QnaBoardDto qnaboard)throws Exception;
	public void updateQnaBoard(QnaBoardDto qnaboard)throws Exception;
	public void updateViewCount(String num) throws Exception;
	public void deleteQnaBoard(String num)throws Exception;
	public List<QnaBoardDto> selectAll()throws Exception;
	public QnaBoardDto selectOne(String num)throws Exception;
}
