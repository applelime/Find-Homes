package com.pearldonut.findhomes.service;

import java.util.List;

import com.pearldonut.findhomes.model.HouseDealDto;
import com.pearldonut.findhomes.model.HouseInfoDto;

public interface HouseMapService {
	List<HouseInfoDto> search(String condition, String value) throws Exception;

	List<HouseInfoDto> getHouseList(String dongCode, String type, String year) throws Exception; // 법정동 코드에 있는 주택 정보를 조회
	List<HouseDealDto> getDealList(String sigungu, String houseName) throws Exception;  // 선택한 집의 상세 거래내역 조회
}
