package com.ssafy.happyhouse.service;

import java.util.List;

import com.ssafy.happyhouse.dto.SReviewDto;

public interface SReviewService {
	public List<SReviewDto> retrieveReview(String scode);
//	PageNavigation makePageNavigation(String pg, String key, String word) throws Exception;
	int getTotalCount(String scode);
	public SReviewDto detailReview (int no);
	public boolean writeReview(SReviewDto review);
	public boolean updateReview(SReviewDto review);
	public boolean deleteReview(int no);
	public double avgReview(String scode);
	public int countReview(String scode, int rating);
}
