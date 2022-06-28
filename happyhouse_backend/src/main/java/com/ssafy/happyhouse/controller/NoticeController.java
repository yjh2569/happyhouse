package com.ssafy.happyhouse.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.dto.NoticeDto;
import com.ssafy.happyhouse.service.NoticeService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/notice")
public class NoticeController {

	private static final Logger logger = LoggerFactory.getLogger(NoticeController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private NoticeService noticeService;

    @ApiOperation(value = "모든 게시글의 정보를 반환한다. key: 검색하고자 하는 column명 , pg: 현재 위치한 페이지 번호, word: 검색 키워드", response = List.class)
	@GetMapping
	public ResponseEntity<List<NoticeDto>> retrieveNotice(
			@RequestParam(value="pg", required = false, defaultValue = "1") String pg,
			@RequestParam(value="key", required = false) String key,
			@RequestParam(value="word", required = false) String word
			) throws Exception {
		logger.debug("retrieveNotice - 호출");
		return new ResponseEntity<List<NoticeDto>>(noticeService.retrieveNotice(pg,key,word), HttpStatus.OK);
	}
    
    @ApiOperation(value = "모든 게시글 전체 개수를 반환한다.key: 검색하고자 하는 column명 , word: 검색 키워드", response = Integer.class)
   	@GetMapping("/count")
   	public ResponseEntity<Integer> getTotalCount(
   			@RequestParam(value="key", required = false) String key,
			@RequestParam(value="word", required = false) String word) throws Exception {
   		logger.debug("getTotalCount - 호출");
   		return new ResponseEntity<Integer>(noticeService.getTotalCount(key,word), HttpStatus.OK);
   	}
    
    @ApiOperation(value = "글번호에 해당하는 게시글의 정보를 반환한다.", response = NoticeDto.class)    
	@GetMapping("{articleno}")
	public ResponseEntity<NoticeDto> detailNotice(@PathVariable int articleno) {
		logger.debug("detailNotice - 호출");
		return new ResponseEntity<NoticeDto>(noticeService.detailNotice(articleno), HttpStatus.OK);
	}

    @ApiOperation(value = "새로운 게시글 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping
	public ResponseEntity<String> writeNotice(@RequestBody NoticeDto notice) {
		logger.debug("writeNotice - 호출");
		if (noticeService.writeNotice(notice)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

    @ApiOperation(value = "글번호에 해당하는 게시글의 정보를 수정한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping("{articleno}")
	public ResponseEntity<String> updateNotice(@RequestBody NoticeDto notice) {
		logger.debug("updateNotice - 호출");
		logger.debug("" + notice);
		
		if (noticeService.updateNotice(notice)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

    @ApiOperation(value = "글번호에 해당하는 게시글의 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("{articleno}")
	public ResponseEntity<String> deleteNotice(@PathVariable int articleno) {
		logger.debug("deleteNotice - 호출");
		if (noticeService.deleteNotice(articleno)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
}