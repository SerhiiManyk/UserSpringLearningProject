<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>User Registration Form</title>
    <link rel="stylesheet" href="<c:url value='/resources/static/css/bootstrap.css'/>">
    <link rel="stylesheet" href="<c:url value='/resources/static/css/app.css'/>">
</head>

<body>

 	<div class="generic-container">
	<div class="well lead">User Registration Form</div>
 	<form:form method="POST" modelAttribute="user" class="form-horizontal">
		<form:input type="hidden" path="id" id="id"/>

		<div class="row">
			<div class="form-group col-md-12">
				<label class="col-md-3 control-label" for="Name"> Name</label>
				<div class="col-md-7">
					<form:input type="text" path="name" id="name" class="form-control input-sm"/>
					<div class="has-error">
						<form:errors path="name" class="help-inline"/>
					</div>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="form-group col-md-12">
				<label class="col-md-3 control-label" for="email">Email</label>
				<div class="col-md-7">
					<form:input type="text" path="email" id="email" class="form-control input-sm" />
					<div class="has-error">
						<form:errors path="email" class="help-inline"/>
					</div>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="form-group col-md-12">
				<label class="col-md-3 control-label" for="password"> Password </label>
				<div class="col-md-7">
					<form:input type="text" path="password" id="password" class="form-control input-sm" />
					<div class="has-error">
						<form:errors path="password" class="help-inline"/>
					</div>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="form-group col-md-12">
				<label class="col-md-3 control-label" for="phone"> Phone number</label>
				<div class="col-md-7">
					<form:input type="text" path="phone" id="phone" class="form-control input-sm"/>
					<div class="has-error">
						<form:errors path="phone" class="help-inline"/>
					</div>
				</div>
			</div>
		</div>

        <div class="row mb-3">
            <div class="col text-end">
                <c:choose>
                    <c:when test="${edit}">
                        <button type="submit" class="btn btn-primary btn-sm">Update</button>
                        <a href="<c:url value='/users' />" class="btn btn-secondary btn-sm">Cancel</a>
                    </c:when>
                    <c:otherwise>
                        <button type="submit" class="btn btn-primary btn-sm">Register</button>
                        <a href="<c:url value='/welcome' />" class="btn btn-secondary btn-sm">Cancel</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

	</form:form>
	</div>
</body>
</html>