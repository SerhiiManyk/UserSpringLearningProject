<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
   <title>Users List</title>
   <link href="<c:url value='/resources/static/css/bootstrap.css'/>" rel="stylesheet">
   <link href="<c:url value='/resources/static/css/app.css'/>" rel="stylesheet">
</head>

<body style="background-color: #e9f2ff;">
<div class="generic-container">
    <div class="panel panel-default">
        <div class="panel-heading clearfix">
            <h3 class="panel-title pull-left">List of Users</h3>

            <!-- Форма сортування -->
            <form method="get" action="<c:url value='/users'/>" class="pull-right form-inline">
                <!-- Поле для вибору поля сортування -->
                <select name="sortField" class="form-control input-sm">
                    <option value="">-- Sort by --</option>
                    <option value="EMAIL">Email</option>
                    <option value="NAME">Name</option>
                    <option value="PHONE_NUMBER">Phone Number</option>
                    <option value="ROLE">Role</option>
                </select>

                <!-- Поле для вибору напряму сортування -->
                <select name="sortOrder" class="form-control input-sm">
                    <option value="ASC">Ascending</option>
                    <option value="DESC">Descending</option>
                </select>

                <button type="submit" class="btn btn-primary btn-sm">Sort</button>
            </form>
        </div>

            <c:if test="${not empty infoMessage}">
                <div class="alert alert-info" style="margin: 10px;">
                    ${infoMessage}
                </div>
            </c:if>
            
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

                    <td>
                        <button type="button" class="btn btn-danger"
                                data-toggle="modal"
                                data-target="#deleteModal"
                                data-user-id="${user.id}"
                                data-user-name="${user.name}">
                                Delete
                        </button>
                    </td>

                </tr>
           </c:forEach>
           </tbody>
        </table>
    </div>
           <div class="well">
               <a href="<c:url value='/newuser'/>">Add New User</a>

                   <!-- Панель пошуку -->
                   <form method="get" action="<c:url value='/users/search'/>" class="pull-right form-inline">
                       <select name="sortField" class="form-control input-sm">
                           <option value="">-- Search by --</option>
                           <option value="EMAIL">Email</option>
                           <option value="NAME">Name</option>
                           <option value="PHONE_NUMBER">Phone Number</option>
                           <option value="ROLE">Role</option>
                       </select>

                       <input type="text"
                              name="searchValue"
                              class="form-control input-sm"
                              placeholder="Search..." />

                       <button type="submit" class="btn btn-primary btn-sm">
                           Search
                       </button>
                   </form>
           </div>

           <div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="deleteModalLabel" aria-hidden="true">
             <div class="modal-dialog" role="document">
               <div class="modal-content">
                 <form id="deleteForm" method="post">
                     <div class="modal-header">
                       <h5 class="modal-title" id="deleteModalLabel">Confirm Delete</h5>
                       <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                         <span aria-hidden="true">&times;</span>
                       </button>
                     </div>
                     <div class="modal-body">
                       Are you sure you want to delete user <strong id="modalUserName"></strong>?
                     </div>
                     <div class="modal-footer">
                       <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                       <button type="submit" class="btn btn-danger">Delete</button>
                     </div>
                 </form>
               </div>
             </div>
           </div>
</div>

<script src="<c:url value='/resources/static/js/jquery.min.js'/>"></script>
<script src="<c:url value='/resources/static/js/bootstrap.min.js'/>"></script>

<script type="text/javascript">
    var contextPath = '/ambulatory/';

  $('#deleteModal').on('show.bs.modal', function(event) {
      var button = $(event.relatedTarget);
      var userId = button.data('user-id');
      var userName = button.data('user-name');
      var modal = $(this);
      modal.find('#modalUserName').text(userName);
      modal.find('#deleteForm').attr('action', contextPath + 'delete-user-' + userId);
  });
</script>

</body>
</html>