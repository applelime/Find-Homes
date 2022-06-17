package com.pearldonut.findhomes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pearldonut.findhomes.model.NoticeDto;
import com.pearldonut.findhomes.service.NoticeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("공지사항 API")
@CrossOrigin("*")
@RestController
@RequestMapping("/notice")
public class NoticeController {
	private final String SUCCESS ="success";
	
	@Autowired
	private NoticeService noticeService;

	@GetMapping("")
	@ApiOperation(value = "공지사항 목록 조회", notes = "전체 공지사항 목록 리턴")
	public ResponseEntity<List<NoticeDto>> list() throws Exception {
		List<NoticeDto> list = noticeService.selectAll();
		if (list != null) {
			return new ResponseEntity<List<NoticeDto>>(list, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<NoticeDto>>(HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping("/{num}")
	@ApiOperation(value = "공지사항 조회", notes = "선택한 공지사항 하나를 리턴")
	public ResponseEntity<NoticeDto> selectOne(@PathVariable int num) throws Exception {
		NoticeDto notice = noticeService.selectOne(num);
		if (notice != null) {
			return new ResponseEntity<NoticeDto>(notice, HttpStatus.OK);
		} else {
			return new ResponseEntity<NoticeDto>(HttpStatus.NO_CONTENT);
		}
	}

	@PostMapping("")
	@ApiOperation(value = "공지사항 등록", notes = "공지사항 등록")
	public ResponseEntity<?> insert(@RequestBody NoticeDto notice) throws Exception {
		noticeService.createNotice(notice);
		return new ResponseEntity<>(SUCCESS,HttpStatus.OK);
	}

	@PutMapping("/{num}")
	@ApiOperation(value = "공지사항 수정", notes = "공지사항 수정")
	public ResponseEntity<?>  update(@PathVariable String num, @RequestBody NoticeDto notice) throws Exception {
		noticeService.updateNotice(notice);
		return new ResponseEntity<>(SUCCESS,HttpStatus.OK);
	}

	@PatchMapping("/{num}")
	@ApiOperation(value = "공지사항 조회수 증가", notes = "공지사항의 조회수를 1 증가")
	public ResponseEntity<?>  updateViewCount(@PathVariable String num) throws Exception {
		noticeService.updateViewCount(num);
		return new ResponseEntity<>(SUCCESS,HttpStatus.OK);
	}

	@DeleteMapping("/{num}")
	@ApiOperation(value = "공지사항 삭제", notes = "공지사항 삭제")
	public ResponseEntity<?> delete(@PathVariable String num) throws Exception {
		noticeService.deleteNotice(num);
		return new ResponseEntity<>(SUCCESS,HttpStatus.OK);
	}

}
