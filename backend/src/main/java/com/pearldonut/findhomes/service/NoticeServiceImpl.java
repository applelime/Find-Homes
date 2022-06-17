package com.pearldonut.findhomes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pearldonut.findhomes.mapper.NoticeMapper;
import com.pearldonut.findhomes.model.NoticeDto;

@Service
public class NoticeServiceImpl implements NoticeService {
	
	@Autowired
	private NoticeMapper noticeMapper;

	@Override
	public void createNotice(NoticeDto notice) throws Exception{
		noticeMapper.createNotice(notice);
	}

	@Override
	public void updateNotice(NoticeDto notice) throws Exception{
		noticeMapper.updateNotice(notice);
	}

	@Override
	public void deleteNotice(String num) throws Exception{
		noticeMapper.deleteNotice(num);
	}

	@Override
	public List<NoticeDto> selectAll() throws Exception{
		return noticeMapper.selectAll();
	}

	@Override
	public NoticeDto selectOne(int num)throws Exception{
		return noticeMapper.selectOne(num);
	}

	@Override
	public void updateViewCount(String num) throws Exception {
		noticeMapper.updateViewCount(num);
	}
}
