package com.pearldonut.findhomes.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.pearldonut.findhomes.model.HouseDealDto;
import com.pearldonut.findhomes.model.HouseInfoDto;

@Mapper
public interface HouseMapMapper {
	List<HouseInfoDto> search(String condition, String val) throws SQLException;
	
	List<HouseInfoDto> getHouseList(Map<String, String> map) throws SQLException;
	List<HouseDealDto> getDealList(Map<String, String> map) throws SQLException;
}
