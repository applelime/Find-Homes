package com.pearldonut.findhomes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pearldonut.findhomes.model.NewsDto;
import com.pearldonut.findhomes.service.NewsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("부동산 뉴스 API")
@CrossOrigin("*")
@RestController
@RequestMapping("/news")
public class NewsController {
	@Autowired
	private NewsService newsService;
	
	@GetMapping("")
	@ApiOperation(value = "뉴스 목록 조회", notes = "오늘 부동산 뉴스 정보들 가져오기")
	public ResponseEntity<?> getNewList() throws Exception{
		List<NewsDto> list = newsService.selectAll();
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
}
