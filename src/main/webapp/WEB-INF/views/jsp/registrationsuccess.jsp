<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>User Registration Confirm Page</title>
    <link rel="stylesheet" href="<c:url value='/resources/static/css/bootstrap.css'/>">
    <link rel="stylesheet" href="<c:url value='/resources/static/css/app.css'/>">
</head>
<body>
<div class="generic-container">
    <c:if test="${not empty success}">
        <div class="alert alert-success lead">
            ${success}
        </div>
    </c:if>

	<span class="well floatRight">
		Go to <a href="<c:url value='/users'/>" class="btn btn-primary custom-width">Users List</a>
	</span>
</div>
</body>

</html>