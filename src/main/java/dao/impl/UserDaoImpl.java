package dao.impl;

import dao.UserDao;
import domain.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {


    @Override
    public List<User> findAllUsers() {
        return List.of();
    }

    @Override
    public void save(User entity) {

    }

    @Override
    public void update(User entity) {

    }

    @Override
    public void delete(User entity) {

    }

    @Override
    public User getById(Long id) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return List.of();
    }
}
