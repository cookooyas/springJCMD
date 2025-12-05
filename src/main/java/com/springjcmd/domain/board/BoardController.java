package com.springjcmd.domain.board;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springjcmd.init.WebInitializer;

@Controller
@RequestMapping("/board")
public class BoardController {
	private static final Logger LOGGER = LoggerFactory.getLogger(WebInitializer.class);
	@Autowired
	BoardService boardService;
	
	public BoardController() {
		LOGGER.debug("MemberController 빈 생성");
	}
	
	@GetMapping("/boardList.do")
	public String getBoardList(Model model) {
		List<Map<String, Object>> boardList = boardService.getBoardListAsMap();
		model.addAttribute("list",boardList);
		return "board/boardList";
	}
}
