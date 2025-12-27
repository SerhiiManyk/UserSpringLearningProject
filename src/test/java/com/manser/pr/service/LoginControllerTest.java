package com.manser.pr.service;

import com.manser.pr.controller.LoginController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    private Model model;

    @BeforeEach
    void setUp() {
        model = new ExtendedModelMap();
    }

    @Test
    public void shouldReturnLoginViewWhenValidationFails() {
        // Arrange
        // симулювати BindingResult.hasErrors() -> true

        // Act
        // виклик loginController.loginCheck(...)

        // Assert
        // перевірити, що viewName = "login"
    }

    @Test
    public void shouldReturnLoginViewWhenUserNotFound() {
        // Arrange
        // симулювати BindingResult.hasErrors() -> false
        // симулювати userService.loginUser(...) -> null

        // Act
        // виклик loginController.loginCheck(...)

        // Assert
        // перевірити, що viewName = "login"
        // перевірити, що в BindingResult додана помилка "Invalid email or password"
    }

    @Test
    public void shouldRedirectToUsersWhenLoginSuccessful() {
        // Arrange
        // симулювати BindingResult.hasErrors() -> false
        // симулювати userService.loginUser(...) -> User

        // Act
        // виклик loginController.loginCheck(...)

        // Assert
        // перевірити, що viewName = "redirect:/users"
    }

    @Test
    public void shouldAddUserToModelWhenValidationFails() {
        // Arrange
        // симулювати BindingResult.hasErrors() -> true

        // Act
        // виклик loginController.loginCheck(...)

        // Assert
        // перевірити, що модель містить loginForm або порожній об’єкт
    }
}
