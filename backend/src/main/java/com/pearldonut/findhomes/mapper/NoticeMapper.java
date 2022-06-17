package com.pearldonut.findhomes.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.pearldonut.findhomes.model.NoticeDto;

@Mapper
public interface NoticeMapper {
	 void createNotice(NoticeDto n) throws SQLException;
	 void updateNotice(NoticeDto n) throws SQLException;
	 void updateViewCount(String num) throws SQLException;
	 void deleteNotice(String num) throws SQLException;
	 List<NoticeDto> selectAll()throws SQLException;
	 NoticeDto selectOne(int num) throws SQLException;
}
