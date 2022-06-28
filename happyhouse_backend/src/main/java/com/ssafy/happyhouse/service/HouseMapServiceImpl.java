package com.ssafy.happyhouse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.dto.HouseInfoDto;
import com.ssafy.happyhouse.dto.ListParameterDto;
import com.ssafy.happyhouse.dto.SidoGugunCodeDto;
import com.ssafy.happyhouse.mapper.HouseMapMapper;


@Service
public class HouseMapServiceImpl implements HouseMapService {
	
	@Autowired
	private HouseMapMapper houseMapMapper;

	@Override
	public List<SidoGugunCodeDto> getSido() throws Exception {
		return houseMapMapper.getSido();
	}

	@Override
	public List<SidoGugunCodeDto> getGugunInSido(String sido) throws Exception {
		return houseMapMapper.getGugunInSido(sido);
	}

	@Override
	public List<HouseInfoDto> getDongInGugun(String gugun) throws Exception {
		return houseMapMapper.getDongInGugun(gugun);
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
//		int totalCount = houseMapMapper.getTotalCount(listParameterDto);
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
	public List<HouseInfoDto> getSearchList(String pg, String key, String word, String lat, String lng) {
		ListParameterDto listParameterDto = new ListParameterDto();
		int pgno = pg != null && pg.length() > 0 ? Integer.parseInt(pg) : 1;
		int countPerPage = 5;
		int start = (pgno - 1) * countPerPage;
		listParameterDto.setKey(key == null ? "" : key.trim());
		listParameterDto.setWord(word == null ? "" : word.trim());
		listParameterDto.setStart(start);		
		listParameterDto.setCountPerPage(countPerPage);
		listParameterDto.setLat(Double.parseDouble(lat));
		listParameterDto.setLng(Double.parseDouble(lng));
		List<HouseInfoDto> list = houseMapMapper.getSearchList(listParameterDto);
		for (HouseInfoDto house : list) {
			if (house.getRecentPrice() == null) {
				house.setRecentPrice("거래 내역 없음");
				continue;
			}
			long price = Long.parseLong(house.getRecentPrice().replace(",", "").trim());
			house.setRecentPrice(price+"0000");
		}
		return list;
	}
	
	@Override
	public List<HouseInfoDto> getAptRank(String key, String word, String rankKey, String lat, String lng) throws Exception {
		ListParameterDto listParameterDto = new ListParameterDto();
		listParameterDto.setKey(key == null ? "" : key.trim());
		listParameterDto.setWord(word == null ? "" : word.trim());
		listParameterDto.setRankKey(rankKey == null ? "" : rankKey.trim());
		listParameterDto.setLat(Double.parseDouble(lat));
		listParameterDto.setLng(Double.parseDouble(lng));
		List<HouseInfoDto> list = houseMapMapper.getAptRank(listParameterDto);
		for (HouseInfoDto house : list) {
			if (house.getRecentPrice() == null) {
				house.setRecentPrice("거래 내역 없음");
				continue;
			}
			long price = Long.parseLong(house.getRecentPrice().replace(",", "").trim());
			StringBuilder sb = new StringBuilder();
			if (price/10000 > 0) {
				sb.append(price/10000).append("억 ");
			}
			if (price%10000 > 0) {
				sb.append(price%10000).append("만 ");
			}
			sb.append("원");
			house.setRecentPrice(sb.toString());
		}
		return list;
	}
	@Override
	public int getTotalCount(String key, String word) throws Exception {
		ListParameterDto listParameterDto = new ListParameterDto();		
		listParameterDto.setKey(key == null ? "" : key.trim());
		listParameterDto.setWord(word == null ? "" : word.trim());
//		listParameterDto.setLat(Double.parseDouble(lat));
//		listParameterDto.setLng(Double.parseDouble(lng));
		return houseMapMapper.getTotalCount(listParameterDto);
	}
}
