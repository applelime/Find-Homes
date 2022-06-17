package com.pearldonut.findhomes.service;

import java.util.List;
import java.util.Map;

import com.pearldonut.findhomes.model.HouseInfoDto;
import com.pearldonut.findhomes.model.InterestHouseDto;

public interface InterestHouseService {
	public List<Map<String,String>> getAptInterestList(String userid);
	public List<HouseInfoDto> getOfficeInterestList(String userid);
	public void addInterest(InterestHouseDto interestHouse);
	public void deleteInterest(int num);
}
