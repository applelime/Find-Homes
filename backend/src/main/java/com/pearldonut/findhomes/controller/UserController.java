package com.pearldonut.findhomes.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pearldonut.findhomes.model.UserDto;
import com.pearldonut.findhomes.service.JwtService;
import com.pearldonut.findhomes.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api("회원관리 API")
@CrossOrigin("*")
@RestController
@RequestMapping("/user")
public class UserController {
	private final String SUCCESS ="success";
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtService jwtService;
	
	@PostMapping("")
	@ApiOperation(value = "회원 등록", notes = "회원 등록")
	public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) { // 회원 가입
		userService.registerUser(userDto);
		return new ResponseEntity<>("success",HttpStatus.OK);
	}
	
	@PutMapping("")
	@ApiOperation(value = "회원정보 수정 ", notes = "회원정보 수정")
	public ResponseEntity<?> updateMember(@RequestBody UserDto userDto) { // 회원정보 수정
		userService.updateUser(userDto);
		return new ResponseEntity<>(SUCCESS,HttpStatus.OK);
	}
	
	@GetMapping("")
	@ApiOperation(value = "전체 회원 조회 ", notes = "전체 회원 조회")
	public ResponseEntity<?> getUserList() {
		List<UserDto> list = userService.getUserList();
		return new ResponseEntity<List<UserDto>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/idcheck/{checkId}")
	@ApiOperation(value = "ID 중복검사", notes = "ID 중복검사")
	public ResponseEntity<?> idCheck(@PathVariable String checkId ){
		boolean flag = userService.idCheck(checkId);
		if(flag) {
			return new ResponseEntity<>(SUCCESS,HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	
	@GetMapping("/{userid}")
	@ApiOperation(value = "회원정보 조회", notes = "회원정보 조회")
	public ResponseEntity<?> mypage(@PathVariable String userid) {
		UserDto userDto = userService.mypage(userid);
		if(userDto != null && userDto.getUserid() != null) {
			return new ResponseEntity<UserDto>(userDto,HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	
	@DeleteMapping("/{userid}") 
	@ApiOperation(value = "회원 탈퇴", notes = "회원 탈퇴")
	public ResponseEntity<?> deleteUser(@PathVariable String userid){
		userService.deleteUser(userid);
		return new ResponseEntity<>(SUCCESS,HttpStatus.OK);
	}
	
	@PostMapping("/findpw")
	@ApiOperation(value = "비밀번호 찾기", notes = "비밀번호 찾기")
	public ResponseEntity<?> findPass(@RequestBody UserDto userDto){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("userid", userDto.getUserid());
		map.put("nickname", userDto.getNickname());
		map.put("phone", userDto.getPhone());
		
		String pw = userService.findPass(map);
		if(pw != null) {
			return new ResponseEntity<String>(pw,HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	
	
	@GetMapping("/info/{userid}")
	@ApiOperation(value = "회원 인증", notes = "회원 정보를 담은 Token을 반환한다.")
	public ResponseEntity<Map<String, Object>> getInfo(
			@PathVariable @ApiParam(value = "인증할 회원의 아이디", required = true) String userid,
			HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		
		if (jwtService.isUsable(request.getHeader("access-token"))) {
			try {
				// 로그인 사용자 정보.
				UserDto memberDto = userService.mypage(userid);
				resultMap.put("userInfo", memberDto);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} catch (Exception e) {
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			// logger.error("사용 불가능 토큰!!!");
			resultMap.put("message", "fail");
			status = HttpStatus.ACCEPTED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@PostMapping("/login")
	@ApiOperation(notes = "로그인 가능 여부 판단 & 로그인", value = "true(가능)/false(불가능)")
	public ResponseEntity<?> login(@RequestBody UserDto userDto){ // 로그인 조회
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("userid", userDto.getUserid());
		map.put("userpw", userDto.getUserpw());
//		System.out.println("userid: "+ userDto.getUserid());
		
		boolean flag = userService.login(map);
		if(flag) {
			Map<String, Object> resultMap = new HashMap<>();
			String token = jwtService.create("userid", userDto.getUserid(), "access-token");// key, data, subject
			resultMap.put("access-token", token);
			resultMap.put("message", SUCCESS);

			return new ResponseEntity<>(resultMap, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
		}
	}
}
