package com.pearldonut.findhomes.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AreaMapper {
	List<Map<String, String>> sidoList();
	List<Map<String, String>> gugunList(String sidoId);
	List<Map<String, String>> dongList(String dongId);
}
