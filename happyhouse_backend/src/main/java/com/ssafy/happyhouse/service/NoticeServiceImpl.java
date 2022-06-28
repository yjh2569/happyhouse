package com.ssafy.happyhouse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.happyhouse.dto.ListParameterDto;
import com.ssafy.happyhouse.dto.NoticeDto;
import com.ssafy.happyhouse.mapper.NoticeMapper;

@Service
public class NoticeServiceImpl implements NoticeService {

	@Autowired
	private NoticeMapper noticeMapper;
	
	@Override
	public List<NoticeDto> retrieveNotice(String pg, String key, String word) {
		ListParameterDto listParameterDto = new ListParameterDto();
		int pgno = pg != null && pg.length() > 0 ? Integer.parseInt(pg) : 1;
		int countPerPage = 10;
		int start = (pgno - 1) * countPerPage;
		listParameterDto.setKey(key == null ? "" : key.trim());
		listParameterDto.setWord(word == null ? "" : word.trim());
		listParameterDto.setStart(start);		
		listParameterDto.setCountPerPage(countPerPage);
		List<NoticeDto> list = noticeMapper.selectNotice(listParameterDto);
		for (NoticeDto notice : list) {
			if (notice.getSubject() == null) {
				notice.setSubject("게시글 없음");
				continue;
			}
		}
		return list;
//		return noticeMapper.selectNotice();
	}

	@Override
	public NoticeDto detailNotice(int articleno) {
		return noticeMapper.selectNoticeByNo(articleno);
	}

	@Override
	@Transactional
	public boolean writeNotice(NoticeDto notice) {
		return noticeMapper.insertNotice(notice) == 1;
	}

	@Override
	@Transactional
	public boolean updateNotice(NoticeDto notice) {
		return noticeMapper.updateNotice(notice) == 1;
	}

	@Override
	@Transactional
	public boolean deleteNotice(int articleno) {
		return noticeMapper.deleteNotice(articleno) == 1;
	}

	@Override
	public int getTotalCount(String key, String word) {
		ListParameterDto listParameterDto = new ListParameterDto();		
		listParameterDto.setKey(key == null ? "" : key.trim());
		listParameterDto.setWord(word == null ? "" : word.trim());
		return noticeMapper.getTotalCount(listParameterDto);
	}
}
