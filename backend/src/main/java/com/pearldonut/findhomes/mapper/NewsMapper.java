package com.pearldonut.findhomes.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.pearldonut.findhomes.model.NewsDto;

@Mapper
public interface NewsMapper {
	public List<NewsDto> selectAll()throws Exception;
}
