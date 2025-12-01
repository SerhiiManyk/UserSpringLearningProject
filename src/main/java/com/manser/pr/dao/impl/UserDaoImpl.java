package com.manser.pr.dao.impl;

import com.manser.pr.dao.UserDao;
import com.manser.pr.domain.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {

    private final SessionFactory sessionFactory;

    public UserDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public List<User> findAllUsers() {
        return getSession()
                .createQuery("FROM User ORDER BY id", User.class)
                .getResultList();
    }

    @Override
    public Long save(User entity) {
        getSession().save(entity);
        return entity.getId();
    }

    @Override
    public User update(User entity) {
        getSession().update(entity);
        return entity;
    }

    @Override
    public void delete(User entity) {
        getSession().delete(entity);
    }

    @Override
    public User getById(Long id) {
        return getSession().get(User.class, id);
    }

    @Override
    public List<User> getAll() {
        return getSession()
                .createQuery("FROM User", User.class)
                .getResultList();
    }
}
