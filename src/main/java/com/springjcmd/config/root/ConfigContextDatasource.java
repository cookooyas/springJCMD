package com.springjcmd.config.root;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/*************************************************************************************************************************
 * ConfigContextDatasource
 * 
 * Root 컨텍스트 내 데이터 소스 설정 클래스로써, WebInitializer 클래스에서 정의된 rootContext 객체가 해당 클래스를 register하여 전역화 수행.
 * 따로 ComponentScan은 필요없으며, EnableTransactionManagement 어노테이션을 이용하여 트랜잭션 매니저를 활성화할 수 있음.
 *************************************************************************************************************************/
@Configuration
@EnableTransactionManagement
public class ConfigContextDatasource {
	private final String jdbcUrl;
	private final String userName;
	private final String passWord;
	private final String jndiName;
	
	public ConfigContextDatasource(Environment env) {
		this.jdbcUrl = env.getProperty("database.url");
		this.userName = env.getProperty("database.username");
		this.passWord = env.getProperty("database.password");
		this.jndiName = env.getProperty("database.jndiname");
	}
	
	// 빈 생성. 빈의 이름을 명시적으로 지정하지 않으면 해당 메서드(또는 클래스)의 camelCase가 bean name으로 정의됨.
	// destroyMethod = "close"를 적용하여 생명주기가 끝난 데이터소스가 소멸하기 전에 HikariDataSource에 정의된 close 메서드를 호출.
	// 위 과정을 통해 안전하게 JDBC커넥션을 닫을 수 있음
	@Bean(destroyMethod = "close")
	// Profile 어노테이션을 이용하면 활성화된 프로필(ActiveProfiles)이 일치할 경우에서만 빈을 생성.
    @Profile({"dev", "local"})
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();

        config.setDriverClassName("org.mariadb.jdbc.Driver");
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(userName);
        config.setPassword(passWord);
        
        // 커넥션 풀 속성은 HikariCP 기본값을 따르거나 필요에 따라 설정
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        
        return new HikariDataSource(config);
    }
	
	// prod 환경에서는 destroy를 따로 수행하지 않음. 
	// JNDI Lookup으로 외부에 존재하는 객체를 참조하여 빈을 등록할 뿐이기 때문.
	// 이렇게 생성될 bean은 따로 name을 정의해주지 않으면 jndiDataSource가 되기 때문에 name을 명시
	@Bean(name = "dataSource")
    @Profile("prod")
    public DataSource jndiDataSource() { // 충돌을 피하기 위해 메서드명은 다르게 유지
        JndiDataSourceLookup lookup = new JndiDataSourceLookup();
        return lookup.getDataSource(jndiName);
    }
	
	// @EnableTransactionManagement가 활성화된 경우, 이 빈이 트랜잭션을 관리함.
	// transactionManager를 찾는 방법은 PlatformTransactionManager타입으로 등록된 빈 중, transactionManager라는 이름의 빈을 먼저 찾음.
	// 빈의 이름이 transactionManager가 아닐 경우, 명시적으로 @Primary 어노테이션을 이용하여 스프링이 해당 빈을 transactionManager로 인식하게 해야함.
	@Bean
	public PlatformTransactionManager transactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
}
