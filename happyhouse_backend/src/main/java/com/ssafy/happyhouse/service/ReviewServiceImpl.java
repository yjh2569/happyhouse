package com.ssafy.happyhouse.service;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.dto.ListParameterDto;
import com.ssafy.happyhouse.dto.ReviewDto;
import com.ssafy.happyhouse.mapper.ReviewMapper;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private ReviewMapper reviewMapper;
	
	@Override
	public List<ReviewDto> retrieveReview(BigInteger aptcode) {
		List<ReviewDto> list = reviewMapper.selectReview(aptcode);
		for (ReviewDto review : list) {
			if (review.getContent() == null) {
				review.setContent("리뷰 없음");
				continue;
			}
		}
		return list;
	}

//	@Override
//	public PageNavigation makePageNavigation(String pg, String key, String word) throws Exception {
//		PageNavigation pageNavigation = new PageNavigation();
//		int currentPage = pg != null && pg.length() > 0 ? Integer.parseInt(pg) : 1;
//		pageNavigation.setCurrentPage(currentPage);
//		int countPerPage = 10;
//		pageNavigation.setCountPerPage(countPerPage);
//		int naviSize = 10;
//		pageNavigation.setNaviSize(naviSize);
//		ListParameterDto listParameterDto = new ListParameterDto();
//		listParameterDto.setKey(key == null ? "" : key.trim());
//		listParameterDto.setWord(word == null ? "" : word.trim());
//		int totalCount = reviewMapper.getTotalCount(listParameterDto);
//		pageNavigation.setTotalCount(totalCount);
//		int totalPageCount = (totalCount - 1) / countPerPage + 1;
//		pageNavigation.setTotalPageCount(totalPageCount);
//		pageNavigation.setStartRange(currentPage <= naviSize);
//		boolean endRange = (totalPageCount - 1) / naviSize * naviSize < currentPage;
//		pageNavigation.setEndRange(endRange);
//		pageNavigation.makeNavigator();
//		return pageNavigation;
//	}

	@Override
	public int getTotalCount(BigInteger no) {
		return reviewMapper.getTotalCount(no);
	}

	@Override
	public ReviewDto detailReview(int no) {
		return reviewMapper.selectReviewByNo(no);
	}

	@Override
	public boolean writeReview(ReviewDto review) {
		return reviewMapper.insertReview(review) ==1;
	}

	@Override
	public boolean updateReview(ReviewDto review) {
		return reviewMapper.updateReview(review) ==1;
	}

	@Override
	public boolean deleteReview(int no) {
		return reviewMapper.deleteReview(no) ==1;
	}

	@Override
	public double avgReview(BigInteger aptcode) {
		return reviewMapper.avgReview(aptcode);
	}

	@Override
	public int countReview(long aptCode, int rating) {
		ListParameterDto listParameterDto = new ListParameterDto();
		listParameterDto.setAptCode(aptCode);
		listParameterDto.setRating(rating);
		return reviewMapper.countReview(listParameterDto);
	}

}
