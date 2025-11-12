package com.springjmdc.enums;

/****************************************************
 * EnumProfile
 * 
 * Profile에 대한 Enum 객체.
 * 스프링 구동 시 activeProfiles에 선언될 수 있는 항목을 객체화
 ****************************************************/

public enum EnumProfile {
	LOCAL_SERVER("local"), //로컬 PC
	DEV_SERVER("dev"), //테스트 서버
	PROD_SERVER("prod"); //운영 서버
	
	private String title;
	
	private EnumProfile(String title) {    
        this.title = title;
    }   
        
    public String getCode() {
    	return name();
    }    
    
    public String getValue() {
    	return this.title;
    }
}
