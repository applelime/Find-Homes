package com.pearldonut.findhomes.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.pearldonut.findhomes.model.QnaBoardDto;

@Mapper
public interface QnaBoardMapper {
	 void createQnaBoard(QnaBoardDto n) throws SQLException;
	 void updateQnaBoard(QnaBoardDto n) throws SQLException;
	 void updateViewCount(String num) throws SQLException;
	 void deleteQnaBoard(String num) throws SQLException;
	 List<QnaBoardDto> selectAll( )throws SQLException;
	 QnaBoardDto selectOne(String num) throws SQLException;
}
