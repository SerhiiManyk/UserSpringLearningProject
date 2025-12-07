package com.manser.pr.service;

import com.manser.pr.controller.UserController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.ui.ExtendedModelMap;

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
    }

    @Test
    public void shouldAddUsersToModel(){
    }

    @Test
    public void shouldReturnRegistrationViewForNewUser(){
    }

    @Test
    public void shouldAddEmptyUserToModel(){
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
