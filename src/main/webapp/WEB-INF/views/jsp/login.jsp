<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>User Login Page</title>
    <link rel="stylesheet" href="<c:url value='/resources/static/css/bootstrap.css'/>">
    <link rel="stylesheet" href="<c:url value='/resources/static/css/app.css'/>">
</head>

<body>

 	<div class="generic-container">
	<div class="well lead">Please Login to enter to User List</div>


    <!-- [NEW] Standard HTML form for Spring Security -->
    <form method="post" action="${pageContext.request.contextPath}/login">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

        <!-- EMAIL -->
        <div class="row">
            <div class="form-group col-md-12">
                <label class="col-md-3 control-label" for="email"> Email</label>
                <div class="col-md-7">
                    <!-- [NEW] name="email" замість form:input з modelAttribute -->
                    <input type="text" name="email" id="email" class="form-control input-sm" required/>
                </div>
            </div>
        </div>

        <!-- PASSWORD -->
        <div class="row">
            <div class="form-group col-md-12">
                <label class="col-md-3 control-label" for="password"> Password </label>
                <div class="col-md-7">
                    <!-- [NEW] name="password" замість form:password -->
                    <input type="password" name="password" id="password" class="form-control input-sm" required/>
                </div>
            </div>
        </div>

        <!-- BUTTONS -->
        <div class="row mb-3">
            <div class="col text-end">
                <button type="submit" class="btn btn-primary btn-sm">Login</button>
                <a href="<c:url value='/welcome'/>" class="btn btn-secondary btn-sm">Back</a>
            </div>
        </div>

        <!-- [NEW] Error and logout messages handled by Spring Security -->
        <c:if test="${param.error != null}">
            <div class="alert alert-danger">
                Invalid email or password.
            </div>
        </c:if>

        <c:if test="${param.logout != null}">
            <div class="alert alert-success">
                You have been logged out.
            </div>
        </c:if>

    </form>
	</div>
</body>
</html>