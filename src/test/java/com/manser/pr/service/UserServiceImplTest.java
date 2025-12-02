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

        Long resultId = userService.save(userForSave);

        assertEquals(10L, resultId);
    }

    @Test
    public void deleteTest(){

        userService.delete(testUser);

        verify(userDao).delete(testUser);
        verify(userDao, times(1)).delete(testUser);
        verifyNoMoreInteractions(userDao);
    }


}
