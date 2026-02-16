package com.manser.pr.service;

import com.manser.pr.domain.Task;

import java.util.List;

public interface TaskService {

    List<Task> findAllUserTasks();

    List<Task> findAllTasks();

    Task createTask(Task task);

    Task updateTask(Task task);

    void deleteTaskById(Long id);

    Task getTaskForEdit(Long id);

    boolean taskExistsForCurrentUser(String title);

    Long countByOwnerId (Long ownerId);

    public List<Object[]> countTasksGroupedByOwner();

}
