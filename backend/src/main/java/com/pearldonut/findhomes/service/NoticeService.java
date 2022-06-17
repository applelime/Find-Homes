package com.pearldonut.findhomes.service;

import java.util.List;

import com.pearldonut.findhomes.model.NoticeDto;

public interface NoticeService {
	public void createNotice(NoticeDto notice)throws Exception;
	public void updateNotice(NoticeDto notice)throws Exception;
	public void deleteNotice(String num)throws Exception;
	public void updateViewCount(String num) throws Exception;
	public List<NoticeDto> selectAll()throws Exception;
	public NoticeDto selectOne(int num)throws Exception;
}
