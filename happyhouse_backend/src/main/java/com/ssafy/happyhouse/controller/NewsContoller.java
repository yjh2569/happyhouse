package com.ssafy.happyhouse.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.dto.NewsDto;

import io.swagger.annotations.Api;

@Api("NewsController V1")
@RestController
@CrossOrigin
public class NewsContoller {

	@GetMapping("news")
	public ResponseEntity<List<NewsDto>> news(@RequestParam("start") int start, @RequestParam("query") String query) {
		String clientId = "h41KtiWMWc2FvxWiL41h"; // 애플리케이션 클라이언트 아이디값"
		String clientSecret = "VvSy0Q3Epd"; // 애플리케이션 클라이언트 시크릿값"

		String textQuery = null;
		try {
			textQuery = URLEncoder.encode(query, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("검색어 인코딩 실패", e);
		}
		
		String apiURL = "https://openapi.naver.com/v1/search/news.json?query=" + textQuery + "&start=" + start;    // json 결과
        //String apiURL = "https://openapi.naver.com/v1/search/blog.xml?query="+ text; // xml 결과


        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        String responseBody = get(apiURL,requestHeaders);
        JSONObject jsonObject = new JSONObject(responseBody);
        JSONArray jsonArray = jsonObject.getJSONArray("items");
        ArrayList<NewsDto> list = new ArrayList<>();
        if (jsonArray != null) {
        	int len = jsonArray.length();
        	for (int i = 0; i < len; i++) {
        		JSONObject jsonItem = jsonArray.getJSONObject(i);
        		String title = (String) jsonItem.get("title");
        		String link = (String) jsonItem.get("link");
        		String pubDate = (String) jsonItem.get("pubDate");
        		list.add(new NewsDto(title, link, pubDate));
        	}
        }
        return new ResponseEntity<List<NewsDto>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/news/count")
	public ResponseEntity<Integer> newsCount(@RequestParam("query") String query) {
		String clientId = "h41KtiWMWc2FvxWiL41h"; // 애플리케이션 클라이언트 아이디값"
		String clientSecret = "VvSy0Q3Epd"; // 애플리케이션 클라이언트 시크릿값"

		String textQuery = null;
		try {
			textQuery = URLEncoder.encode(query, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("검색어 인코딩 실패", e);
		}
		
		String apiURL = "https://openapi.naver.com/v1/search/news.json?query=" + textQuery + "&start=1";    // json 결과
        //String apiURL = "https://openapi.naver.com/v1/search/blog.xml?query="+ text; // xml 결과


        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        String responseBody = get(apiURL,requestHeaders);
        JSONObject jsonObject = new JSONObject(responseBody);
        Integer totalCount = (Integer) jsonObject.get("total");
        return new ResponseEntity<Integer>(totalCount, HttpStatus.OK);
	}
	
	private static String get(String apiUrl, Map<String, String> requestHeaders){
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }


            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 에러 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }


    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }


    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);


        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();


            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }


            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }
}
