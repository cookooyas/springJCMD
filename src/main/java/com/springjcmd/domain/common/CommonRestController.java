package com.springjcmd.domain.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/common")
public class CommonController {
	@GetMapping(value = "/healthCheck.do")
	public String healthCheck() {
		return "OK";
	}
}
