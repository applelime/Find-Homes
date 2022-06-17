package com.pearldonut.findhomes.model;

import lombok.Data;

@Data
public class UserDto {
	private String userid;	//아이디
	private String userpw;	//비밀번호
	private String nickname;//닉네임 (별명)
	private String email;	//이메일
	private String phone;	//전화번호
	private String registerdate; //가입일
	private String usertype;	 //유저타입(관리자여부)
	
	public UserDto(String userId, String userPw) {
		this.userid = userId;
		this.userpw = userPw;
	}
}
