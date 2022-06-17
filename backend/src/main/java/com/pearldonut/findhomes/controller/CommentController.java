package com.pearldonut.findhomes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pearldonut.findhomes.model.CommentDto;
import com.pearldonut.findhomes.service.CommentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("게시판 댓글  API")
@CrossOrigin("*")
@RestController
@RequestMapping("/comment")
public class CommentController {
	@Autowired
	private CommentService commentService;

	@GetMapping("/{boardNum}")
	@ApiOperation(value = "댓글 목록 조회", notes = "해당 게시물의  댓글 목록을 리턴")
	public ResponseEntity<List<CommentDto>> list(@PathVariable int boardNum) throws Exception {
		List<CommentDto> list = commentService.selectAll();
		if (list != null) {
			return new ResponseEntity<List<CommentDto>>(list, HttpStatus.OK);
		}else {
			return new ResponseEntity<List<CommentDto>>(HttpStatus.NO_CONTENT);
		}
	}

	@PostMapping("")
	@ApiOperation(value = "댓글 추가", notes = "해당 게시물에 새 댓글 추가")
	public ResponseEntity<CommentDto> insert(@RequestBody CommentDto comment) throws Exception {
		commentService.createComment(comment);
		return new ResponseEntity<CommentDto>(HttpStatus.OK);
	}

	@PutMapping("/{num}")
	@ApiOperation(value = "댓글 수정", notes = "해당 번호의 댓글 내용 수정")
	public ResponseEntity<CommentDto> update(@PathVariable int num, @RequestBody CommentDto comment) throws Exception {
		comment.setNum(num);
		commentService.updateComment(comment);
		return new ResponseEntity<CommentDto>(HttpStatus.OK);
	}

	@DeleteMapping("/{num}")
	@ApiOperation(value = "댓글 삭제", notes = "해당 번호의 댓글 삭제")
	public ResponseEntity<CommentDto> delete(@PathVariable String num) throws Exception {
		commentService.deleteComment(num);
		return new ResponseEntity<CommentDto> (HttpStatus.OK);
	}

}
