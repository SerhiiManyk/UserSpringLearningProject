<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Universal Error Page</title>
    <link rel="stylesheet" href="<c:url value='/resources/static/css/bootstrap.css'/>">
    <link rel="stylesheet" href="<c:url value='/resources/static/css/app.css'/>">
</head>
<body>
<div class="generic-container">
    <c:if test="${not empty alertMessage}">
        <div class="alert alert-danger lead">
            ${alertMessage}
        </div>
    </c:if>

	<span class="well floatRight">
		Go to <a href="<c:url value='${backUrl}'/>" class="btn btn-primary custom-width">${backLabel}</a>
	</span>
</div>
</body>

</html>