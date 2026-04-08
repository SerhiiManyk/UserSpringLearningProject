package com.manser.pr.service;

import com.manser.pr.dao.UserDao;
import com.manser.pr.domain.SortField;
import com.manser.pr.domain.SortOrder;
import com.manser.pr.domain.User;
import com.manser.pr.domain.UserRole;
import com.manser.pr.exception.UserAlreadyExistsException;
import com.manser.pr.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;


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
                UserRole.REGULAR_USER);
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
                UserRole.REGULAR_USER);

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
                UserRole.REGULAR_USER);

        Long result = userService.save(userForSave);

        assertNotNull(result);
        assertEquals(10L, result);
    }

    @Test
    public void deleteTest(){

        userService.delete(testUser);

        verify(userDao, times(1)).delete(testUser);
        verifyNoMoreInteractions(userDao);
    }

    @Test
    public void getByIdTest(){
        when(userDao.getById(any(Long.class))).thenReturn(testUser);

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
    public void loginUserTest() {

        when(userDao.getByEmailAndPassword("petro@mail.com", "12345"))
                .thenReturn(testUser);

        User result = userService.loginUser("petro@mail.com", "12345");

        assertNotNull(result);
        assertEquals(testUser.getEmail(), result.getEmail());

        verify(userDao).getByEmailAndPassword("petro@mail.com", "12345");
    }

    @Test
    public void getByEmailTest() {

        when(userDao.getByEmail("petro@mail.com")).thenReturn(testUser);

        User result = userService.getByEmail("petro@mail.com");

        assertNotNull(result);
        assertEquals(testUser.getEmail(), result.getEmail());

        verify(userDao).getByEmail("petro@mail.com");
    }

    @Test
    public void checkEmailUniqueShouldThrowException() {

        User existingUser = new User(
                2L,
                "Ivan",
                "petro@mail.com",
                "123",
                "111",
                UserRole.REGULAR_USER
        );

        when(userDao.getByEmail("petro@mail.com")).thenReturn(existingUser);

        assertThrows(
                UserAlreadyExistsException.class,
                () -> userService.checkEmailUnique(testUser)
        );
    }

    @Test
    public void checkEmailUniqueShouldPass() {

        when(userDao.getByEmail("petro@mail.com")).thenReturn(null);

        assertDoesNotThrow(() -> userService.checkEmailUnique(testUser));

        verify(userDao).getByEmail("petro@mail.com");
    }

    @Test
    public void getAllSortedWithNullFieldShouldReturnAll() {

        List<User> users = List.of(testUser);

        when(userDao.getAll()).thenReturn(users);

        List<User> result = userService.getAllSorted(null, SortOrder.ASC);

        assertEquals(1, result.size());

        verify(userDao).getAll();
    }

    @Test
    public void getAllSortedTest() {

        List<User> users = List.of(testUser);

        when(userDao.sortAllUsers(SortField.NAME, SortOrder.ASC))
                .thenReturn(users);

        List<User> result = userService.getAllSorted(SortField.NAME, SortOrder.ASC);

        assertEquals(1, result.size());

        verify(userDao).sortAllUsers(SortField.NAME, SortOrder.ASC);
    }

    @Test
    public void getSearchResultWithEmptyValueShouldReturnAll() {

        List<User> users = List.of(testUser);

        when(userDao.getAll()).thenReturn(users);

        List<User> result = userService.getSearchResult(SortField.NAME, "");

        assertEquals(1, result.size());

        verify(userDao).getAll();
    }

    @Test
    public void searchByRoleTest() {

        List<User> users = List.of(testUser);

        when(userDao.searchUsersByRole(UserRole.REGULAR_USER))
                .thenReturn(users);

        List<User> result =
                userService.getSearchResult(SortField.ROLE, "REGULAR_USER");

        assertEquals(1, result.size());

        verify(userDao).searchUsersByRole(UserRole.REGULAR_USER);
    }

    @Test
    public void searchByRoleInvalidValueShouldThrowException() {

        assertThrows(
                IllegalArgumentException.class,
                () -> userService.getSearchResult(SortField.ROLE, "INVALID_ROLE")
        );
    }

    @Test
    public void getCurrentUserTest() {

        SecurityContext context = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        UserDetails userDetails = mock(UserDetails.class);

        SecurityContextHolder.setContext(context);

        when(context.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("petro@mail.com");

        when(userDao.getByEmail("petro@mail.com")).thenReturn(testUser);

        User result = userService.getCurrentUser();

        assertNotNull(result);
        assertEquals(testUser.getEmail(), result.getEmail());

        verify(userDao).getByEmail("petro@mail.com");
    }
}
