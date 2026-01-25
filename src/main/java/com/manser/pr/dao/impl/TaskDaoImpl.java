package com.manser.pr.dao.impl;

import com.manser.pr.dao.TaskDao;
import com.manser.pr.domain.Task;
import com.manser.pr.domain.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class TaskDaoImpl implements TaskDao {

    private final SessionFactory sessionFactory;

    public TaskDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public List<Task> findByOwner(User owner) {
        return List.of();
    }

    @Override
    public Long save(Task entity) {
        getSession().save(entity);
        return entity.getId();
    }

    @Override
    public Task update(Task entity) {
        getSession().update(entity);
        return entity;
    }

    @Override
    public void delete(Task entity) {

    }

    @Override
    public Task getById(Long id) {
        return null;
    }

    @Override
    public List<Task> getAll() {
        return getSession()
                .createQuery("FROM Task", Task.class)
                .getResultList();
    }
}
