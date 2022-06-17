package com.pearldonut.findhomes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pearldonut.findhomes.model.HouseDealDto;
import com.pearldonut.findhomes.model.HouseInfoDto;
import com.pearldonut.findhomes.service.HouseMapService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("주택 데이터 조회 API")
@CrossOrigin("*")
@RestController
@RequestMapping("/house")
public class HouseMapController {
	@Autowired
	private HouseMapService houseMapService;

	@GetMapping("/dong/{dongCode}")
	@ApiOperation(value = "해당 동의 주택 목록 조회", notes = "동코드로 해당 동에 위치한 주택 데이터 리턴")
	public ResponseEntity<List<HouseInfoDto>> getHouseList(@PathVariable String dongCode,
			@RequestParam("type") String type, @RequestParam("year") String year) throws Exception {
		return new ResponseEntity<List<HouseInfoDto>>(houseMapService.getHouseList(dongCode, type, year), HttpStatus.OK);
	}
	
	@GetMapping("/deal")
	@ApiOperation(value = "해당 주택의 거래 데이터 조회", notes = "선택한 주택의 최근 거래 데이터 리턴")
	public ResponseEntity<List<HouseDealDto>> getDealList(@RequestParam("sigungu") String sigungu, @RequestParam("house-name") String houseName) throws Exception {
		return new ResponseEntity<List<HouseDealDto>>(houseMapService.getDealList(sigungu, houseName), HttpStatus.OK);
	}
}
