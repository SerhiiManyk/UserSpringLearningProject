<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Access Denied</title>

    <link rel="stylesheet" href="<c:url value='/resources/static/css/bootstrap.css'/>">
    <link rel="stylesheet" href="<c:url value='/resources/static/css/app.css'/>">

    <style>
        .access-container {
            margin-top: 100px;
            text-align: center;
        }

        .access-box {
            background-color: #f8d7da;
            border: 1px solid #f5c6cb;
            color: #721c24;
            padding: 40px;
            border-radius: 10px;
            display: inline-block;
            min-width: 400px;
        }

        .forbidden-icon {
            font-size: 80px;
            margin-bottom: 20px;
        }

        .btn-back {
            margin-top: 25px;
            padding: 10px 25px;
            font-size: 16px;
            white-space: normal;
        }
    </style>
</head>

<body style="background-color: #f4f6f9;">

<div class="access-container">
    <div class="access-box">

        <!-- Big forbidden sign -->
        <div class="forbidden-icon">
            🚫
        </div>

        <h2><strong>Access Denied</strong></h2>

        <p>You do not have permission to perform this action.</p>

        <a href="<c:url value='/users' />"
           class="btn btn-danger btn-back">
            Back to User List
        </a>

    </div>
</div>

</body>
</html>