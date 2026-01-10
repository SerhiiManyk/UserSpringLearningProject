package com.manser.pr.dao;

import com.manser.pr.domain.SortField;
import com.manser.pr.domain.SortOrder;
import com.manser.pr.domain.User;

import java.util.List;

public interface UserDao extends CrudDao<User> {

    List<User> findAllUsers();

    User getByEmailAndPassword(String email, String password);

    User getByEmail(String email);

    List<User> sortAllUsers(SortField sortField, SortOrder sortOrder);

     List<User> searchUsers(SortField field, String searchValue, SortOrder sortOrder);
}
