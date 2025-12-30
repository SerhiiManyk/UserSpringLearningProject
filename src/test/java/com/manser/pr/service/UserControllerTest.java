package com.manser.pr.service;

import com.manser.pr.controller.UserController;
import com.manser.pr.domain.User;
import com.manser.pr.domain.UserRole;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private RedirectAttributes redirectAttributes;

    @InjectMocks
    private UserController userController;

    private Model model;

    @BeforeEach
    void setUp() {
        model = new ExtendedModelMap();
    }

    public User createUser() {
        return new User(
                1L,
                "Petro",
                "petro@mail.com",
                "12345",
                "503-808-557",
                UserRole.REGULAR_USER);
    }

    @Test
    public void shouldReturnUserListView() {
        when(userService.getAll()).thenReturn(List.of(new User(), new User()));

        String viewName = userController.listUsers(model);

        Assertions.assertEquals("userlist", viewName);
        Assertions.assertTrue(model.containsAttribute("users"));
        verify(userService).getAll();
    }

    @Test
    public void shouldAddUsersToModel() {
        List<User> users = List.of(new User(), new User());
        when(userService.getAll()).thenReturn(users);

        userController.listUsers(model);

        Assertions.assertTrue(model.containsAttribute("users"));
        Assertions.assertEquals(users, ((ExtendedModelMap) model).get("users"));
        verify(userService).getAll();
    }

    @Test
    public void shouldReturnRegistrationViewForNewUser() {
        String viewName = userController.newUser(model);

        Assertions.assertEquals("registration", viewName);

        Assertions.assertTrue(model.containsAttribute("user"));
        Assertions.assertInstanceOf(User.class, ((ExtendedModelMap) model).get("user"));

        Assertions.assertTrue(model.containsAttribute("edit"));
        Assertions.assertEquals(false, ((ExtendedModelMap) model).get("edit"));
    }

    @Test
    public void shouldAddEmptyUserToModel() {
        String viewName = userController.newUser(model);

        Assertions.assertEquals("registration", viewName);

        Assertions.assertTrue(model.containsAttribute("user"));
        Assertions.assertNotNull(((ExtendedModelMap) model).get("user"));
        Assertions.assertInstanceOf(User.class, ((ExtendedModelMap) model).get("user"));

        Assertions.assertEquals(false, ((ExtendedModelMap) model).get("edit"));
    }

    @Test
    public void shouldSetEditFlagToFalse() {
        String viewName = userController.newUser(model);

        Assertions.assertTrue(model.containsAttribute("edit"));
        Assertions.assertNotNull(((ExtendedModelMap) model).get("edit"));
        Assertions.assertEquals(false, ((ExtendedModelMap) model).get("edit"));
    }

    @Test
    public void shouldReturnRegistrationViewWhenValidationFails() {

        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = userController.saveUser(createUser(), bindingResult, redirectAttributes);

        Assertions.assertEquals("registration", viewName);
        verifyNoInteractions(redirectAttributes);
        verify(userService, never()).save(any());
    }

    @Test
    public void shouldSaveUserWhenValid() {
        User testUser = createUser();

        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = userController.saveUser(testUser,
                bindingResult,
                redirectAttributes);

        Assertions.assertEquals("redirect:/registrationsuccess", viewName);
        verify(userService).save(testUser);
        verify(userService, times(1)).save(testUser);
        verify(redirectAttributes).addFlashAttribute(eq("success"), eq("User Petro registered successfully"));
    }

    @Test
    public void shouldRedirectToSuccessAfterSaving() {
        User testUser = createUser();

        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = userController.saveUser(testUser,
                bindingResult,
                redirectAttributes);

        Assertions.assertEquals("redirect:/registrationsuccess", viewName);
    }

    @Test
    public void shouldAddSuccessFlashMessage() {
        User testUser = createUser();

        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = userController.saveUser(testUser,
                bindingResult,
                redirectAttributes);

        verify(redirectAttributes).addFlashAttribute(eq("success"), eq("User Petro registered successfully"));
    }

    @Test
    public void shouldReturnRegistrationViewForEditMode() {
        Long userId = 1L;

        User testUser = createUser();

        when(userService.getById(userId)).thenReturn(testUser);

        String viewName = userController.editUser(userId, model);

        Assertions.assertEquals("registration", viewName);
    }

    @Test
    public void shouldAddExistingUserToModel() {
        User testUser = createUser();

        when(userService.getById(1L)).thenReturn(testUser);

        String viewName = userController.editUser(1L, model);

        Assertions.assertEquals("registration", viewName);
        Assertions.assertEquals(testUser, ((ExtendedModelMap) model).get("user"));
        Assertions.assertTrue(model.containsAttribute("user"));
        Assertions.assertTrue(model.containsAttribute("edit"));
        Assertions.assertEquals(true, ((ExtendedModelMap) model).get("edit"));
    }

    @Test
    public void shouldSetEditFlagToTrue() {
        User testUser = createUser();

        when(userService.getById(1L)).thenReturn(testUser);

        String viewName = userController.editUser(1L, model);

        Assertions.assertEquals(true, ((ExtendedModelMap) model).get("edit"));
    }

    @Test
    public void shouldReturnRegistrationViewWhenUpdateValidationFails() {
        User testUser = createUser();

        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = userController.updateUser(testUser, bindingResult, redirectAttributes, model);

        Assertions.assertEquals("registration", viewName);
        verify(userService, never()).update(any());
    }

    @Test
    public void shouldUpdateUserWhenValid() {
        User testUser = createUser();

        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = userController.updateUser(testUser, bindingResult, redirectAttributes, model);

        Assertions.assertEquals("redirect:/registrationsuccess", viewName);
        verify(userService, times(1)).update(testUser);
        verify(redirectAttributes).addFlashAttribute(eq("success"), eq("User Petro updated successfully"));
    }

    @Test
    public void shouldRedirectToSuccessAfterUpdating() {
        User testUser = createUser();

        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = userController.updateUser(testUser, bindingResult, redirectAttributes, model);

        Assertions.assertEquals("redirect:/registrationsuccess", viewName);
        verify(redirectAttributes).addFlashAttribute(eq("success"), eq("User Petro updated successfully"));
    }

    @Test
    public void shouldAddSuccessFlashMessageOnUpdate() {
        User testUser = createUser();

        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = userController.updateUser(testUser, bindingResult, redirectAttributes,model);

        verify(redirectAttributes).addFlashAttribute(eq("success"), eq("User Petro updated successfully"));
    }

    @Test
    public void shouldDeleteExistingUser() {
        User testUser = createUser();

        when(userService.getById(1L)).thenReturn(testUser);

        String viewName = userController.deleteUser(testUser.getId(),redirectAttributes);

        verify(userService).delete(testUser);
        Assertions.assertEquals("redirect:/users", viewName);
    }

    @Test
    public void shouldDoNothingIfUserNotFound() {
        when(userService.getById(1L)).thenReturn(null);

        String viewName = userController.deleteUser(1L, redirectAttributes);

        verify(userService, never()).delete(any());
        Assertions.assertEquals("redirect:/users", viewName);

    }

    @Test
    public void shouldReturnSuccessPageView() {
        String viewName = userController.successPage();

        Assertions.assertEquals("registrationsuccess", viewName);
    }
}
