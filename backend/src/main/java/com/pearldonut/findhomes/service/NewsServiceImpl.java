package com.pearldonut.findhomes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pearldonut.findhomes.mapper.NewsMapper;
import com.pearldonut.findhomes.model.NewsDto;

@Service
public class NewsServiceImpl implements NewsService{
	@Autowired
	private NewsMapper newsMapper;

	@Override
	public List<NewsDto> selectAll() throws Exception {
		return newsMapper.selectAll();
	}
}
