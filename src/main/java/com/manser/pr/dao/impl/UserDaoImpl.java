package com.manser.pr.dao.impl;

import com.manser.pr.dao.UserDao;
import com.manser.pr.domain.SortField;
import com.manser.pr.domain.SortOrder;
import com.manser.pr.domain.User;
import com.manser.pr.exception.UserDeleteException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
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
        try {
            getSession().delete(entity);
        } catch (Exception e) {
            throw new UserDeleteException(
                    "Cannot delete user. It may be used by other records.", e);
        }
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

    @Override
    public User getByEmailAndPassword(String email, String password) {
        List<User> users = getSession()
                .createQuery(
                        "FROM User u WHERE u.email = :email AND u.password = :password",
                        User.class
                )
                .setParameter("email", email)
                .setParameter("password", password)
                .getResultList();

        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public User getByEmail(String email) {
        List<User> users = getSession()
                .createQuery(
                        "FROM User u WHERE u.email = :email",
                        User.class
                )
                .setParameter("email", email)
                .getResultList();

        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public List<User> sortAllUsers(SortField sortField, SortOrder sortOrder) {
        return getSession()
                .createQuery("FROM User u ORDER BY u." + sortField.getDbField() + " " + sortOrder.name(), User.class)
                .getResultList();
    }

    @Override
    public List<User> searchUsers(SortField field, String searchValue) {
        String hql = "FROM User u WHERE u." + field.getDbField() + " LIKE :searchValue ORDER BY u."
                + field.getDbField() + " ASC";
        return getSession()
                .createQuery(hql, User.class)
                .setParameter("searchValue", "%" + searchValue + "%")
                .getResultList();
    }

    public List<User> getAllUsersOrSearchByCriteria(SortField sortField,SortOrder sortOrder, String searchValue){
        String hql = "FROM User u ";

        boolean hasSearch = searchValue != null && !searchValue.isBlank() && sortField != null;

        if(hasSearch){
            hql += " WHERE u." + sortField.getDbField() + " LIKE :searchValue ";
        }
        if (sortField != null) {
            hql += " ORDER BY u." + sortField.getDbField();
            hql += (sortOrder != null ? " " + sortOrder.name() : " ASC");
        }
        Query<User> query= getSession().createQuery(hql, User.class);

        if (hasSearch) {
            query.setParameter("searchValue", "%" + searchValue + "%");
        }

        return query.getResultList();
    }

}
