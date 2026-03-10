<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>
        <c:choose>
            <c:when test="${edit}">Edit Task</c:when>
            <c:otherwise>Create Task</c:otherwise>
        </c:choose>
    </title>

    <link href="<c:url value='/resources/static/css/bootstrap.css'/>" rel="stylesheet">
    <link href="<c:url value='/resources/static/css/app.css'/>" rel="stylesheet">
</head>

<body style="background-color: #e9f2ff;">

<div class="generic-container">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title">
                <c:choose>
                    <c:when test="${edit}">
                        Edit Task for
                    </c:when>
                    <c:otherwise>
                        Create Task for
                    </c:otherwise>
                </c:choose>

                <span class="task-owner">${taskOwner.name}</span>
            </h3>
        </div>

        <div class="panel-body">

            <!-- form action -->
            <c:choose>
                <c:when test="${edit}">
                    <c:url var="formAction"
                           value="/users/${taskOwner.id}/tasks/${task.id}" />
                </c:when>
                <c:otherwise>
                    <c:url var="formAction"
                           value="/users/${userId}/tasks" />
                </c:otherwise>
            </c:choose>

            <form:form method="post"
                       modelAttribute="task"
                       action="${formAction}"
                       cssClass="form-horizontal">

                <input type="hidden"
                       name="${_csrf.parameterName}"
                       value="${_csrf.token}"/>

                <form:hidden path="owner.id"/>

                <c:if test="${currentUser.userRole == 'ADMINISTRATOR'}">
                    <div class="form-group">
                        <label>User</label>
                        <select name="selectedUserId" class="form-control" required>
                            <c:forEach items="${allUsers}" var="user">
                                <option value="${user.id}"
                                        <c:if test="${user.id == userId}">selected</c:if>>
                                    ${user.name} (${user.email})
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                </c:if>

                <!-- TITLE -->
                <div class="form-group">
                    <label class="col-sm-2 control-label">Title</label>
                    <div class="col-sm-6">
                        <form:input path="title" cssClass="form-control"/>
                        <form:errors path="title" cssClass="text-danger"/>
                    </div>
                </div>

                <!-- DESCRIPTION -->
                <div class="form-group">
                    <label class="col-sm-2 control-label">Description</label>
                    <div class="col-sm-6">
                        <form:textarea path="description" cssClass="form-control"/>
                        <form:errors path="description" cssClass="text-danger"/>
                    </div>
                </div>

                <!-- DEADLINE -->
                <div class="form-group">
                    <label class="col-sm-2 control-label">Deadline</label>
                    <div class="col-sm-4">
                        <form:input path="deadline" type="datetime-local" cssClass="form-control"/>
                        <form:errors path="deadline" cssClass="text-danger"/>
                    </div>
                </div>

                <!-- STATUS -->
                <div class="form-group">
                    <label class="col-sm-2 control-label">Status</label>
                    <div class="col-sm-4">
                        <form:select path="status" cssClass="form-control">
                            <form:option value="" label="-- Select status --"/>
                            <form:options items="${statuses}"/>
                        </form:select>
                        <form:errors path="status" cssClass="text-danger"/>
                    </div>
                </div>

                <!-- PRIORITY -->
                <div class="form-group">
                    <label class="col-sm-2 control-label">Priority</label>
                    <div class="col-sm-4">
                        <form:select path="priority" cssClass="form-control">
                            <form:option value="" label="-- Select priority --"/>
                            <form:options items="${priorities}"/>
                        </form:select>
                        <form:errors path="priority" cssClass="text-danger"/>
                    </div>
                </div>

                <!-- SUBMIT BUTTON -->
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-6">
                        <button type="submit" class="btn btn-primary">
                            <c:choose>
                                <c:when test="${edit}">Update</c:when>
                                <c:otherwise>Create</c:otherwise>
                            </c:choose>
                        </button>

                        <a href="<c:url value='/users/${taskOwner.id}/tasks'/>"
                           class="btn btn-default">
                            Cancel
                        </a>
                    </div>
                </div>

            </form:form>

        </div>
    </div>
</div>

</body>
</html>
