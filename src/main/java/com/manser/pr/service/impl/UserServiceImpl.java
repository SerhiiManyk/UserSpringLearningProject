package com.manser.pr.service.impl;

import com.manser.pr.dao.UserDao;
import com.manser.pr.domain.User;
import com.manser.pr.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<User> findAllUsers() {
        return userDao.findAllUsers();
    }

    @Override
    public Long save(User entity) {
        return userDao.save(entity);
    }

    @Override
    public User update(User entity) {
        userDao.update(entity);
        return entity;
    }

    @Override
    public void delete(User entity) {
        userDao.delete(entity);
    }

    @Override
    public User getById(Long id) {
        return userDao.getById(id);
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public User loginUser(String email, String password) {
        return userDao.getByEmailAndPassword(email, password);
    }
}
