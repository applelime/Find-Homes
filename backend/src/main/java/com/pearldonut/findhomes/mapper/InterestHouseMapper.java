package com.pearldonut.findhomes.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.pearldonut.findhomes.model.HouseInfoDto;
import com.pearldonut.findhomes.model.InterestHouseDto;

@Mapper
public interface InterestHouseMapper {
	public List<Map<String,String>> getAptInterestList(String userid);
	public List<HouseInfoDto> getOfficeInterestList(String userid);
	public void addInterest(InterestHouseDto interestHouse);
	public void deleteInterest(int num);
}
