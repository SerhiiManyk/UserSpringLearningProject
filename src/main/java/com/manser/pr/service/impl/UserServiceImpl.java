package com.manser.pr.service.impl;

import com.manser.pr.dao.UserDao;
import com.manser.pr.domain.SortField;
import com.manser.pr.domain.SortOrder;
import com.manser.pr.domain.User;
import com.manser.pr.domain.UserRole;
import com.manser.pr.exception.UserAlreadyExistsException;
import com.manser.pr.service.UserService;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
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

    @Override
    public User getByEmail(String email) {
        return userDao.getByEmail(email);
    }

    @Override
    public void checkEmailUnique(User user) {
        User existingUser = getByEmail(user.getEmail());
        if (existingUser != null && !existingUser.getId().equals(user.getId())) {
            throw new UserAlreadyExistsException(
                    "Email " + user.getEmail() + " is already taken"
            );
        }
    }

    @Override
    public List<User> getAllSorted(SortField sortField, SortOrder sortOrder) {
        if (sortField == null) {
            return userDao.getAll();
        }
        if (sortOrder == null) {
            sortOrder = SortOrder.ASC;
        }
        return userDao.sortAllUsers(sortField, sortOrder);
    }

    @Override
    public List<User> getSearchResult(SortField sortField, String searchValue) {
        if (sortField == null || searchValue == null || searchValue.trim().isEmpty()) {
            return userDao.getAll();
        }
        if (sortField == SortField.ROLE) {
            try {
                return userDao.searchUsersByRole(UserRole.valueOf(searchValue.trim().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid role value: " + searchValue);
            }
        }
        return userDao.searchUsers(sortField, searchValue);
    }

    public User getCurrentUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        UserDetails userDetails = (UserDetails) context.getAuthentication().getPrincipal();
        String email = userDetails.getUsername();
        User user = userDao.getByEmail(email);
        if (user == null) throw new EntityNotFoundException("User not found");
        return user;
    }
}
