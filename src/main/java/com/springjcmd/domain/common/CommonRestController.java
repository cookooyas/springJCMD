package com.springjcmd.domain.common;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/api")
public class CommonRestController {
	@GetMapping(value = "/healthCheck")
	public String healthCheck() {
		return "OK";
	}
}
