package com.ssafy.happyhouse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.dto.ListParameterDto;
import com.ssafy.happyhouse.dto.SReviewDto;
import com.ssafy.happyhouse.mapper.SReviewMapper;

@Service
public class SReviewServiceImpl implements SReviewService {

	
	@Autowired
	private SReviewMapper sreviewMapper;
	
	@Override
	public List<SReviewDto> retrieveReview(String scode) {
		List<SReviewDto> list = sreviewMapper.selectReview(scode);
		for (SReviewDto review : list) {
			if (review.getContent() == null) {
				review.setContent("리뷰 없음");
				continue;
			}
		}
		return list;
	}

	@Override
	public int getTotalCount(String scode) {
		return sreviewMapper.getTotalCount(scode);
	}

	@Override
	public SReviewDto detailReview(int no) {
		return sreviewMapper.selectReviewByNo(no);
	}

	@Override
	public boolean writeReview(SReviewDto review) {
		return sreviewMapper.insertReview(review)==1;
	}

	@Override
	public boolean updateReview(SReviewDto review) {
		return sreviewMapper.updateReview(review)==1;
	}

	@Override
	public boolean deleteReview(int no) {
		return sreviewMapper.deleteReview(no)==1;
	}

	@Override
	public double avgReview(String scode) {
		return sreviewMapper.avgReview(scode);
	}

	@Override
	public int countReview(String scode, int rating) {
		ListParameterDto listParameterDto = new ListParameterDto();
		listParameterDto.setScode(scode);
		listParameterDto.setRating(rating);
		return sreviewMapper.countReview(listParameterDto);
	}

}
