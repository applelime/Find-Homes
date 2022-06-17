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

import com.pearldonut.findhomes.model.QnaBoardDto;
import com.pearldonut.findhomes.service.QnaBoardService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("Q&A 게시판 API")
@CrossOrigin("*")
@RestController
@RequestMapping("/qnaboard")
public class QnaBoardController {
	@Autowired
	private QnaBoardService qnaBoardService;

	@GetMapping("")
	@ApiOperation(value = "게시물 목록 조회", notes = "전체 게시물 목록 리턴")
	public ResponseEntity<List<QnaBoardDto>> list() throws Exception {
		List<QnaBoardDto> list = qnaBoardService.selectAll();
		if (list != null) {
			return new ResponseEntity<List<QnaBoardDto>>(list, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<QnaBoardDto>>(HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping("/{num}")
	@ApiOperation(value = "게시물 조회", notes = "선택한 게시물 하나를 리턴")
	public ResponseEntity<QnaBoardDto> selectOne(@PathVariable String num) throws Exception {
		QnaBoardDto article = qnaBoardService.selectOne(num);
		if (article != null) {
			return new ResponseEntity<QnaBoardDto>(article, HttpStatus.OK);
		} else {
			return new ResponseEntity<QnaBoardDto>(HttpStatus.NO_CONTENT);
		}
	}

	@PostMapping("")
	@ApiOperation(value = "게시물 등록", notes = "게시물 등록")
	public ResponseEntity<QnaBoardDto> insert(@RequestBody QnaBoardDto qnaboard) throws Exception {
		qnaBoardService.createQnaBoard(qnaboard);
		return new ResponseEntity<QnaBoardDto>(HttpStatus.OK);
	}

	@PutMapping("/{num}")
	@ApiOperation(value = "게시물 수정", notes = "게시물 수정")
	public ResponseEntity<QnaBoardDto> update(@PathVariable String num, @RequestBody QnaBoardDto qnaboard) throws Exception {
		qnaBoardService.updateQnaBoard(qnaboard);
		return new ResponseEntity<QnaBoardDto>(HttpStatus.OK);
	}
	
	@PatchMapping("/{num}")
	@ApiOperation(value = "게시물 조회수 증가", notes = "게시물의 조회수를 1 증가")
	public ResponseEntity<QnaBoardDto> updateViewCount(@PathVariable String num) throws Exception {
		qnaBoardService.updateViewCount(num);
		return new ResponseEntity<QnaBoardDto>(HttpStatus.OK);
	}

	@DeleteMapping("/{num}")
	@ApiOperation(value = "게시물 삭제", notes = "게시물 삭제")
	public ResponseEntity<QnaBoardDto> delete(@PathVariable String num) throws Exception {
		qnaBoardService.deleteQnaBoard(num);
		return new ResponseEntity<QnaBoardDto>(HttpStatus.OK);
	}

}
