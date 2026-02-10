<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Tasks List</title>

    <link href="<c:url value='/resources/static/css/bootstrap.css'/>" rel="stylesheet">
    <link href="<c:url value='/resources/static/css/app.css'/>" rel="stylesheet">
</head>

<body style="background-color: #e9f2ff;">

<div class="generic-container">
    <div class="panel panel-default">

        <!-- PANEL HEADER -->
        <div class="panel-heading clearfix">
            <h3 class="panel-title pull-left">List of Tasks</h3>
        </div>

        <!-- PANEL BODY -->
        <div class="panel-body">

            <table class="table table-hover">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>TITLE</th>
                    <th>DESCRIPTION</th>
                    <th>CREATE TIME</th>
                    <th>UPDATE TIME</th>
                    <th>DEADLINE</th>
                    <th>OWNER</th>
                    <th>STATUS</th>
                    <th>PRIORITY</th>
                    <th width="90"></th>
                    <th width="90"></th>
                </tr>
                </thead>

                <tbody>
                <c:if test="${empty tasks}">
                    <tr>
                        <td colspan="11" class="text-center text-muted">
                            No tasks found
                        </td>
                    </tr>
                </c:if>

                <c:forEach items="${tasks}" var="task">
                    <tr>
                        <td>${task.id}</td>
                        <td>${task.title}</td>
                        <td>${task.description}</td>
                        <td>${task.createdAt}</td>
                        <td>${task.updatedAt}</td>
                        <td>${task.deadline}</td>
                        <td>${task.owner.name}</td>
                        <td>${task.status}</td>
                        <td>${task.priority}</td>

                        <!-- EDIT -->
                        <td>
                            <a href="<c:url value='/edit-task-${task.id}'/>"
                               class="btn btn-xs btn-primary">
                                Edit
                            </a>
                        </td>

                        <!-- DELETE (only for authorized users) -->
                        <td>
                            <sec:authorize access="hasRole('ADMIN')">
                                <form action="<c:url value='/delete-task-${task.id}'/>"
                                      method="post"
                                      style="display:inline;">
                                    <button type="submit"
                                            class="btn btn-xs btn-danger"
                                            onclick="return confirm('Delete this task?');">
                                        Delete
                                    </button>
                                </form>
                            </sec:authorize>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

        </div>
    </div>
</div>

</body>
</html>
