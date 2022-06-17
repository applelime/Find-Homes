package com.pearldonut.findhomes.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pearldonut.findhomes.mapper.AreaMapper;

@Service
public class AreaServiceImpl implements AreaService {
	
	@Autowired
	private AreaMapper areaMapper;

	@Override
	public List<Map<String, String>> sidoList() {
		return areaMapper.sidoList();
	}

	@Override
	public List<Map<String, String>> gugunList(String sidoCode) {
		return areaMapper.gugunList(sidoCode);
	}

	@Override
	public List<Map<String, String>> dongList(String gugunCode) {
		return areaMapper.dongList(gugunCode);
	}
}
