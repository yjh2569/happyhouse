package com.ssafy.happyhouse.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.happyhouse.dto.ListParameterDto;
import com.ssafy.happyhouse.dto.SReviewDto;
@Mapper
public interface SReviewMapper {
	public List<SReviewDto> selectReview(String scode);
	public SReviewDto selectReviewByNo(int no);
	public int insertReview(SReviewDto review);
	public int updateReview(SReviewDto review);
	public int deleteReview(int no);
	int getTotalCount(String scode);
	public double avgReview(String scode);
	public int countReview(ListParameterDto listParameterDto);
}