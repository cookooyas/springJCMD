package com.springjmdc.filter.wrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/***************************************************************************
 * XssRequestWrapper
 * 
 * 보통 래퍼(Wrapper)클래스는 기존 객체(요청)의 기능을 수정 및 확장하기 위해 적용함.
 * 현재 XssRequestWrapper는 HttpServletRequestWrapper로써,
 * HttpServletRequest객체에서 XSS공격에 취약할 수 있는 부분을 사전에 찾아내어 수정하는 역할.
 * 보통은 eGov, Apache Commons Text, OWASP Java HTML Sanitizer등에서 제공하는 메서드를 사용
 * 현재는 단순하게 요청에 <script>태그가 포함되어 있으면 해당 부분만 수정하게끔 로직을 구성함.
 ***************************************************************************/
public class XssRequestWrapper extends HttpServletRequestWrapper {

    public XssRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getParameter(String name) {
    	// super 클래스가 parameter를 넘겨주기 이전에 XSS 취약점을 검사한다.
        String value = super.getParameter(name);
        return _cleanXSS(value);
    }

    @Override
    public String[] getParameterValues(String name) {
    	// super 클래스가 parameter 배열을 넘겨주기 이전에 각각의 파라미터에 대해 XSS 취약점을 검사한다.
        String[] values = super.getParameterValues(name);
        if (values != null) {
            int count = values.length;
            String[] encodedValues = new String[count];
            for (int i = 0; i < count; i++) {
                encodedValues[i] = _cleanXSS(values[i]);
            }
            return encodedValues;
        }
        return super.getParameterValues(name);
    }

    private String _cleanXSS(String value) {
        if (value == null || value.isEmpty()) {
            return value;
        }
        
        // 파라미터를 검사하여 각 파라미터의 값에 "<", ">", "(", ")", "'", "eval(*)", "javascript:", "script" 문자열은 정규식으로 대체한다.
        value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
        value = value.replaceAll("'", "&#39;");
        value = value.replaceAll("eval\\((.*)\\)", "");
        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
        value = value.replaceAll("script", "");
        
        return value;
    }
}