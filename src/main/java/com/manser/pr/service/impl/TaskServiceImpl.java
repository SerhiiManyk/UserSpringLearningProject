package com.manser.pr.service.impl;

import com.manser.pr.dao.TaskDao;
import com.manser.pr.dao.UserDao;
import com.manser.pr.domain.Task;
import com.manser.pr.domain.User;
import com.manser.pr.service.TaskService;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Objects;

public class TaskServiceImpl implements TaskService {

    private final TaskDao taskDao;
    private final UserDao userDao;

    public TaskServiceImpl(TaskDao taskDao, UserDao userDao) {
        this.taskDao = taskDao;
        this.userDao = userDao;
    }


    @Override
    public List<Task> findAllUserTasks() {
        return List.of();
    }

    @Override
    public List<Task> findAllTasks() {
        return List.of();
    }

    @Override
    public Task createTask(Task task) {
        SecurityContext context = SecurityContextHolder.getContext();
        UserDetails userDetails = (UserDetails) context.getAuthentication().getPrincipal();
        String email = userDetails.getUsername();
        User currentUser = userDao.getByEmail(email);
        task.setOwner(currentUser);

        Objects.requireNonNull(task.getStatus(), "Task status must not be null");
        Objects.requireNonNull(task.getPriority(), "Task priority must not be null");

        taskDao.save(task);
        return task;
    }

    @Override
    public Task updateTask(Task task) {
        return null;
    }

    @Override
    public void deleteTaskById(Long id) {

    }

    @Override
    public Task getTaskForEdit(Long id) {
        return null;
    }
}
