<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Welcome to User Spring Learning Project</title>
    <link rel="stylesheet" href="<c:url value='/resources/static/css/bootstrap.css'/>">
    <link rel="stylesheet" href="<c:url value='/resources/static/css/app.css'/>">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
</head>

<body style="background-color: #c3e6cb;">

     <div class="panel-heading">
          <h1 class="display-4 text-center mb-4">
              Welcome to User Spring Learning Project
          </h1>
     </div>

     <div class="container text-center">
         <img src="<c:url value='/resources/static/images/logo.jpeg'/>"
              class="img-fluid my-4"
              alt="Kalach">
     </div>

     <div style="display: flex; justify-content: center; gap: 10px; margin-top: 20px;">
         <a href="<c:url value='/newuser'/>"
            style="flex: 1; max-width: 150px; padding: 10px; text-align: center; color: white; background-color: #007bff; border-radius: 5px; text-decoration: none;">
             <i class="bi bi-person-plus"></i> Registration
         </a>
         <a href="<c:url value='/login'/>"
            style="flex: 1; max-width: 150px; padding: 10px; text-align: center; color: white; background-color: #28a745; border-radius: 5px; text-decoration: none;">
             <i class="bi bi-box-arrow-in-right"></i> LogIn
         </a>
     </div>

</body>

</html>