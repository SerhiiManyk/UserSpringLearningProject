package com.manser.pr.controller;

import com.manser.pr.domain.*;
import com.manser.pr.exception.UserAlreadyExistsException;
import com.manser.pr.service.TaskService;
import com.manser.pr.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class UserController {

    private final UserService userService;
    private final TaskService taskService;

    public UserController(UserService userService, TaskService taskService) {
        this.userService = userService;
        this.taskService = taskService;
    }

    @ModelAttribute("roles")
    public UserRole[] roles() {
        return UserRole.values();
    }

    @GetMapping("/newuser")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("edit", false);
        return "registration";
    }

    @PostMapping("/newuser")
    public String saveUser(@Valid User user,
                           BindingResult result,
                           RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "registration";
        }
        try {
            userService.checkEmailUnique(user);
            userService.save(user);
        } catch (UserAlreadyExistsException e) {
            redirectAttributes.addFlashAttribute("alertMessage", e.getMessage());
            redirectAttributes.addFlashAttribute("backUrl", "/newuser");
            redirectAttributes.addFlashAttribute("backLabel", "Back to registration");
            return "redirect:/successfull";
        } catch (Exception j) {
            redirectAttributes.addFlashAttribute(
                    "alertMessage",
                    "Registration failed: " + j.getMessage()
            );
            redirectAttributes.addFlashAttribute("backUrl", "/newuser");
            redirectAttributes.addFlashAttribute("backLabel", "Back");
            return "redirect:/successfull";
        }
        redirectAttributes.addFlashAttribute("success", "User " + user.getName() + " registered successfully");
        return "redirect:/registrationsuccess";
    }

    @GetMapping("/edit-user-{id}")
    public String editUser(@PathVariable Long id, Model model) {
        User user = userService.getById(id);
        if (user == null) {
            return "redirect:/users";
        }
        model.addAttribute("user", user);
        model.addAttribute("edit", true);
        return "registration";
    }

    @PostMapping("/edit-user-{id}")
    public String updateUser(@PathVariable Long id,
                             @Valid User user,
                             BindingResult result,
                             RedirectAttributes redirectAttributes,
                             Model model) {
        if (result.hasErrors()) {
            model.addAttribute("edit", true);
            return "registration";
        }
        user.setId(id);
        try {
            userService.checkEmailUnique(user);
            userService.update(user);
        } catch (UserAlreadyExistsException e) {
            redirectAttributes.addFlashAttribute("alertMessage", e.getMessage());
            redirectAttributes.addFlashAttribute("backUrl", "/edit-user-" + user.getId());
            redirectAttributes.addFlashAttribute("backLabel", "Back to edit");
            return "redirect:/successfull";
        } catch (Exception j) {
            redirectAttributes.addFlashAttribute(
                    "alertMessage",
                    "Update failed: " + j.getMessage()
            );
            redirectAttributes.addFlashAttribute("backUrl", "/users");
            redirectAttributes.addFlashAttribute("backLabel", "Back to users");
            return "redirect:/successfull";
        }
        redirectAttributes.addFlashAttribute("success", "User " + user.getName() + " updated successfully");
        return "redirect:/registrationsuccess";
    }

    @PostMapping("/delete-user-{id}")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        User user = userService.getById(id);

        if (user != null) {
            userService.delete(user);
            redirectAttributes.addFlashAttribute("success", "User " + user.getName() + " deleted successfully");
        }
        return "redirect:/users";
    }

    @GetMapping("/registrationsuccess")
    public String successPage() {
        return "registrationsuccess";
    }

    @GetMapping("/successfull")
    public String successFullPage() {
        return "successfull";
    }

    @GetMapping("/users")
    public String sortedListUsers(SortField sortField,
                                  SortOrder sortOrder,
                                  Model model,
                                  RedirectAttributes redirectAttributes) {
        try {
            List<User> users = (sortField != null)
                    ? userService.getAllSorted(sortField, sortOrder)
                    : userService.getAll();

            for (User user : users) {
                boolean overdue = false;
                boolean dueSoon = false;

                if (user.getTasks() != null) {
                    for (Task task : user.getTasks()) {
                        if (task.isOverdue()) {
                            overdue = true;
                        } else if (task.isDueSoon()) {
                            dueSoon = true;
                        }
                    }
                }

                user.setHasOverdueTasks(overdue);
                user.setHasDueSoonTasks(dueSoon);
            }

            model.addAttribute("users", users);
            model.addAttribute("taskCounts", buildTaskCountMap());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(
                    "alertMessage",
                    "Unable to load users: " + e.getMessage()
            );
            redirectAttributes.addFlashAttribute("backUrl", "/");
            redirectAttributes.addFlashAttribute("backLabel", "Home");
            return "redirect:/successfull";
        }
        return "userlist";
    }

    @GetMapping("/users/search")
    public String searchingUsersList(
            SortField sortField,
            String searchValue,
            Model model) {
        try {
            List<User> resultList = userService.getSearchResult(sortField, searchValue);

            model.addAttribute("users", resultList);
            model.addAttribute("taskCounts", buildTaskCountMap());

            if (resultList.isEmpty()) {
                model.addAttribute("infoMessage", "No results found");
            }
        } catch (IllegalArgumentException e) {
            model.addAttribute("users", List.of());
            model.addAttribute("infoMessage", e.getMessage());
        }
        return "userlist";
    }

    private Map<Long, Long> buildTaskCountMap() {
        List<Object[]> counts = taskService.countTasksGroupedByOwner();
        return counts.stream()
                .collect(Collectors.toMap(
                        row -> ((User) row[0]).getId(),
                        row -> (Long) row[1]
                ));
    }

}
