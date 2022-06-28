package com.ssafy.happyhouse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.dto.ListParameterDto;
import com.ssafy.happyhouse.dto.QnaDto;
import com.ssafy.happyhouse.mapper.QnaMapper;

@Service
public class QnaServiceImpl implements QnaService {
	
	@Autowired
	private QnaMapper qnaMapper;
	
	@Override
	public List<QnaDto> selectQna(String pg, String key, String word) throws Exception {
		ListParameterDto listParameterDto = new ListParameterDto();
		int pgno = pg != null && pg.length() > 0 ? Integer.parseInt(pg) : 1;
		int countPerPage = 10;
		int start = (pgno - 1) * countPerPage;
		listParameterDto.setKey(key == null ? "" : key.trim());
		listParameterDto.setWord(word == null ? "" : word.trim());
		listParameterDto.setStart(start);		
		listParameterDto.setCountPerPage(countPerPage);
		List<QnaDto> list = qnaMapper.selectQna(listParameterDto);
		return list;
	}

	@Override
	public QnaDto selectQnaByNo(int no) throws Exception {
		return qnaMapper.selectQnaByNo(no);
	}

	@Override
	public boolean insertQna(QnaDto qnaDto) throws Exception {
		return qnaMapper.insertQna(qnaDto) == 1;
	}

	@Override
	public boolean updateQna(QnaDto qnaDto) throws Exception {
		return qnaMapper.updateQna(qnaDto) == 1;
	}

	@Override
	public boolean qnaAnswered(int no) throws Exception {
		return qnaMapper.qnaAnswered(no) == 1;
	}

	@Override
	public boolean deleteQna(int no) throws Exception {
		return qnaMapper.deleteQna(no) == 1;
	}

	@Override
	public int getTotalCount(String key, String word) throws Exception {
		ListParameterDto listParameterDto = new ListParameterDto();		
		listParameterDto.setKey(key == null ? "" : key.trim());
		listParameterDto.setWord(word == null ? "" : word.trim());
		return qnaMapper.getTotalCount(listParameterDto);
	}

}
