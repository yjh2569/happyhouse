package com.ssafy.happyhouse.mapper;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.happyhouse.dto.ListParameterDto;
import com.ssafy.happyhouse.dto.NoticeDto;
import com.ssafy.happyhouse.dto.ReviewDto;
@Mapper
public interface ReviewMapper {
	public List<ReviewDto> selectReview(BigInteger aptcode);
	public ReviewDto selectReviewByNo(int no);
	public int insertReview(ReviewDto review);
	public int updateReview(ReviewDto review);
	public int deleteReview(int no);
	int getTotalCount(BigInteger no);
	public double avgReview(BigInteger aptcode);
	public int countReview(ListParameterDto listParameterDto);
}