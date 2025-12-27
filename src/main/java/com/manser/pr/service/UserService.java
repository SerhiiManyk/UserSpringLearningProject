package com.manser.pr.service;

import com.manser.pr.domain.User;

import java.util.List;

public interface UserService extends CrudService<User>{

    List<User> findAllUsers();

    User loginUser(String email, String password);
}
