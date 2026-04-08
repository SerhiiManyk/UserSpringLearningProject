<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Welcome to User Spring Learning Project</title>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="<c:url value='/resources/static/css/bootstrap.css'/>">
    <link rel="stylesheet" href="<c:url value='/resources/static/css/app.css'/>">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">

    <style>
        /* Hover ефекти для кнопок */
        .btn:hover {
            opacity: 0.9;
            transform: translateY(-2px);
            transition: all 0.2s;
        }
        /* Контейнер з тінню */
        .welcome-card {
            background-color: #f8f9fa;
            padding: 40px 30px;
            border-radius: 10px;
            box-shadow: 0 6px 18px rgba(0,0,0,0.15);
        }
    </style>
</head>

<body style="background-color: #c3e6cb;">

    <div class="container text-center" style="margin-top: 60px;">

        <!-- Заголовок -->
        <div class="welcome-card">
            <h1 class="display-4 mb-4">Welcome to User Spring Learning Project</h1>

            <!-- Логотип -->
            <img src="<c:url value='/resources/static/images/logo.jpeg'/>"
                 class="img-responsive center-block"
                 alt="Logo" style="max-width: 500px; margin-bottom: 30px;">

            <!-- Кнопки Registration та Login -->
            <div class="row">
                <div class="col-xs-12 col-sm-6" style="margin-bottom:10px;">
                    <a href="<c:url value='/newuser'/>" class="btn btn-primary btn-lg btn-block">
                        <i class="bi bi-person-plus"></i> Registration
                    </a>
                </div>
                <div class="col-xs-12 col-sm-6">
                    <a href="<c:url value='/login'/>" class="btn btn-success btn-lg btn-block">
                        <i class="bi bi-box-arrow-in-right"></i> LogIn
                    </a>
                </div>
            </div>
        </div>

    </div>

</body>

</html>