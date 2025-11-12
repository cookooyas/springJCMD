package com.springjcmd.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.springjcmd.filter.wrapper.XssRequestWrapper;

/***************************************************************************
 * HTMLTagFilter
 * 
 * WebInitializer클래스에서 임포트하는 HTML TAG 보안을 위한 커스텀 필터.
 * 넘겨받은 HttpServletRequest객체 요청을 서블릿 또는 다음 필터에 전달(doFilter)하기 이전에 적용
 * XSS공격 방어를 위한 기본적인 필터만 테스트해보기 위해 하기와 같이 커스텀 필터를 제작
 ***************************************************************************/
public class HTMLTagFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// 생성부, 필요시 로직 추가
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		XssRequestWrapper wrapper = new XssRequestWrapper(httpRequest);
		
		chain.doFilter(wrapper, response);
	}

	@Override
	public void destroy() {
		// 소멸부, 필요시 로직 추가
	}
}
