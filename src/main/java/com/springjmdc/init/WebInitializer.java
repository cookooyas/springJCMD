package com.springjmdc.init;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import com.springjmdc.enums.EnumProfile;
import com.springjmdc.filter.HTMLTagFilter;

/*****************************************************************
 * WebApplicationInitializer 인터페이스
 * 
 * WebApplicationInitializer는 Spring MVC를 기반으로하는 
 * 웹 애플리케이션을 POJO방식으로 설정하게 해주는 인터페이스
 * web.xml의 대체 역할이며, JavaConfig 환경에서 사용
 * Spring은 구동 시 web.xml이 없다면 해당 인터페이스의 구현체 클래스들을 찾아 실행
 *****************************************************************/
public class WebInitializer implements WebApplicationInitializer{
	
	/**********************************************************
	 * onStartup
	 * 
	 * 웹 애플리케이션의 시작시점에 실행
	 * 스프링 컨테이너와 서블릿 컨테이너(WAS)를 통합하고 설정하는 역할
	 * 통합된 서블릿 컨테이너가 제공하는 ServletContext 객체가 인자로 전달
	 **********************************************************/
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		// Root 컨텍스트 정의
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		// 특정 Root 컨텍스트 설정 클래스 목록 (추가 예정)
		Class<?>[] contextClasses = new Class[] {};
		// Root 컨텍스트에 설정 클래스 등록
		rootContext.register(contextClasses);
		// 모든 서블릿 컨텍스트에 Root 컨텍스트 등록
		servletContext.addListener(new ContextLoaderListener(rootContext));
		
		// WEB MVC 컨텍스트 정의
		AnnotationConfigWebApplicationContext mvcContext = new AnnotationConfigWebApplicationContext();
		// MVC 컨텍스트에 설정 클래스 등록
		mvcContext.register();
		
		// MVC 컨텍스트의 프론트 컨트롤러 역할의 Dispatcher 서블릿 정의
		DispatcherServlet mvcDispatcherServlet = new DispatcherServlet(mvcContext);
		// 요청에 적합한 HTTP method 또는 handler가 없다면, 404예외를 던짐.
		mvcDispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
		// 서블릿 컨텍스트에 Dispatcher Servlet을 WAS에 등록하는 mvcServletRegistration라는 이름으로 생성
		ServletRegistration.Dynamic mvcServletRegistration = servletContext.addServlet("mvcServletRegistration", mvcDispatcherServlet);
		// 서블릿 로딩 중 우선순위 보장
		mvcServletRegistration.setLoadOnStartup(1);
		// 디스패처 서블릿은 .do로 끝나는 모든 요청을을 처리하도록 매핑
		mvcServletRegistration.addMapping("*.do");
		
		// 인코딩 필터 정의
		FilterRegistration.Dynamic characterEncodingFilter = servletContext.addFilter("characterEncodingFilter", new CharacterEncodingFilter());
		// 인코딩 필터 적용 범위 설정 (필터가 적용될 디스패처 타입, 필터가 서블릿 매핑 앞에 적용될지 여부, 필터가 적용될 URL 패턴)
		characterEncodingFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
		// 인코딩 타입 설정 (CharacterEncodingFilter의 매개변수 encoding 값 초기화)
		characterEncodingFilter.setInitParameter("encoding", "UTF-8");
		// 인코딩 타입 설정 (CharacterEncodingFilter의 매개변수 forceEncoding 값 초기화, forceEncoding : 모든 요청과 응답에서도 필터 인코딩 적용 여부)
		characterEncodingFilter.setInitParameter("forceEncoding", "true");
		
		// HTML TAG 보안 필터
		FilterRegistration.Dynamic htmlTagFilter = servletContext.addFilter("htmlTagFilter", new HTMLTagFilter());
		// HTML TAG 보안 필터 적용 범위 설정 (필터가 적용될 디스패처 타입, 필터가 서블릿 매핑 앞에 적용될지 여부, 필터가 적용될 URL 패턴)
		htmlTagFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
		
		// Root 컨텍스트의 프로필에 따라 별도의 설정
		_setActiveProfile(rootContext);
	}

	/**********************************************************************
	 * _setActiveProfile
	 * 
	 * 실행 옵션에서 설정한 profile에 따라 ActiveProfiles 값이 변경됨.
	 * 선언된 profile이 없다면, ActiveProfile은 EnumProfile의 LOCAL_SERVER로 고정
	 **********************************************************************/
	private void _setActiveProfile(AnnotationConfigWebApplicationContext rootContext) {
		String[] activeProfile = rootContext.getEnvironment().getActiveProfiles();
		if(activeProfile.length == 0) {
			//실행환경 옵션에 profile 이 없는 경우 기본적으로는 개발자 환경으로 설정		
			rootContext.getEnvironment().setActiveProfiles(EnumProfile.LOCAL_SERVER.getValue());
		}		
	}
	
}
