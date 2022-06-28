package com.ssafy.happyhouse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.dto.CommentDto;
import com.ssafy.happyhouse.mapper.CommentMapper;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentMapper commentMapper;
	
	@Override
	public List<CommentDto> selectComment(int qnano) throws Exception {
		return commentMapper.selectComment(qnano);
	}
	
	@Override
	public CommentDto selectCommentById(int commentid) throws Exception {
		return commentMapper.selectCommentById(commentid);
	}

	@Override
	public boolean insertComment(CommentDto commentDto) throws Exception {
		return commentMapper.insertComment(commentDto) == 1;
	}

	@Override
	public boolean updateComment(CommentDto commentDto) throws Exception {
		return commentMapper.updateComment(commentDto) == 1;
	}

	@Override
	public boolean deleteComment(int commentid) throws Exception {
		return commentMapper.deleteComment(commentid) == 1;
	}

}
