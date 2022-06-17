package com.pearldonut.findhomes.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
	String userid;	//아이디
	String userpw;	//비밀번호
}
