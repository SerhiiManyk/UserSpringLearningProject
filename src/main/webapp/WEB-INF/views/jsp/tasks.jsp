<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Tasks List</title>

    <link href="<c:url value='/resources/static/css/bootstrap.css'/>" rel="stylesheet">
    <link href="<c:url value='/resources/static/css/app.css'/>" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
</head>

<body style="background-color: #e9f2ff;">

<div class="container-fluid">
    <div class="panel panel-default">

        <!-- PANEL HEADER -->
        <div class="panel-heading clearfix">
            <h3>
            <i class="bi bi-list-task"></i>Tasks of
            <span class="task-owner">${taskOwner.name}</span>
            <span class="task-count-badge">${fn:length(tasks)}</span>
            </h3>
        </div>

        <!-- PANEL BODY -->
        <div class="panel-body">

            <div class="table-responsive">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>PRIORITY</th>
                    <th>TITLE</th>
                    <th>DESCRIPTION</th>
                    <th>CREATE TIME</th>
                    <th>UPDATE TIME</th>
                    <th>DEADLINE</th>
                    <th>OWNER</th>
                    <th>STATUS</th>
                    <th></th>
                    <th></th>
                </tr>
                </thead>

                <tbody>
                <c:if test="${empty tasks}">
                    <tr>
                        <td colspan="10" class="text-center text-muted">
                            No tasks found
                        </td>
                    </tr>
                </c:if>

                <c:forEach items="${tasks}" var="task">
                    <tr class="${task.overdue ? 'danger' : (task.dueSoon ? 'warning' : '')}">

                        <td>
                            <span class="priority-dot priority-${task.priority}"></span>
                            <span class="priority-text">${fn:toLowerCase(task.priority)}</span>
                        </td>
                        <td>${task.title}</td>
                        <td>${task.description}</td>
                        <td>${task.createdAtFormatted}</td>
                        <td>${task.updatedAtFormatted}</td>
                        <td class="${task.overdue ? 'text-danger' : (task.dueSoon ? 'text-warning' : '')}">
                            ${task.deadlineFormatted}
                            <br>
                            <small class="deadline-countdown">${task.deadlineCountdown}</small>
                        </td>
                        <td>
                            <c:out value="${task.owner != null ? task.owner.name : '-'}"/>
                        </td>
                        <td>
                            <span class="status-badge status-${task.status}">
                                ${fn:toLowerCase(task.status)}
                            </span>
                        </td>

                        <!-- EDIT -->
                        <td>
                            <c:if test="${task.owner != null}">
                                <a href="<c:url value='/users/${task.owner.id}/tasks/${task.id}/edit'/>" class="btn btn-xs btn-primary">
                                    <i class="bi bi-pencil-square"></i>Edit
                                </a>
                            </c:if>
                        </td>

                        <!-- DELETE (only for authorized users) -->
                        <td>
                            <c:if test="${task.owner != null and
                                         (pageContext.request.isUserInRole('ADMINISTRATOR')
                                         or pageContext.request.userPrincipal.name == task.owner.email)}">
                                <form action="<c:url value='/users/${task.owner.id}/tasks/${task.id}/delete'/>"
                                      method="post"
                                      style="display:inline;">

                                <input type="hidden"
                                      name="${_csrf.parameterName}"
                                      value="${_csrf.token}" />

                                <button type="submit"
                                      class="btn btn-xs btn-danger"
                                      onclick="return confirm('Delete this task?');">
                                      <i class="bi bi-trash"></i>Delete
                                </button>

                                </form>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            </div>

            <div class="d-flex justify-content-between flex-wrap" style="margin-top:20px;">

                <!-- Back to users -->
                <a href="<c:url value='/users'/>"
                   class="btn btn-default">
                   <i class="bi bi-arrow-return-left"></i>Back to users
                </a>

                <!-- Create new task -->
                <c:choose>

                    <c:when test="${currentUser.userRole != 'ADMINISTRATOR'}">
                        <a href="<c:url value='/users/${userId}/tasks/new'/>"
                           class="btn btn-success">
                           <i class="bi bi-plus-circle"></i>Create new task
                        </a>
                    </c:when>

                    <c:otherwise>
                        <form method="get" action="<c:url value='/users/tasks/new'/>" class="form-inline">
                            <select name="selectedUserId" class="form-control input-sm" required>
                                <c:forEach items="${allUsers}" var="user">
                                    <option value="${user.id}">${user.name} (${user.email})</option>
                                </c:forEach>
                            </select>
                            <button type="submit" class="btn btn-success btn-sm">
                                <i class="bi bi-plus-circle"></i>Create new task
                            </button>
                        </form>
                    </c:otherwise>
                </c:choose>

            </div>

        </div>
    </div>
</div>

</body>
</html>
