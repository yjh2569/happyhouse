package com.ssafy.happyhouse.service;

import java.util.List;

import com.ssafy.happyhouse.dto.QnaDto;

public interface QnaService {
	List<QnaDto> selectQna(String pg, String key, String word) throws Exception;
	QnaDto selectQnaByNo(int no) throws Exception;
	boolean insertQna(QnaDto qnaDto) throws Exception;
	boolean updateQna(QnaDto qnaDto) throws Exception;
	boolean qnaAnswered(int no) throws Exception;
	boolean deleteQna(int no) throws Exception;
	int getTotalCount(String key, String word) throws Exception;
}
