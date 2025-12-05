package com.springjcmd.config.root;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@MapperScan(basePackages = {"com.springjcmd.mapper"})
@EnableTransactionManagement
public class ConfigMyBatis {
	@Bean
	public SqlSessionFactory sqlSessionFactory(@Autowired DataSource dataSource) throws Exception {
		org.mybatis.spring.SqlSessionFactoryBean factoryBean = new org.mybatis.spring.SqlSessionFactoryBean();
		factoryBean.setDataSource(dataSource);
		
		return factoryBean.getObject();
	}
	
	@Bean
    public SqlSessionTemplate sqlSessionTemplate(@Autowired SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
