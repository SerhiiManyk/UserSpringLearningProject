package com.manser.pr.service;

import com.manser.pr.dao.UserDao;
import com.manser.pr.domain.User;
import com.manser.pr.domain.UserRole;
import com.manser.pr.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserDao userDao;

    private UserServiceImpl userService;

    private User testUser;

    @BeforeEach
    public void init() {
        userService = new UserServiceImpl(userDao);

        testUser = new User(
                1L,
                "Petro",
                "petro@mail.com",
                "12345",
                "503-808-557",
                UserRole.PATIENT);
    }

    @Test
    public void updateTest(){
        when(userDao.update(any(User.class))).then(returnsFirstArg());

        User userForUpdate = new User(
                testUser.getId(),
                "Ivan",
                "ivan@mail.com",
                "67890",
                "111-222-333",
                UserRole.PATIENT);

        User result = userService.update(userForUpdate);

        assertNotNull(result);
        assertEquals("Ivan", result.getName());
        assertEquals("ivan@mail.com", result.getEmail());
        assertEquals("67890", result.getPassword());
    }

    @Test
    public void saveTest(){
        when(userDao.save(any(User.class))).thenReturn(10L);

        User userForSave = new User(
                null,
                "Ivan",
                "ivan@mail.com",
                "67890",
                "111-222-333",
                UserRole.PATIENT);

        assertEquals(10L, userService.save(userForSave));
    }

    @Test
    public void deleteTest(){

        userService.delete(testUser);

        verify(userDao).delete(testUser);
        verify(userDao, times(1)).delete(testUser);
        verifyNoMoreInteractions(userDao);
    }

    @Test
    public void getByIdTest(){
        when(userService.getById(any(Long.class))).thenReturn(testUser);

        Long testId = testUser.getId();

        User result = userService.getById(testId);

        assertNotNull(result);
        assertEquals(testUser.getId(), result.getId());
        assertEquals(testUser.getName(), result.getName());
        assertEquals(testUser.getEmail(), result.getEmail());

        verify(userDao, times(1)).getById(testId);
    }

    @Test
    public void getAllTest() {
        List<User> users = List.of(testUser);

        when(userDao.getAll()).thenReturn(users);

        List<User> result = userService.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testUser.getId(), result.get(0).getId());

        verify(userDao, times(1)).getAll();
    }

    @Test
    public void findAllUsersTest(){
        List<User> users = List.of(testUser);

        when(userDao.findAllUsers()).thenReturn(users);

        List<User> result = userService.findAllUsers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testUser, result.get(0));

        verify(userDao, times(1)).findAllUsers();
    }


}
