package com.ssafy.happyhouse.service;

import java.math.BigInteger;
import java.util.List;

import com.ssafy.happyhouse.dto.ListParameterDto;
import com.ssafy.happyhouse.dto.ReviewDto;

public interface ReviewService {
	public List<ReviewDto> retrieveReview(BigInteger aptcode);
//	PageNavigation makePageNavigation(String pg, String key, String word) throws Exception;
	int getTotalCount(BigInteger no);
	public ReviewDto detailReview (int no);
	public boolean writeReview(ReviewDto review);
	public boolean updateReview(ReviewDto review);
	public boolean deleteReview(int no);
	public double avgReview(BigInteger aptcode);
	public int countReview(long aptCode, int rating);
}
