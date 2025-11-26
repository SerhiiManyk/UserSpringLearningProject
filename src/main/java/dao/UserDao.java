package dao;

import domain.User;

import java.util.List;

public interface UserDao extends CrudDao<User> {

    List<User> findAllUsers();
}
