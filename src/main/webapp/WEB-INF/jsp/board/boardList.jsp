<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>게시판 목록 (Map)</title>
</head>
<body>
    <h1>게시판 목록 (Map 방식)</h1>
    <table border="1">
        <thead>
            <tr>
                <th>ID</th>
                <th>제목</th>
                <th>작성일</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="item" items="${list}">
                <tr>
                    <td>${item.id}</td> 
                    <td>${item.title}</td>
                    <td>${item.created_at}</td> 
                </tr>
            </c:forEach>
            <c:if test="${empty list}">
                <tr>
                    <td colspan="3">등록된 게시글이 없습니다.</td>
                </tr>
            </c:if>
        </tbody>
    </table>
</body>
</html>