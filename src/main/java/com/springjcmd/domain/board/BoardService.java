package com.springjcmd.domain.board;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.springjcmd.init.WebInitializer;

@Service
public class BoardService {
	private static final Logger LOGGER = LoggerFactory.getLogger(WebInitializer.class);

	public List<Map<String,Object>> getBoardListAsMap() {
		return null;
	}
}
