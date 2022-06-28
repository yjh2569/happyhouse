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
import com.ssafy.happyhouse.dto.SReviewDto;
import com.ssafy.happyhouse.service.NoticeService;
import com.ssafy.happyhouse.service.SReviewService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/sreview")
public class SReviewController {

	private static final Logger logger = LoggerFactory.getLogger(SReviewController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private SReviewService sreviewService;

    @ApiOperation(value = "해당 상권의 모든 리뷰의 정보를 반환한다.", response = List.class)
	@GetMapping("/list/{scode}")
	public ResponseEntity<List<SReviewDto>> retrieveReview(@PathVariable String scode) throws Exception {
		logger.debug("retrieveReview - 호출");
		return new ResponseEntity<List<SReviewDto>>(sreviewService.retrieveReview(scode), HttpStatus.OK);
	}
    
    @ApiOperation(value = "해당 상권의 평균별점의 정보를 반환한다.", response = List.class)
   	@GetMapping("/avg/{scode}")
   	public ResponseEntity<Double> avgReview(@PathVariable String scode) throws Exception {
   		logger.debug("avgReview - 호출");
   		return new ResponseEntity<Double>(sreviewService.avgReview(scode), HttpStatus.OK);
   	}
    @ApiOperation(value = "해당 상권의 각 평점의 개수 정보를 반환한다.", response = List.class)
   	@GetMapping("/rcount")
   	public ResponseEntity<Integer> countReview(@RequestParam("scode") String scode,@RequestParam("rating") int rating) throws Exception {
   		logger.debug("countReview - 호출");
   		return new ResponseEntity<Integer>(sreviewService.countReview(scode, rating), HttpStatus.OK);
   	}   
    
    @ApiOperation(value = "모든 상권별 리뷰 전체 개수를 반환한다", response = List.class)
   	@GetMapping("/count/{scode}")
   	public ResponseEntity<Integer> getTotalCount(@PathVariable String scode) throws Exception {
   		logger.debug("getTotalCount - 호출");
   		return new ResponseEntity<Integer>(sreviewService.getTotalCount(scode), HttpStatus.OK);
   	}
    
    @ApiOperation(value = "글번호에 해당하는 게시글의 정보를 반환한다.", response = NoticeDto.class)    
	@GetMapping("{no}")
	public ResponseEntity<SReviewDto> detailReview(@PathVariable int no) {
		logger.debug("detailReview - 호출");
		return new ResponseEntity<SReviewDto>(sreviewService.detailReview(no), HttpStatus.OK);
	}

    @ApiOperation(value = "새로운 리뷰 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping
	public ResponseEntity<String> writeReview(@RequestBody SReviewDto review) {
		logger.debug("writeReview - 호출");
		if (sreviewService.writeReview(review)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

    @ApiOperation(value = "글번호에 해당하는 리뷰의 정보를 수정한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping("{no}")
	public ResponseEntity<String> updateReview(@RequestBody SReviewDto review) {
		logger.debug("updateReview - 호출");
		logger.debug("" + review);		
		if (sreviewService.updateReview(review)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

    @ApiOperation(value = "글번호에 해당하는 리뷰의 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("{no}")
	public ResponseEntity<String> deleteReview(@PathVariable int no) {
		logger.debug("deleteReview - 호출");
		if (sreviewService.deleteReview(no)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
}