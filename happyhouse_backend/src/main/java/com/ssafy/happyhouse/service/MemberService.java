package com.ssafy.happyhouse.service;

import com.ssafy.happyhouse.dto.MemberDto;

public interface MemberService {

	public MemberDto login(MemberDto memberDto) throws Exception;
	public MemberDto userInfo(String userid) throws Exception;
	public boolean register(MemberDto memberDto) throws Exception;
	public boolean idCheck(String userid) throws Exception;
	public boolean userModify(MemberDto memberDto) throws Exception;
}
