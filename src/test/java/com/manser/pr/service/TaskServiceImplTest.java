package com.manser.pr.service;

import com.manser.pr.dao.TaskDao;
import com.manser.pr.dao.UserDao;
import com.manser.pr.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {

    @Mock
    private UserDao userDao;

    @Mock
    private TaskDao taskDao;

    private TaskServiceImpl taskServiceImpl;

    @Test
    public void createTaskShouldCreateTaskAndAssignCurrentUserAsOwner(){
    }

    @Test
    public void createTaskShouldThrowExceptionWhenTaskStatusIsNull(){
    }

    @Test
    public void createTaskShouldThrowExceptionWhenTaskPriorityIsNull(){
    }

    @Test
    public void findAllUserTasksShouldReturnAllTasksForCurrentUser(){
    }

    @Test
    public void findAllUserTasksShouldReturnEmptyListWhenUserHasNoTasks(){
    }

    @Test
    public void findAllTasksShouldReturnAllTasksWhenUserIsAdmin(){
    }

    @Test
    public void findAllTasksShouldThrowAccessDeniedExceptionWhenUserIsNotAdmin(){
    }

    @Test
    public void updateTaskShouldUpdateTaskWhenUserIsOwner(){
    }

    @Test
    public void updateTaskShouldUpdateTaskWhenUserIsAdmin(){
    }

    @Test
    public void updateTaskShouldThrowAccessDeniedExceptionWhenUserIsNotOwnerAndNotAdmin(){
    }

    @Test
    public void updateTaskShouldThrowEntityNotFoundExceptionWhenTaskDoesNotExistAndNotAdmin(){
    }

    @Test
    public void deleteTaskByIdShouldDeleteTaskWhenUserIsOwner(){
    }

    @Test
    public void deleteTaskByIdShouldDeleteTaskWhenUserIsAdmin(){
    }

    @Test
    public void deleteTaskByIdShouldThrowAccessDeniedExceptionWhenUserIsNotOwnerAndNotAdmin(){
    }

    @Test
    public void deleteTaskByIdShouldThrowEntityNotFoundExceptionWhenTaskDoesNotExist(){
    }

    @Test
    public void getTaskForEditShouldReturnTaskForEditWhenUserIsOwner(){
    }

    @Test
    public void getTaskForEditShouldReturnTaskForEditWhenUserIsAdmin(){
    }

    @Test
    public void getTaskForEditShouldThrowAccessDeniedExceptionWhenUserIsNotOwnerAndNotAdmin(){
    }

    @Test
    public void getTaskForEditShouldThrowEntityNotFoundExceptionWhenTaskDoesNotExist(){
    }
}
