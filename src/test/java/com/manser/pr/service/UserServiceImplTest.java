package com.manser.pr.service;

import com.manser.pr.dao.UserDao;
import com.manser.pr.domain.User;
import com.manser.pr.domain.UserRole;
import com.manser.pr.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserDao userDao;

    private UserServiceImpl userService;

    private static User testUser;

    @BeforeAll
    public static void prepareTestData() {
        testUser = new User(
                1L,
                "Petro",
                "petro@mail.com",
                "12345",
                "503-808-557",
                UserRole.PATIENT);
    }

    @BeforeEach
    public void init() {
        userService = new UserServiceImpl(userDao);
    }

    @Test
    public void updateTest(){
        when(userDao.getById(any(Long.class))).thenReturn(testUser);
        when(userDao.update(any(User.class))).then(returnsFirstArg());

    }
}
