package com.manser.pr.dao.impl;

import com.manser.pr.dao.TaskDao;
import com.manser.pr.domain.Task;
import com.manser.pr.domain.User;
import com.manser.pr.exception.TaskDeleteException;
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
        return getSession()
                .createQuery(
                        "FROM Task t WHERE t.owner = :owner",
                        Task.class
                )
                .setParameter("owner", owner)
                .getResultList();
    }

    @Override
    public boolean existsByTitleAndOwner(String title, User owner) {
        return !getSession()
                .createQuery(
                        "select 1 from Task t where t.owner = :owner and t.title = :title"
                )
                .setParameter("owner", owner)
                .setParameter("title", title)
                .setMaxResults(1)
                .getResultList()
                .isEmpty();
    }

    @Override
    public boolean existsByTitleAndOwnerExcludingId(String title, User owner, Long taskId) {
        Long count = getSession()
                .createQuery(
                        "select count(t) from Task t " +
                                "where t.owner = :owner " +
                                "and t.title = :title " +
                                "and t.id <> :taskId",
                        Long.class
                )
                .setParameter("owner", owner)
                .setParameter("title", title)
                .setParameter("taskId", taskId)
                .uniqueResult();

        return count != null && count > 0;
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
        try {
            getSession().delete(entity);
        } catch (Exception e) {
            throw new TaskDeleteException(
                    "Cannot delete task. It may be used by other records.", e);
        }
    }

    @Override
    public Task getById(Long id) {
        return getSession().get(Task.class, id);
    }

    @Override
    public List<Task> getAll() {
        return getSession()
                .createQuery("FROM Task", Task.class)
                .getResultList();
    }
}
