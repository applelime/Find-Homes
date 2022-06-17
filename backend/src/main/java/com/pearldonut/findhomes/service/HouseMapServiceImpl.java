package com.pearldonut.findhomes.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pearldonut.findhomes.mapper.HouseMapMapper;
import com.pearldonut.findhomes.model.HouseDealDto;
import com.pearldonut.findhomes.model.HouseInfoDto;

@Service
public class HouseMapServiceImpl implements HouseMapService {

	@Autowired
	private HouseMapMapper houseMapMapper;
	
	@Override
	public List<HouseInfoDto> search(String condition, String val) throws Exception {
		return houseMapMapper.search(condition, val);
	}

	@Override
	public List<HouseInfoDto> getHouseList(String dongCode, String type, String year) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("dongCode", dongCode);
		map.put("type", type);
		map.put("year", year);
		return houseMapMapper.getHouseList(map);
	}

	@Override
	public List<HouseDealDto> getDealList(String sigungu, String houseName) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("sigungu", sigungu);
		map.put("houseName", houseName);
		return houseMapMapper.getDealList(map);
	}
}
