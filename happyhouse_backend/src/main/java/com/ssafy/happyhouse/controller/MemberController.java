package com.ssafy.happyhouse.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.dto.MemberDto;
import com.ssafy.happyhouse.service.JwtService;
import com.ssafy.happyhouse.service.JwtServiceImpl;
import com.ssafy.happyhouse.service.MemberService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api("MemberController V1")
@RestController
@RequestMapping("/user")
public class MemberController {

	public static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private JwtService jwtService;

	@Autowired
	private MemberService memberService;

	@ApiOperation(value = "로그인", notes = "Access-token과 로그인 결과 메세지를 반환한다.", response = Map.class)
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(
			@RequestBody @ApiParam(value = "로그인 시 필요한 회원정보(아이디, 비밀번호).", required = true) MemberDto memberDto) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			MemberDto loginUser = memberService.login(memberDto);
			if (loginUser != null) {
				String token = jwtService.create("userid", loginUser.getUserid(), "access-token");// key, data, subject
				logger.debug("로그인 토큰정보 : {}", token);
				resultMap.put("access-token", token);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} else {
				resultMap.put("message", FAIL);
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception e) {
			logger.error("로그인 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "회원인증", notes = "회원 정보를 담은 Token을 반환한다.", response = Map.class)
	@GetMapping("/info/{userid}")
	public ResponseEntity<Map<String, Object>> getInfo(
			@PathVariable("userid") @ApiParam(value = "인증할 회원의 아이디.", required = true) String userid,
			HttpServletRequest request) {
//		logger.debug("userid : {} ", userid);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		if (jwtService.isUsable(request.getHeader("access-token"))) {
			logger.info("사용 가능한 토큰!!!");
			try {
//				로그인 사용자 정보.
				MemberDto memberDto = memberService.userInfo(userid);
				resultMap.put("userInfo", memberDto);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} catch (Exception e) {
				logger.error("정보조회 실패 : {}", e);
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			logger.error("사용 불가능 토큰!!!");
			resultMap.put("message", FAIL);
			status = HttpStatus.ACCEPTED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@ApiOperation(value = "회원가입", notes = "입력한 정보를 바탕으로 회원 가입한다.", response = String.class)
	@PostMapping("/register")
	public ResponseEntity<String> register(
			@RequestBody @ApiParam(value = "회원가입 시 필요한 회원정보(아이디, 비밀번호, 이메일, 주소).", required = true) MemberDto memberDto) {
		HttpStatus status = null;
		String message = "";
		try {
			if (memberService.register(memberDto)) {
				message = SUCCESS;
				status = HttpStatus.ACCEPTED;
			} else {
				message = FAIL;
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception e) {
			logger.error("로그인 실패 : {}", e);
			message = e.getMessage();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<String>(message, status);
	}
	
	@ApiOperation(value = "아이디 체크", notes = "회원가입 시 이미 가입된 아이디인지 확인한다.", response = String.class)
	@GetMapping("/check/{userid}")
	public ResponseEntity<String> idCheck(
			@PathVariable("userid") @ApiParam(value = "중복 확인할 아이디.", required = true) String userid,
			HttpServletRequest request) {
		String message = "";
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			if (memberService.idCheck(userid)) {
				logger.info("사용 가능한 아이디!!!");
				message = SUCCESS;
			} else {
				logger.info("사용 불가능한 아이디!!!");
				message = FAIL;
			}
		} catch (Exception e) {
			logger.error("아이디 확인 실패 : {}", e);
			message = FAIL;
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<String>(message, status);
	}
	
	@ApiOperation(value = "회원정보 수정", notes = "사용자 정보를 수정한다.", response = String.class)
	@PutMapping("/modify")
	public ResponseEntity<String> userModify(
			@RequestBody @ApiParam(value = "정보 수정 시 필요한 회원정보(아이디, 비밀번호, 이메일, 주소).", required = true) MemberDto memberDto) {
		HttpStatus status = null;
		String message = "";
		try {
			if (memberService.userModify(memberDto)) {
				message = SUCCESS;
				status = HttpStatus.ACCEPTED;
			} else {
				message = FAIL;
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception e) {
			logger.error("회원정보 수정 실패 : {}", e);
			message = e.getMessage();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<String>(message, status);
	}

}
