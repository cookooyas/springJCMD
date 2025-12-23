package com.springjcmd.config.root;

import org.apache.ibatis.annotations.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.springjcmd.init.WebInitializer;

/*****************************************************************************************************************************************
 * ConfigContextCommon
 * 
 * Root 컨텍스트 설정 클래스로써, WebInitializer 클래스에서 정의된 rootContext 객체가 해당 클래스를 register하여 전역화 수행.
 * basePackages(com.springjcmd)내의 Service, Repository 어노테이션이 붙은 모든 컴포넌트(bean)를 공통으로 사용하기위해 Root 컨텍스트에 등록함.
 * 활성화된 Profile에 따라 다른 프로퍼티 값을 가져오기 위해 PropertySource 어노테이션 사용해 Spring 컨테이너 초기화 시기에 키 값을 Environment에 등록.
 * PropertySourcesPlaceholderConfigurer라는 특수 빈처리기가 properties 파일 내부에 키-값 형태로 정의된 요소들에서 DataSource빈 생성 전에 여러 설정값을 찾아와 매핑
 *****************************************************************************************************************************************/
@Configuration
@ComponentScan(	basePackages = "com.springjcmd",
				includeFilters = {//root context 에서는 service,repository 만 포함해서 공통으로 사용한다
						@ComponentScan.Filter(type = FilterType.ANNOTATION,value = Service.class),
						@ComponentScan.Filter(type = FilterType.ANNOTATION,value = Repository.class)					
				},
				excludeFilters = {//controller 는 각 서블릿 컨텍스트에서 스캔하여 사용 한다
						@ComponentScan.Filter(type = FilterType.ANNOTATION,value = Controller.class),												
						@ComponentScan.Filter(type = FilterType.ANNOTATION,value = ControllerAdvice.class),
						@ComponentScan.Filter(type = FilterType.ANNOTATION,value = Configuration.class)						
				}
)
@PropertySource("classpath:/application-${spring.profiles.active:local}.properties")
public class ConfigContextCommon {
	private static final Logger LOGGER = LoggerFactory.getLogger(WebInitializer.class); // ⬅️ Logger 선언

	@Autowired
	private final Environment env;
	
	public ConfigContextCommon(Environment env) {
		this.env = env;
		String activeProfile = env.getActiveProfiles().length > 0 ? env.getActiveProfiles()[0] : "default (or LOCAL_SERVER)";
		LOGGER.debug(activeProfile);
	}
}
