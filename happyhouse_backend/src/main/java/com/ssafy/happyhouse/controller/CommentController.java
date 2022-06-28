package com.ssafy.happyhouse.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.dto.CommentDto;
import com.ssafy.happyhouse.service.CommentService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/comment")
public class CommentController {

	private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private CommentService commentService;

    @ApiOperation(value = "QNA 번호에 대한 comment 리스트를 반환한다.", response = List.class)
	@GetMapping("{qnano}")
	public ResponseEntity<List<CommentDto>> selectComment(@PathVariable int qnano) throws Exception {
		logger.debug("selectComment - 호출");
		return new ResponseEntity<List<CommentDto>>(commentService.selectComment(qnano), HttpStatus.OK);
	}
    
    @ApiOperation(value = "comment 번호에 해당하는 comment 정보를 반환한다.", response = CommentDto.class)    
	@GetMapping("/detail/{commentid}")
	public ResponseEntity<CommentDto> selectCommentById(@PathVariable int commentid) throws Exception {
		logger.debug("selectCommentById - 호출");
		return new ResponseEntity<CommentDto>(commentService.selectCommentById(commentid), HttpStatus.OK);
	}

    @ApiOperation(value = "새로운 comment 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping
	public ResponseEntity<String> insertComment(@RequestBody CommentDto commentDto) throws Exception {
		logger.debug("insertComment - 호출");
		if (commentService.insertComment(commentDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

    @ApiOperation(value = "comment 번호에 해당하는 comment 정보를 수정한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping
	public ResponseEntity<String> updateComment(@RequestBody CommentDto commentDto) throws Exception {
		logger.debug("updateComment - 호출");
		logger.debug("" + commentDto);
		
		if (commentService.updateComment(commentDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

    @ApiOperation(value = "comment 번호에 해당하는 comment 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("{commentid}")
	public ResponseEntity<String> deleteComment(@PathVariable int commentid) throws Exception {
		logger.debug("deleteComment - 호출");
		if (commentService.deleteComment(commentid)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
}