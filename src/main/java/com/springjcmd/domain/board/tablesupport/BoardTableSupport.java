package com.springjcmd.domain.board.tablesupport;

import java.sql.JDBCType;

import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class BoardTableSupport {
	public static final SqlTable board = SqlTable.of("BOARD");

	public static final SqlColumn<Integer> id = board.column("ID",JDBCType.INTEGER);
	public static final SqlColumn<Integer> title = board.column("TITLE",JDBCType.INTEGER);
	public static final SqlColumn<Integer> content = board.column("CONTENT",JDBCType.INTEGER);
	public static final SqlColumn<Integer> createdAt = board.column("CREATED_AT",JDBCType.INTEGER);
}
