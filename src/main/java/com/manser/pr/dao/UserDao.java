package com.manser.pr.dao;

import com.manser.pr.domain.User;

import java.util.List;

public interface UserDao extends CrudDao<User> {

    List<User> findAllUsers();

    User getByEmailAndPassword(String email, String password);

    User getByEmail(String email);
}
