package com.springjcmd.config.servlet;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

/****************************************************************************************************************************
 * ConfigContextMvcServlet
 * 
 * MVC Servlet 컨텍스트 설정 클래스로써, WebInitializer 클래스에서 정의된 DispatcherServlet 객체에 연결되어 자식 컨텍스트를 구성함.
 * basePackages(com.springjcmd) 내의 Controller 및 RestController 어노테이션이 붙은 웹 계층 컴포넌트(bean)를 등록하고 관리함.
 * View Resolver, Message Converter, Interceptor 등 Spring MVC의 핵심 설정을 정의하며, 클라이언트의 HTTP 요청 처리를 전담함.
 * EnableWebMvc 어노테이션을 이용하면 핵심 인프라빈(RequestMappingHandlerAdapter, MappingJackson2JsonView 등..)을 개발자가 고려할 필요 없음.
 * EnableWebMvc 어노테이션을 비활성화 할 경우 MessageConverters, JsonViewResolver, ReqeustMappingHandler등을 직접 구현해서 빈으로 등록해야 함.
 ****************************************************************************************************************************/
@Configuration
@ComponentScan(	basePackages = "com.springjcmd",
				includeFilters = {//servlet context 에서는 Controller 만 포함해서 사용한다
						@ComponentScan.Filter(type = FilterType.ANNOTATION,value = Controller.class)					
				},
				excludeFilters = {//root context 의 service,repository 그리고 환경구성은 제외						
						@ComponentScan.Filter(type = FilterType.ANNOTATION,value = Service.class),
						@ComponentScan.Filter(type = FilterType.ANNOTATION,value = Repository.class)				
				}
)
@EnableAspectJAutoProxy
@EnableWebMvc
public class ConfigContextMvcServlet {
	
	// Spring은 빈으로 등록된 모든 ViewResolver 타입을 view resolver로 사용한다.
	// UrlBasedViewResolver는 뷰의 이름에 접두사와 접미사를 붙여 원하는 뷰 경로를 완성시키는 기능만 제공
	// 실제 적용되는 뷰 기술은 ViewClass를 정의하여 구현한다.
	// 만약 JSP기반이라면 setViewClass가 JstlView(JSP)로 선언된 하위 클래스 InternalResourceViewResolver를 사용해도 된다.
	@Bean
	public UrlBasedViewResolver urlBasedViewResolver() {			
		UrlBasedViewResolver urlBasedViewResolver =  new UrlBasedViewResolver();
		urlBasedViewResolver.setOrder(1);
		urlBasedViewResolver.setViewClass(JstlView.class); // InternalResourceViewResolver를 사용할 경우 해당 옵션이 미리 적용되어있음.
		urlBasedViewResolver.setPrefix("/WEB-INF/jsp/");
		urlBasedViewResolver.setSuffix(".jsp");
		return urlBasedViewResolver;
	}
}
