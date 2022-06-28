package com.ssafy.happyhouse.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.happyhouse.dto.CommentDto;

@Mapper
public interface CommentMapper {
	List<CommentDto> selectComment(int qnano) throws SQLException;
	CommentDto selectCommentById(int commentid) throws SQLException;
	int insertComment(CommentDto commentDto) throws SQLException;
	int updateComment(CommentDto commentDto) throws SQLException;
	int deleteComment(int commentid) throws SQLException;
}
