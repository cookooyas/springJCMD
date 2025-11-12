package com.springjcmd.config.root;

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

/*************************************************************************************************************************
 * ConfigContextCommon
 * 
 * Root 컨텍스트 설정 클래스로써, WebInitializer 클래스에서 정의된 rootContext 객체가 해당 클래스를 register하여 전역화 수행.
 * basePackages(com.springjcmd)내의 Service, Repository 어노테이션이 붙은 모든 컴포넌트(bean)를 공통으로 사용하기위해 Root 컨텍스트에 등록함.
 * 활성화된 Profile에 따라 다른 프로퍼티 값을 가져오기 위해 PropertySource 어노테이션 사용.
 *************************************************************************************************************************/
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
@PropertySource("classpath:/egovframework/profileprop/config-${spring.profiles.active}.properties")
public class ConfigContextCommon {
	@Autowired
	private final Environment env;
	
	public ConfigContextCommon(Environment env) {
		this.env = env;
		String activeProfile = env.getActiveProfiles().length > 0 ? env.getActiveProfiles()[0] : "default (or LOCAL_SERVER)";
		// 추후 Logger 추가 및 수정 예정
		System.out.println(activeProfile);
	}
}
