package com.manser.pr.service;

import com.manser.pr.dao.TaskDao;
import com.manser.pr.dao.UserDao;
import com.manser.pr.domain.Priority;
import com.manser.pr.domain.Task;
import com.manser.pr.domain.TaskStatus;
import com.manser.pr.domain.User;
import com.manser.pr.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {

    @Mock
    private UserDao userDao;

    @Mock
    private TaskDao taskDao;

    @InjectMocks
    private TaskServiceImpl taskServiceImpl;

    @AfterEach
    public void clear() {
        SecurityContextHolder.clearContext();
    }

    private User setupSecurityContextForUser(String email){
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn(email);

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);

        User user = new User();
        user.setEmail(email);
        when(userDao.getByEmail(email)).thenReturn(user);

        return user;
    }

    private Task createSampleTask() {
        Task task = new Task();
        task.setTitle("Title");
        task.setDescription("Desc");
        task.setStatus(TaskStatus.NEW);
        task.setPriority(Priority.MEDIUM);
        return task;
    }

    @Test
    public void createTaskShouldCreateTaskAndAssignCurrentUserAsOwner(){
        User currentUser = setupSecurityContextForUser("test@mail.com");
        Task task = createSampleTask();

        Task createdTask = taskServiceImpl.createTask(task);

        assertEquals(currentUser, createdTask.getOwner());
        verify(taskDao).save(createdTask);
    }

    @Test
    public void createTaskShouldThrowExceptionWhenTaskStatusIsNull(){

        Task task = new Task();
        task.setTitle("New Task");
        task.setStatus(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            taskServiceImpl.createTask(task);
        });

        assertEquals("Task status must not be null", exception.getMessage());

        verify(taskDao, never()).save(any());
    }

    @Test
    public void createTaskShouldThrowExceptionWhenTaskPriorityIsNull(){

        Task task = new Task();
        task.setTitle("New Task");
        task.setStatus(TaskStatus.NEW);
        task.setPriority(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            taskServiceImpl.createTask(task);
        });
            assertEquals("Task priority must not be null", exception.getMessage());

        verify(taskDao, never()).save(any());
    }

    @Test
    public void findAllUserTasksShouldReturnAllTasksForCurrentUser(){
    }

    @Test
    public void findAllUserTasksShouldThrowAccessDeniedExceptionWhenUserHasNoTasks(){
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
