<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>User Login Page</title>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="<c:url value='/resources/static/css/bootstrap.css'/>">
    <link rel="stylesheet" href="<c:url value='/resources/static/css/app.css'/>">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">

    <style>
        /* --- Загальні стилі --- */
        body {
            background-color: #c3e6cb;
            font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
        }

        /* --- Контейнер логіну --- */
        .login-card {
            max-width: 450px;
            margin: 80px auto;
            padding: 30px 25px;
            background: #f8f9fa;
            border-radius: 10px;
            box-shadow: 0 6px 18px rgba(0,0,0,0.15);
            opacity: 0;
            transform: translateY(-20px);
            animation: fadeInUp 0.6s forwards;
        }

        /* --- Анімація появи --- */
        @keyframes fadeInUp {
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        .login-card h2 {
            margin-bottom: 25px;
            color: #007bff;
        }

        /* --- Поля вводу --- */
        .form-control:focus {
            border-color: #007bff;
            box-shadow: 0 0 6px rgba(0, 123, 255, 0.4);
        }

        /* --- Кнопки --- */
        .btn i {
            margin-right: 6px;
        }

        .alert {
            margin-top: 15px;
        }

        /* --- Адаптивність --- */
        @media (max-width: 576px) {
            .login-card {
                margin: 40px 15px;
                padding: 25px 15px;
            }
        }
    </style>
</head>

<body>

<div class="login-card text-center">

    <!-- Заголовок -->
    <h2>User Login</h2>

    <!-- Повідомлення про помилку / logout -->
    <c:if test="${param.error != null}">
        <div class="alert alert-danger">Invalid email or password.</div>
    </c:if>

    <c:if test="${param.logout != null}">
        <div class="alert alert-success">You have been logged out.</div>
    </c:if>

    <!-- Форма логування -->
    <form method="post" action="${pageContext.request.contextPath}/login">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

        <div class="form-group">
            <label for="email">Email</label>
            <input type="text" name="email" id="email" class="form-control input-sm" placeholder="Enter your email" required/>
        </div>

        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" name="password" id="password" class="form-control input-sm" placeholder="Enter your password" required/>
        </div>

        <div class="form-group" style="margin-top: 20px;">
            <button type="submit" class="btn btn-primary btn-lg btn-block">
                <i class="bi bi-box-arrow-in-right"></i> Login
            </button>
            <a href="<c:url value='/welcome'/>" class="btn btn-secondary btn-lg btn-block" style="margin-top:10px;">
                <i class="bi bi-arrow-return-left"></i> Back
            </a>
        </div>
    </form>

</div>

</body>
</html>