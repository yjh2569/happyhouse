package com.ssafy.happyhouse.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.happyhouse.dto.StoreDto;

public interface StoreService {
	List<StoreDto> getStoreList(String pg, String key, String word, String lat, String lng) throws SQLException;
	int getTotalCount(String key, String word) throws Exception;
}
