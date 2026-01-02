<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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

    <c:if test="${not empty errors and errors.fieldErrorCount == 0}">
        <div class="alert alert-danger">
            <form:errors path="" />
        </div>
    </c:if>

	<form:form method="POST"
	        modelAttribute="loginForm"
	        action="${pageContext.request.contextPath}/login"
	        class="form-horizontal">

            <!-- EMAIL -->
			<div class="row">
    			<div class="form-group col-md-12">
    				<label class="col-md-3 control-label" for="email"> Email</label>
    				<div class="col-md-7">
    					<form:input type="text" path="email" id="email" class="form-control input-sm"/>
    					<div class="has-error">
    						<form:errors path="email" class="help-inline"/>
    					</div>
    				</div>
    			</div>
    		</div>

            <!-- PASSWORD -->
    		<div class="row">
            	<div class="form-group col-md-12">
            		<label class="col-md-3 control-label" for="password"> Password </label>
            			<div class="col-md-7">
            				<form:password path="password" id="password" class="form-control input-sm" />
            					<div class="has-error">
            						<form:errors path="password" class="help-inline"/>
            					</div>
            			</div>
            	</div>
            </div>

            <!-- BUTTONS -->
            <div class="row mb-3">
                <div class="col text-end">

                    <button type="submit" class="btn btn-primary btn-sm">
                        Login
                    </button>

                    <a href="<c:url value='/welcome'/>" class="btn btn-secondary btn-sm">
                        Back
                    </a>

                </div>
            </div>

	</form:form>
	</div>
</body>
</html>