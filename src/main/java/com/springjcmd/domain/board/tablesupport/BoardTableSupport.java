package com.springjcmd.domain.board.tablesupport;

import java.sql.JDBCType;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class BoardTableSupport {
    // 1. 매퍼의 MyBatis3Utils 등에서 인자로 전달할 '테이블 객체'
    public static final BoardTable boardSupport = new BoardTable();

    // 2. 외부에서 impo453rt static 해서 쓸 컬럼들 (상단에 선언된 board 객체의 필드를 참조)
    public static final SqlColumn<Integer> id = boardSupport.id;
    public static final SqlColumn<Integer> title = boardSupport.title;
    public static final SqlColumn<Integer> content = boardSupport.content;
    public static final SqlColumn<Integer> createdAt = boardSupport.createdAt;
    
    public static final class BoardTable extends SqlTable {
        public final SqlColumn<Integer> id = column("ID", JDBCType.INTEGER);
        public final SqlColumn<Integer> title = column("TITLE", JDBCType.INTEGER);
        public final SqlColumn<Integer> content = column("CONTENT", JDBCType.INTEGER);
        public final SqlColumn<Integer> createdAt = column("CREATED_AT", JDBCType.INTEGER);

        public BoardTable() {
            super("BOARD");
        }
    }
}