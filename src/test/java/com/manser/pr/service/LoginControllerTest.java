package com.manser.pr.service;

import com.manser.pr.controller.LoginController;
import com.manser.pr.domain.LoginForm;
import com.manser.pr.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoginControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private RedirectAttributes redirectAttributes;

    @InjectMocks
    private LoginController loginController;

    public LoginForm getLoginForm() {
        LoginForm form = new LoginForm();
        form.setEmail("test@mail.com");
        form.setPassword("123456");
        return form;
    }

//    @Test
//    public void shouldReturnLoginViewWhenValidationFails() {
//
//        when(bindingResult.hasErrors()).thenReturn(true);
//
//        String viewName = loginController.loginCheck(getLoginForm(), bindingResult, redirectAttributes);
//
//        Assertions.assertEquals("login", viewName);
//    }

//    @Test
//    public void shouldReturnLoginViewWhenUserNotFound() {
//
//        when(bindingResult.hasErrors()).thenReturn(false);
//        when(userService.loginUser(getLoginForm().getEmail(), getLoginForm().getPassword())).thenReturn(null);
//
//        String viewName = loginController.loginCheck(getLoginForm(), bindingResult, redirectAttributes);
//
//        Assertions.assertEquals("login", viewName);
//        verify(userService).loginUser(getLoginForm().getEmail(), getLoginForm().getPassword());
//        verify(bindingResult).addError(
//                argThat(error ->
//                        error instanceof ObjectError &&
//                                error.getObjectName().equals("loginForm") &&
//                                error.getCode().equals("login.invalid")
//                )
//        );
//    }

//    @Test
//    public void shouldRedirectToUsersWhenLoginSuccessful() {
//
//        when(bindingResult.hasErrors()).thenReturn(false);
//        when(userService.loginUser(getLoginForm().getEmail(), getLoginForm().getPassword())).thenReturn(new User());
//
//        String viewName = loginController.loginCheck(getLoginForm(), bindingResult, redirectAttributes);
//
//        Assertions.assertEquals("redirect:/users", viewName);
//        verify(userService).loginUser(getLoginForm().getEmail(), getLoginForm().getPassword());
//        verify(bindingResult, never()).addError(any());
//    }

}
