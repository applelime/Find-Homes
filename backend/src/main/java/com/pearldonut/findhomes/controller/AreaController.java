package com.pearldonut.findhomes.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pearldonut.findhomes.service.AreaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("행정 구역 API")
@CrossOrigin("*")
@RestController
@RequestMapping("/area")
public class AreaController {
	@Autowired
	AreaService areaService;
	
	@GetMapping("/sido")
	@ApiOperation(value = "시/도 목록", notes = "전체 시/도 목록을 리턴")
	public ResponseEntity<?> sidoList() {
		List<Map<String, String>> list = areaService.sidoList();
		if(list != null && !list.isEmpty()) {
			return new ResponseEntity<List<Map<String, String>>>(list, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}
	
	@GetMapping("/gugun/{sidoCode}")
	@ApiOperation(value = "구/군 목록", notes = "선택한 시/도의 구/군 목록을 리턴")
	public ResponseEntity<?> gugunList(@PathVariable String sidoCode) {
		List<Map<String, String>> list = areaService.gugunList(sidoCode);
		if(list != null && !list.isEmpty()) {
			return new ResponseEntity<List<Map<String, String>>>(list, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}
	
	@GetMapping("/dong/{gugunCode}")
	@ApiOperation(value = "동 목록", notes = "선택한 구/군의 동 목록을 리턴")
	public ResponseEntity<?> dongList(@PathVariable String gugunCode) {
		List<Map<String, String>> list = areaService.dongList(gugunCode);
		if(list != null && !list.isEmpty()) {
			return new ResponseEntity<List<Map<String, String>>>(list, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}
}
