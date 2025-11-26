<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
   <title>Users List</title>
   <link href="<c:url value='/resources/static/css/bootstrap.css'/>" rel="stylesheet">
   <link href="<c:url value='/resources/static/css/app.css'/>" rel="stylesheet">
</head>

<body>
<div class="generic-container">
    <div class="panel panel-default">
        <div class="panel-heading"><span class="lead">List of Users </span></div>
        <table class="table table-hover">
            <thead>
            <tr>
                <th>ID</th>
                <th>NAME</th>
                <th>EMail</th>
                <th>PASSWORD</th>
                <th>PHONE NUMBER</th>
                <th>USER ROLE</th>
                <th width="100"></th>
                <th width="100"></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${users}" var="user">
                <tr>
                    <td>${user.id}</td>
                    <td>${user.name}</td>
                    <td>${user.email}</td>
                    <td>${user.password}</td>
                    <td>${user.phone}</td>
                    <td>${user.userRole}</td>

                    <td><a href="<c:url value='/edit-user-${user.id}'/>" class="btn btn-success custom-width">edit</a>
                    </td>
                    <td><a href="<c:url value='/delete-user-${user.id}'/>"
                         class="btn btn-danger custom-width">delete</a></td>
                </tr>
           </c:forEach>
           </tbody>
        </table>
    </div>
           <div class="well">
               <a href="<c:url value='/newuser'/>">Add New User</a>
           </div>
</div>
</body>
</html>