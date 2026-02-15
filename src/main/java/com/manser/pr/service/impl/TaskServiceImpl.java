package com.manser.pr.service.impl;

import com.manser.pr.dao.TaskDao;
import com.manser.pr.dao.UserDao;
import com.manser.pr.domain.Task;
import com.manser.pr.domain.User;
import com.manser.pr.domain.UserRole;
import com.manser.pr.exception.TaskAlreadyExistException;
import com.manser.pr.service.TaskService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskDao taskDao;
    private final UserDao userDao;

    public TaskServiceImpl(TaskDao taskDao, UserDao userDao) {
        this.taskDao = taskDao;
        this.userDao = userDao;
    }

    private User getCurrentUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        UserDetails userDetails = (UserDetails) context.getAuthentication().getPrincipal();
        String email = userDetails.getUsername();
        User user = userDao.getByEmail(email);
        if (user == null) throw new EntityNotFoundException("User not found");
        return user;
    }


    @Override
    @Transactional(readOnly = true)
    public List<Task> findAllUserTasks() {
        User currentUser = getCurrentUser();
        return taskDao.findByOwnerId(currentUser.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> findAllTasks() {
        User currentUser = getCurrentUser();
        if (currentUser.getUserRole() == UserRole.ADMINISTRATOR) {
            return taskDao.getAll();
        } else {
            throw new AccessDeniedException("Only administrators can view tasks");
        }
    }

    @Override
    public Task createTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task must not be null");
        }
        if (task.getTitle() == null || task.getTitle().isBlank()) {
            throw new IllegalArgumentException("Task title must not be null or blank");
        }
        if (task.getStatus() == null) {
            throw new IllegalArgumentException("Task status must not be null");
        }
        if (task.getPriority() == null) {
            throw new IllegalArgumentException("Task priority must not be null");
        }

        User currentUser = getCurrentUser();

        if (taskDao.existsByTitleAndOwner(task.getTitle(), currentUser)) {
            throw new TaskAlreadyExistException("Task with this title already exists");
        }
        currentUser.addTask(task);

        return task;
    }

    @Override
    public Task updateTask(Task task) {
        User currentUser = getCurrentUser();

        Task existTask = taskDao.getById(task.getId());
        if (existTask == null) {
            throw new EntityNotFoundException("Task with id " + task.getId() + " does not exist");
        }

        if (!existTask.getOwner().getId().equals(currentUser.getId()) && currentUser.getUserRole() != UserRole.ADMINISTRATOR) {
            throw new AccessDeniedException("You are not allowed to update this task");
        }

        boolean titleExists = taskDao.existsByTitleAndOwnerExcludingId(
                task.getTitle(),
                existTask.getOwner(),
                task.getId()
        );

        if (titleExists) {
            throw new TaskAlreadyExistException("Task with this title already exists");
        }
        existTask.setTitle(task.getTitle());
        existTask.setDescription(task.getDescription());
        existTask.setStatus(task.getStatus());
        existTask.setPriority(task.getPriority());
        existTask.setDeadline(task.getDeadline());

        if (task.getOwner() != null && task.getOwner().getId() != null && !task.getOwner().getId().equals(existTask.getOwner().getId())) {

            User newOwner = userDao.getById(task.getOwner().getId());
            if (newOwner == null) {
                throw new EntityNotFoundException("User with id " + task.getOwner().getId() + " does not exist");
            }
            User oldOwner = existTask.getOwner();
            oldOwner.removeTask(existTask);
            newOwner.addTask(existTask);
        }

        return existTask;
    }

    @Override
    public void deleteTaskById(Long id) {
        User currentUser = getCurrentUser();

        Task task = taskDao.getById(id);
        if (task == null) {
            throw new EntityNotFoundException("Task with id " + id + " does not exist");
        }
        if (task.getOwner().getId().equals(currentUser.getId()) || currentUser.getUserRole() == UserRole.ADMINISTRATOR) {
            taskDao.delete(task);
        } else {
            throw new AccessDeniedException("You are not allowed to delete this task");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Task getTaskForEdit(Long id) {
        User currentUser = getCurrentUser();

        Task task = taskDao.getById(id);
        if (task == null) {
            throw new EntityNotFoundException("Task with id " + id + " does not exist");
        }
        if (task.getOwner().getId().equals(currentUser.getId()) || currentUser.getUserRole() == UserRole.ADMINISTRATOR) {
            return task;
        } else {
            throw new AccessDeniedException("You are not allowed to edit this task");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean taskExistsForCurrentUser(String title) {
        return taskDao.existsByTitleAndOwner(title, getCurrentUser());
    }

    @Override
    @Transactional(readOnly = true)
    public Long countByOwnerId (Long ownerId) {
        if (ownerId == null) {
            throw new IllegalArgumentException("Owner id must not be null");
        }
        return taskDao.countByOwnerId(ownerId);
    }
}
