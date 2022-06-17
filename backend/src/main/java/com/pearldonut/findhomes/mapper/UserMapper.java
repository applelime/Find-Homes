package com.pearldonut.findhomes.mapper;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.pearldonut.findhomes.model.UserDto;
@Mapper
public interface UserMapper {
	public void registerUser(UserDto userDto); //회원가입
	public void updateUser(UserDto userDto); //회원정보 수정
	public UserDto mypage(String userid); //회원 조회(검색)
	public int login(Map<String, String> map); //로그인 - 유저 있는지
	public String findPass(Map<String, String> map); //비밀번호 찾기
	public void deleteUser(String userid); // 회원 탈퇴
	public List<UserDto> getUserList();
	public int idCheck(String userid);
}
