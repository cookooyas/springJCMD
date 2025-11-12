package com.springjcmd.config.root;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/********************************************************************************************************************************************
* ConfigContextAspect
* 
* Root 컨텍스트 내 데이터 소스 설정 클래스로써, WebInitializer 클래스에서 정의된 rootContext 객체가 해당 클래스를 register하여 전역화 수행.
* 따로 ComponentScan은 필요없으며, EnableAspectJAutoProxy 어노테이션을 이용하여 CGLIB 방식의 Auto Proxing 기능을 강제함
* 기존의 JDK Dynamic Proxy 방식은 트랜잭션, Advice등을 위해 횡단 관심사(AOP)로 적용하기 위해서 인터페이스를 정의했고, 이를 위해 1대1 매핑이 되는 인터페이스가 필수적이었음.
* 하지만 CGLIB의 경우 실제 클래스의 모든 public메서드에 한해 AOP를 걸기 위해 동적으로 프록시를 생성해주기 때문에 인터페이스가 따로 필요하지 않음.
* 현대의 스프링은 interface와 Aspect 적용여부를 판단하여 JDK Dynamic Proxy와 CGLIB중 하나를 선택한다.
* 중요한 점은 EnableAspectJAutoProxy기능은 로컬 컨텍스트(현재 설정된 곳은 Root 컨텍스트)에서만 활성화되기 때문에, 여러개의 애플리케이션 컨텍스트가 존재할 경우 각각 걸어줘야함.
********************************************************************************************************************************************/
@Configuration
@EnableAspectJAutoProxy
public class ConfigContextAspect {
	// 추후에 CommonContextAspect 클래스에서 Aspect 객체를 생성할 예정
	// 이러한 설정으로 인해 Spring 컨테이너 설정이라는 목적과 AOP 횡단 관심사 로직을 구현하는 목적을 따로 분리가능
//	@Bean
//	public CommonContextAspect commonAspect() {
//		return new CommonContextAspect();
//	}
}
