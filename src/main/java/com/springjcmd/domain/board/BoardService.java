package com.springjcmd.domain.board;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.springjcmd.domain.board.dto.BoardDto;
import com.springjcmd.domain.board.mapper.BoardMapper;
import com.springjcmd.domain.board.tablesupport.BoardTableSupport;
import com.springjcmd.init.WebInitializer;
import lombok.RequiredArgsConstructor;

import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;;

/*****************************************************************
 * BoardService 클래스
 * 
 * 서비스 계층, CGLIB 방식 적용. MyBatis는 별도로 JDK Dynamic Proxy 사용. 의존성 주입방법은 총 3가지가 있음.
 * 필드 주입, 생성자 주입, setter 주입. 보통 @Autowired, @Resource, @Inject로 세가지 방법을 사용하지만 현재
 * 프로젝트에서는 롬복을 이용한 주입방법을 적용함. 롬복의 RequiredArgsConstructor로 필수 생성자를 만들며, 필드 멤버는
 * 불변성을 보장. final이 붙은 필드의 생성자를 롬복이 처리해주며 의존성을 주입해줌.
 *****************************************************************/
@Service
@RequiredArgsConstructor
public class BoardService {
	private static final Logger LOGGER = LoggerFactory.getLogger(WebInitializer.class);

	private final BoardMapper boardMapper;

	public BoardDto getBoardListAsMap(BoardDto boardDto) {
		return boardMapper.selectOne(c -> c.where().and(BoardTableSupport.id, isEqualTo(boardDto.getId())))
				.orElseGet(() -> new BoardDto());
	}
}
