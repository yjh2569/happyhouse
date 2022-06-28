package com.ssafy.happyhouse.mapper;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.happyhouse.dto.MemberDto;

@Mapper
public interface MemberMapper {

	public MemberDto login(MemberDto memberDto) throws SQLException;
	public MemberDto userInfo(String userid) throws SQLException;
	public int register(MemberDto memberDto) throws SQLException;
	public int idCheck(String userid) throws SQLException;
	public int userModify(MemberDto memberDto) throws SQLException;
}