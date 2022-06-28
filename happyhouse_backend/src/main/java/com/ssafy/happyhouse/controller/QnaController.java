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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.dto.QnaDto;
import com.ssafy.happyhouse.service.QnaService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/qna")
public class QnaController {

	private static final Logger logger = LoggerFactory.getLogger(QnaController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private QnaService qnaService;

    @ApiOperation(value = "QNA 리스트를 반환한다. key: 검색하고자 하는 column명 , pg: 현재 위치한 페이지 번호, word: 검색 키워드", response = List.class)
	@GetMapping
	public ResponseEntity<List<QnaDto>> selectQna(
			@RequestParam(value="pg", required = false, defaultValue = "1") String pg,
			@RequestParam(value="key", required = false) String key,
			@RequestParam(value="word", required = false) String word
			) throws Exception {
		logger.debug("selectQna - 호출");
		return new ResponseEntity<List<QnaDto>>(qnaService.selectQna(pg,key,word), HttpStatus.OK);
	}
    
    @ApiOperation(value = "QNA 전체 개수를 반환한다. key: 검색하고자 하는 column명 , word: 검색 키워드", response = Integer.class)
   	@GetMapping("/count")
   	public ResponseEntity<Integer> getTotalCount(
   			@RequestParam(value="key", required = false) String key,
			@RequestParam(value="word", required = false) String word) throws Exception {
   		logger.debug("getTotalCount - 호출");
   		return new ResponseEntity<Integer>(qnaService.getTotalCount(key,word), HttpStatus.OK);
   	}
    
    @ApiOperation(value = "QNA 번호에 해당하는 QNA 정보를 반환한다.", response = QnaDto.class)    
	@GetMapping("{no}")
	public ResponseEntity<QnaDto> selectQnaByNo(@PathVariable int no) throws Exception {
		logger.debug("selectQnaByNo - 호출");
		return new ResponseEntity<QnaDto>(qnaService.selectQnaByNo(no), HttpStatus.OK);
	}

    @ApiOperation(value = "새로운 QNA 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping
	public ResponseEntity<String> insertQna(@RequestBody QnaDto notice) throws Exception {
		logger.debug("insertQna - 호출");
		if (qnaService.insertQna(notice)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

    @ApiOperation(value = "QNA 번호에 해당하는 QNA 정보를 수정한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping
	public ResponseEntity<String> updateQna(@RequestBody QnaDto notice) throws Exception {
		logger.debug("updateQna - 호출");
		logger.debug("" + notice);
		
		if (qnaService.updateQna(notice)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
    
    @ApiOperation(value = "QNA 번호에 해당하는 QNA를 답변 완료 상태로 바꾼다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping("{no}")
	public ResponseEntity<String> qnaAnswered(@PathVariable int no) throws Exception {
		logger.debug("updateQna - 호출");		
		if (qnaService.qnaAnswered(no)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

    @ApiOperation(value = "QNA 번호에 해당하는 QNA 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("{no}")
	public ResponseEntity<String> deleteQna(@PathVariable int no) throws Exception {
		logger.debug("deleteQna - 호출");
		if (qnaService.deleteQna(no)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
}