package com.pearldonut.findhomes.service;

import java.util.List;

import com.pearldonut.findhomes.model.NewsDto;

public interface NewsService {
	public List<NewsDto> selectAll()throws Exception;
}
