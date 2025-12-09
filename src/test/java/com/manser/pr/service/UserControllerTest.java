package com.manser.pr.service;

import com.manser.pr.controller.UserController;
import com.manser.pr.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.ui.ExtendedModelMap;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private Model model;

    @BeforeEach
    void setUp() {
        model = new ExtendedModelMap();
    }

    @Test
    public void shouldReturnUserListView(){
        when(userService.getAll()).thenReturn(List.of(new User(), new User()));

        String viewName = userController.listUsers(model);

        Assertions.assertEquals("userlist", viewName);
        Assertions.assertTrue(model.containsAttribute("users"));
        verify(userService).getAll();
    }

    @Test
    public void shouldAddUsersToModel(){
        List<User> users = List.of(new User(), new User());
        when(userService.getAll()).thenReturn(users);

        userController.listUsers(model);

        Assertions.assertTrue(model.containsAttribute("users"));
        Assertions.assertEquals(users, ((ExtendedModelMap)model).get("users"));
        verify(userService).getAll();
    }

    @Test
    public void shouldReturnRegistrationViewForNewUser(){
        String viewName = userController.newUser(model);

        Assertions.assertEquals("registration", viewName);

        Assertions.assertTrue(model.containsAttribute("user"));
        Assertions.assertInstanceOf(User.class, ((ExtendedModelMap) model).get("user"));

        Assertions.assertTrue(model.containsAttribute("edit"));
        Assertions.assertEquals(false, ((ExtendedModelMap)model).get("edit"));
    }

    @Test
    public void shouldAddEmptyUserToModel(){
        String viewName = userController.newUser(model);

        Assertions.assertEquals("registration", viewName);

        Assertions.assertTrue(model.containsAttribute("user"));
        Assertions.assertNotEquals(null, ((ExtendedModelMap)model).get("user"));
        Assertions.assertInstanceOf(User.class, ((ExtendedModelMap) model).get("user"));

        Assertions.assertEquals(false, ((ExtendedModelMap)model).get("edit"));
    }

    @Test
    public void shouldSetEditFlagToFalse(){
    }

    @Test
    public void shouldReturnRegistrationViewWhenValidationFails(){
    }

    @Test
    public void shouldSaveUserWhenValid(){
    }

    @Test
    public void shouldRedirectToSuccessAfterSaving() {
    }

    @Test
    public void shouldAddSuccessFlashMessage(){
    }

    @Test
    public void shouldReturnRegistrationViewForEditMode(){
    }

    @Test
    public void shouldAddExistingUserToModel(){
    }

    @Test
    public void shouldSetEditFlagToTrue(){
    }

    @Test
    public void shouldReturnRegistrationViewWhenUpdateValidationFails(){
    }

    @Test
    public void shouldUpdateUserWhenValid(){
    }

    @Test
    public void shouldRedirectToSuccessAfterUpdating(){
    }

    @Test
    public void shouldAddSuccessFlashMessageOnUpdate(){
    }

    @Test
    public void shouldDeleteExistingUser(){
    }

    @Test
    public void shouldRedirectToUsersAfterDelete(){
    }

    @Test
    public void shouldDoNothingIfUserNotFound(){
    }

    @Test
    public void shouldReturnSuccessPageView(){
    }
}
