package com.manser.pr.service;

import com.manser.pr.dao.TaskDao;
import com.manser.pr.dao.UserDao;
import com.manser.pr.domain.*;
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

import org.springframework.security.access.AccessDeniedException;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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
        user.setId(1L);
        when(userDao.getByEmail(email)).thenReturn(user);

        return user;
    }

    private Task createSampleTask() {
        Task task = new Task();
        task.setTitle("Title");
        task.setDescription("Desc");
        task.setStatus(TaskStatus.NEW);
        task.setPriority(Priority.MEDIUM);
        task.setId(10L);
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
        User currentUser = setupSecurityContextForUser("test@mail.com");
        List<Task> tasks = new ArrayList<>();
        tasks.add(createSampleTask());
        tasks.add(createSampleTask());

        when(taskDao.findByOwner(currentUser)).thenReturn(tasks);

        List<Task> resultList = taskServiceImpl.findAllUserTasks();

        assertEquals(2, resultList.size());
        assertEquals(tasks, resultList);

        verify(taskDao).findByOwner(currentUser);
    }

    @Test
    public void findAllUserTasksShouldReturnEmptyListWhenUserHasNoTasks() {

        User currentUser = setupSecurityContextForUser("test@mail.com");

        when(taskDao.findByOwner(currentUser)).thenReturn(Collections.emptyList());

        List<Task> result = taskServiceImpl.findAllUserTasks();

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(taskDao).findByOwner(currentUser);
    }

    @Test
    public void findAllTasksShouldReturnAllTasksWhenUserIsAdmin(){
        User currentUser = setupSecurityContextForUser("test@mail.com");
        currentUser.setUserRole(UserRole.ADMINISTRATOR);

        List<Task> tasks = new ArrayList<>();
        tasks.add(createSampleTask());
        tasks.add(createSampleTask());

        when(taskDao.getAll()).thenReturn(tasks);

        List<Task> result = taskServiceImpl.findAllTasks();

        assertEquals(2, result.size());
        verify(taskDao).getAll();
    }

    @Test
    public void findAllTasksShouldThrowAccessDeniedExceptionWhenUserIsNotAdmin(){
        User currentUser = setupSecurityContextForUser("test@mail.com");
        currentUser.setUserRole(UserRole.UN_LOGIN_USER);

        Exception exception = assertThrows(AccessDeniedException.class, () -> {
            taskServiceImpl.findAllTasks();
        });
        assertEquals("Only administrators can view tasks", exception.getMessage());
        verify(taskDao, never()).getAll();
    }

    @Test
    public void updateTaskShouldUpdateTaskWhenUserIsOwner(){
        User currentUser = setupSecurityContextForUser("test@mail.com");
        Task task = createSampleTask();
        task.setOwner(currentUser);

        when(taskDao.getById(1L)).thenReturn(task);
        when(taskDao.update(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Task updatedData = new Task();
        updatedData.setId(1L);
        updatedData.setTitle("Title 2");
        updatedData.setStatus(TaskStatus.IN_PROGRESS);
        updatedData.setPriority(task.getPriority());

        Task result = taskServiceImpl.updateTask(updatedData);

        assertEquals("Title 2", result.getTitle());
        assertEquals(TaskStatus.IN_PROGRESS, result.getStatus());
        assertEquals(currentUser, result.getOwner());

        verify(taskDao).update(task);
    }

    @Test
    public void updateTaskShouldUpdateTaskWhenUserIsAdmin(){
        User currentUser = setupSecurityContextForUser("test@mail.com");
        currentUser.setUserRole(UserRole.ADMINISTRATOR);

        User taskOwner = new User();
        taskOwner.setEmail("owner@mail.com");
        taskOwner.setId(200L);

        Task  task = createSampleTask();
        task.setOwner(taskOwner);

        Task taskToUpdate = createSampleTask();
        taskToUpdate.setId(1L);
        taskToUpdate.setTitle("Title 2");
        taskToUpdate.setStatus(TaskStatus.IN_PROGRESS);

        when(taskDao.getById(1L)).thenReturn(task);
        when(taskDao.update(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Task result = taskServiceImpl.updateTask(taskToUpdate);

        assertEquals(taskOwner, result.getOwner());
        assertEquals("Title 2", result.getTitle());
        assertEquals(TaskStatus.IN_PROGRESS, result.getStatus());
    }

    @Test
    public void updateTaskShouldThrowAccessDeniedExceptionWhenUserIsNotOwnerAndNotAdmin(){
        User currentUser = setupSecurityContextForUser("test@mail.com");
        currentUser.setUserRole(UserRole.REGULAR_USER);

        User taskOwner = new User();
        taskOwner.setId(200L);

        Task  task = createSampleTask();
        task.setOwner(taskOwner);

        Task taskToUpdate = createSampleTask();
        taskToUpdate.setId(1L);

        when(taskDao.getById(1L)).thenReturn(task);
        Exception exception = assertThrows(AccessDeniedException.class, () -> {
            taskServiceImpl.updateTask(taskToUpdate);
        });
        assertEquals("You are not allowed to update this task", exception.getMessage());
        verify(taskDao, never()).update(any(Task.class));
    }

    @Test
    public void deleteTaskByIdShouldDeleteTaskWhenUserIsOwner(){
        User currentUser = setupSecurityContextForUser("test@mail.com");
        currentUser.setUserRole(UserRole.REGULAR_USER);
        currentUser.setId(1L);

        Task  task = createSampleTask();
        task.setId(1L);
        task.setOwner(currentUser);

        when(taskDao.getById(1L)).thenReturn(task);

        taskServiceImpl.deleteTaskById(1L);

        verify(taskDao).delete(task);
    }

    @Test
    public void deleteTaskByIdShouldDeleteTaskWhenUserIsAdmin(){
        User currentUser = setupSecurityContextForUser("test@mail.com");
        currentUser.setUserRole(UserRole.ADMINISTRATOR);
        currentUser.setId(1L);

        User taskOwner = new User();
        taskOwner.setId(2L);

        Task  task = createSampleTask();
        task.setId(1L);
        task.setOwner(taskOwner);

        when(taskDao.getById(1L)).thenReturn(task);

        taskServiceImpl.deleteTaskById(1L);

        verify(taskDao).delete(task);
    }

    @Test
    public void deleteTaskByIdShouldThrowAccessDeniedExceptionWhenUserIsNotOwnerAndNotAdmin(){
        User currentUser = setupSecurityContextForUser("test@mail.com");
        currentUser.setUserRole(UserRole.REGULAR_USER);
        currentUser.setId(1L);

        User taskOwner = new User();
        taskOwner.setId(2L);

        Task  task = createSampleTask();
        task.setId(1L);
        task.setOwner(taskOwner);

        when(taskDao.getById(1L)).thenReturn(task);

        Exception exception = assertThrows(AccessDeniedException.class, () -> {
            taskServiceImpl.deleteTaskById(1L);
        });

        assertEquals("You are not allowed to delete this task", exception.getMessage());
        verify(taskDao, never()).delete(any(Task.class));
    }

    @Test
    public void deleteTaskByIdShouldThrowEntityNotFoundExceptionWhenTaskDoesNotExist(){
        User currentUser = setupSecurityContextForUser("test@mail.com");
        currentUser.setUserRole(UserRole.REGULAR_USER);
        currentUser.setId(1L);

        when(taskDao.getById(200L)).thenThrow(new EntityNotFoundException("Task with id 200 does not exist"));

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> taskServiceImpl.deleteTaskById(200L)
        );
        assertEquals("Task with id 200 does not exist", exception.getMessage());

        verify(taskDao, never()).delete(any(Task.class));
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
