package com.springjcmd.domain.board.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.select.SelectDSLCompleter;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;
import org.mybatis.dynamic.sql.util.mybatis3.MyBatis3Utils;

import static                                                                                                                                                                                                                                                                                                          com.springjcmd.domain.board.tablesupport.BoardTableSupport.*;
import com.springjcmd.domain.board.dto.BoardDto;

import java.util.Optional;

@Mapper
public interface BoardMapper {
	BasicColumn[] selectList = BasicColumn.columnList(id,title,content);
	
	@SelectProvider(type = SqlProviderAdapter.class, method="select")
	Optional<BoardDto> selectOne(SelectStatementProvider selectStatement);
	default Optional<BoardDto> selectOne(SelectDSLCompleter completer) {
		return  MyBatis3Utils.selectOne(this::selectOne, selectList, boardSupport, completer);
	}
}
