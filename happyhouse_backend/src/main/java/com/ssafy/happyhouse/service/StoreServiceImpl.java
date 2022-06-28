package com.ssafy.happyhouse.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.dto.ListParameterDto;
import com.ssafy.happyhouse.dto.StoreDto;
import com.ssafy.happyhouse.mapper.StoreMapper;

@Service
public class StoreServiceImpl implements StoreService {

	@Autowired
	private StoreMapper storeMapper;
	
	@Override
	public List<StoreDto> getStoreList(String pg, String key, String word, String lat, String lng) throws SQLException {		
		ListParameterDto listParameterDto = new ListParameterDto();
		int pgno = pg != null && pg.length() > 0 ? Integer.parseInt(pg) : 1;
		int countPerPage = 10;
		int start = (pgno - 1) * countPerPage;
		listParameterDto.setKey(key == null ? "" : key.trim());
		listParameterDto.setWord(word == null ? "" : word.trim());
		listParameterDto.setStart(start);		
		listParameterDto.setCountPerPage(countPerPage);
		listParameterDto.setLat(Double.parseDouble(lat));
		listParameterDto.setLng(Double.parseDouble(lng));  
		return storeMapper.getStoreList(listParameterDto);
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
//		int totalCount = storeMapper.getTotalCount(listParameterDto);
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
	public int getTotalCount(String key, String word) throws Exception {
		ListParameterDto listParameterDto = new ListParameterDto();		
		listParameterDto.setKey(key == null ? "" : key.trim());
		listParameterDto.setWord(word == null ? "" : word.trim());
//		listParameterDto.setLat(Double.parseDouble(lat));
//		listParameterDto.setLng(Double.parseDouble(lng));
		return storeMapper.getTotalCount(listParameterDto);
	}

}
