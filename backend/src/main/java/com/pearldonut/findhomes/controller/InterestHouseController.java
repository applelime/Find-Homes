package com.pearldonut.findhomes.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pearldonut.findhomes.model.HouseInfoDto;
import com.pearldonut.findhomes.model.InterestHouseDto;
import com.pearldonut.findhomes.service.InterestHouseService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("관심 매물 API")
@CrossOrigin("*")
@RestController
@RequestMapping("/interestHouse")
public class InterestHouseController {
	@Autowired
	private InterestHouseService interestService;

	@GetMapping("/office/{userid}")
	@ApiOperation(value = "오피스텔 관심매물 목록 조회", notes = "오피스텔 관심매물의 목록을 리턴")
	public ResponseEntity<?> getInterestOfficeList(@PathVariable String userid){
		List<HouseInfoDto> list = interestService.getOfficeInterestList(userid);	
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	@GetMapping("/apt/{userid}")
	@ApiOperation(value = "아파트 관심매물 목록 조회", notes = "아파트 관심매물의 목록을 리턴")
	public ResponseEntity<?> getInterestAptList(@PathVariable String userid){
		List<Map<String,String>> list = interestService.getAptInterestList(userid);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	@PostMapping("")
	@ApiOperation(value = "관심매물 등록", notes = "아파트/오피스텔 관심매물 등록")
	public ResponseEntity<?> addInterest(@RequestBody InterestHouseDto interestHouse){
		interestService.addInterest(interestHouse);
		return new ResponseEntity<>("success", HttpStatus.OK);
	}
	
	@DeleteMapping("/{num}")
	@ApiOperation(value = "관심매물 삭제", notes = "아파트/오피스텔 관심매물 삭제")
	public ResponseEntity<?> deleteInterest(@PathVariable int num){
		interestService.deleteInterest(num);
		return new ResponseEntity<>("success", HttpStatus.OK);
	}

}
