package com.pearldonut.findhomes.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pearldonut.findhomes.mapper.UserMapper;
import com.pearldonut.findhomes.model.UserDto;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	
	@Override
	public void registerUser(UserDto userDto) {
		userMapper.registerUser(userDto);
	}

	@Override
	public void updateUser(UserDto userDto) {
		userMapper.updateUser(userDto);
	}

	@Override
	public UserDto mypage(String userid) {
		return userMapper.mypage(userid);
	}

	@Override
	public boolean login(Map<String, String> map) {
//		System.out.println(map.get("userid"));
//		System.out.println(map.get("userpw"));
		int check = userMapper.login(map);
//		System.out.println(check);
		
		if (check==1)
			return true;
		
		return false;
	}

	@Override
	public String findPass(Map<String, String> map) {
		return userMapper.findPass(map);
	}

	@Override
	public void deleteUser(String userid) {
		userMapper.deleteUser(userid);
	}

	@Override
	public List<UserDto> getUserList() {
		return userMapper.getUserList();
	}

	@Override
	public boolean idCheck(String checkId) {
		int check = userMapper.idCheck(checkId);
		return check >= 1 ? false : true;
	}
}
