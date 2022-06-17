package com.pearldonut.findhomes.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pearldonut.findhomes.mapper.InterestHouseMapper;
import com.pearldonut.findhomes.model.HouseInfoDto;
import com.pearldonut.findhomes.model.InterestHouseDto;

@Service
public class InterestHouseServiceImpl implements InterestHouseService{

	@Autowired
	private InterestHouseMapper interestMapper;
	
	@Override
	public List<Map<String,String>> getAptInterestList(String userid) {
		return interestMapper.getAptInterestList(userid);
	}

	@Override
	public List<HouseInfoDto> getOfficeInterestList(String userid) {
		return interestMapper.getOfficeInterestList(userid);
	}

	@Override
	public void addInterest(InterestHouseDto interestHouse) {
		interestMapper.addInterest(interestHouse);
	}

	@Override
	public void deleteInterest(int num) {
		interestMapper.deleteInterest(num);
	}
}
