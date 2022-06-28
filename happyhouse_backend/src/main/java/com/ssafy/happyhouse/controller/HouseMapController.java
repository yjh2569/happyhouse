package com.ssafy.happyhouse.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.dto.HouseInfoDto;
import com.ssafy.happyhouse.dto.SidoGugunCodeDto;
import com.ssafy.happyhouse.dto.StoreDto;
import com.ssafy.happyhouse.service.HouseMapService;
import com.ssafy.happyhouse.service.StoreService;

import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/map")
//@CrossOrigin("*")
public class HouseMapController {
	
	private final Logger logger = LoggerFactory.getLogger(HouseMapController.class);

	@Autowired
	private HouseMapService haHouseMapService;
	@Autowired
	private StoreService storeService;
	
    @ApiOperation(value = "모든 시의 정보를 반환한다", response = List.class)
	@GetMapping("/sido")
	public ResponseEntity<List<SidoGugunCodeDto>> sido() throws Exception {
		logger.debug("sido : {}", haHouseMapService.getSido());
		return new ResponseEntity<List<SidoGugunCodeDto>>(haHouseMapService.getSido(), HttpStatus.OK);
	}
    @ApiOperation(value = "모든 구군의 정보를 반환한다.", response = List.class)
	@GetMapping("/gugun")
	public ResponseEntity<List<SidoGugunCodeDto>> gugun(@RequestParam("sido") String sido) throws Exception {
		return new ResponseEntity<List<SidoGugunCodeDto>>(haHouseMapService.getGugunInSido(sido), HttpStatus.OK);
	}
    @ApiOperation(value = "모든 동의 정보를 반환한다.", response = List.class)
	@GetMapping("/dong")
	public ResponseEntity<List<HouseInfoDto>> dong(@RequestParam("gugun") String gugun) throws Exception {
		return new ResponseEntity<List<HouseInfoDto>>(haHouseMapService.getDongInGugun(gugun), HttpStatus.OK);
	}
    @ApiOperation(value = "모든 아파트의 정보를 반환한다. key: 검색하고자 하는 column명 , pg: 현재 위치한 페이지 번호, word: 검색 키워드, lat:위도, lng: 경도", response = List.class)
	@GetMapping("/apt")
	public ResponseEntity<List<HouseInfoDto>> apt(
			@RequestParam(value="pg", required = false, defaultValue = "1") String pg,
			@RequestParam(value="key", required = false) String key,
			@RequestParam(value="word", required = false) String word,
			 @RequestParam("lat") String lat,
			 @RequestParam("lng") String lng) throws Exception {
		return new ResponseEntity<List<HouseInfoDto>>(haHouseMapService.getSearchList(pg, key, word, lat, lng), HttpStatus.OK);
	}
    @ApiOperation(value = "모든 아파트의 랭킹 정보를 반환한다. dongCode: 검색하고자 하는 법정동의 코드, key: 검색하고자 하는 column명 , lat:위도, lng: 경도", response = List.class)
	@GetMapping("/aptrank")
	public ResponseEntity<List<HouseInfoDto>> aptRanking(
			@RequestParam(value="key", required = true) String key,
			@RequestParam(value="word", required = true) String word,
			@RequestParam(value="rankKey", required = false) String rankKey,
			 @RequestParam("lat") String lat,
			 @RequestParam("lng") String lng) throws Exception {
		return new ResponseEntity<List<HouseInfoDto>>(haHouseMapService.getAptRank(key, word, rankKey, lat, lng), HttpStatus.OK);
	}
//    @ApiOperation(value = "모든 아파트의 페이지별 정보를 반환한다. key: 검색하고자 하는 column명 , pg: 현재 위치한 페이지 번호, word: 검색 키워드", response = List.class)
//	@GetMapping("/paging_apt")
//	public ResponseEntity<PageNavigation> pagingApt(
//			@RequestParam("pg") String pg, 
//			@RequestParam("key") String key, 
//			@RequestParam("word") String word) throws Exception {
//		return new ResponseEntity<PageNavigation>(haHouseMapService.makePageNavigation(pg, key, word), HttpStatus.OK);
//	}
	
	@ApiOperation(value = "모든 아파트 검색별 전체 개수를 반환한다.key: 검색하고자 하는 column명 , word: 검색 키워드,  lat:위도, lng: 경도", response = List.class)
   	@GetMapping("/count")
   	public ResponseEntity<Integer> getTotalCount(
   			@RequestParam(value="key", required = true) String key,
			@RequestParam(value="word", required = true) String word) throws Exception {
   		logger.debug("getTotalCount - 호출");
   		return new ResponseEntity<Integer>(haHouseMapService.getTotalCount(key,word), HttpStatus.OK);
   	}    
	
	@ApiOperation(value = "모든 주변상권 검색별 전체 개수를 반환한다.key: 검색하고자 하는 column명 , word: 검색 키워드,  lat:위도, lng: 경도", response = List.class)
   	@GetMapping("/scount")
   	public ResponseEntity<Integer> getTotalCountSt(
   			@RequestParam(value="key", required = true) String key,
			@RequestParam(value="word", required = true) String word) throws Exception {
   		logger.debug("getTotalCountSt - 호출");
   		return new ResponseEntity<Integer>(storeService.getTotalCount(key,word), HttpStatus.OK);
   	}    
	
	  @ApiOperation(value = "모든 주변상권의 정보를 반환한다. key: 검색하고자 하는 column명 , pg: 현재 위치한 페이지 번호, word: 검색 키워드,  lat:위도, lng: 경도", response = List.class)
	 @GetMapping("/store") 
	 public ResponseEntity<List<StoreDto>> store(
			 @RequestParam("pg") String pg, 
			 @RequestParam("key") String key, 
			 @RequestParam("word") String word,
			 @RequestParam("lat") String lat,
			 @RequestParam("lng") String lng) throws Exception { 
		 return new ResponseEntity<List<StoreDto>>(storeService.getStoreList(pg, key, word, lat, lng), HttpStatus.OK); 
	 }
//	  @ApiOperation(value = "모든 주변상권의 페이지별 정보를 반환한다. key: 검색하고자 하는 column명 , pg: 현재 위치한 페이지 번호, word: 검색 키워드", response = List.class)
//	 @GetMapping("/paging_store")
//	 public ResponseEntity<PageNavigation> pagingStore(
//			 @RequestParam("pg") String pg, 
//			 @RequestParam("key") String key, 
//			 @RequestParam("word") String word) throws Exception {
//		 return new ResponseEntity<PageNavigation>(storeService.makePageNavigation(pg, key, word), HttpStatus.OK);
//	 }
	 
}
