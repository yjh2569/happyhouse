package com.ssafy.happyhouse.service;

import java.util.List;

import com.ssafy.happyhouse.dto.NoticeDto;

public interface NoticeService {
	public List<NoticeDto> retrieveNotice(String pg, String key, String word);
	int getTotalCount(String key, String word);
	public NoticeDto detailNotice(int articleno);
	public boolean writeNotice(NoticeDto notice);
	public boolean updateNotice(NoticeDto notice);
	public boolean deleteNotice(int articleno);
}
