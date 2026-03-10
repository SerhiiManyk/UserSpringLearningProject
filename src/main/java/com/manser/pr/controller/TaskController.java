package com.manser.pr.controller;

import com.manser.pr.domain.*;
import com.manser.pr.exception.TaskAlreadyExistException;
import com.manser.pr.service.TaskService;
import com.manser.pr.service.UserService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@Controller
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;

    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    private void prepareTaskForm(Model model, Long userId, boolean edit,Task task) {

        User user = userService.getById(userId);

        model.addAttribute("taskOwner", user);
        model.addAttribute("edit", edit);
        model.addAttribute("statuses", TaskStatus.values());
        model.addAttribute("priorities", Priority.values());
        model.addAttribute("userId", userId);
        model.addAttribute("task", task);

        User currentUser = userService.getCurrentUser();
        model.addAttribute("currentUser", currentUser);

        if (currentUser.getUserRole() == UserRole.ADMINISTRATOR) {
            model.addAttribute("allUsers", userService.getAll());
        }
    }

    @GetMapping({"/users/tasks/new", "/users/{userId}/tasks/new"})
    public String createTaskForm(
            @PathVariable(required = false) Long userId,
            @RequestParam(required = false) Long selectedUserId,
            Model model,
            RedirectAttributes redirectAttributes) {

        User currentUser = userService.getCurrentUser();
        User taskOwner;

        if (userId != null) {

            if (!currentUser.getUserRole().equals(UserRole.ADMINISTRATOR) &&
                    !currentUser.getId().equals(userId)) {
                redirectAttributes.addFlashAttribute(
                        "alertMessage",
                        "You cannot create tasks for another user."
                );
                return "redirect:/accessDenied";
            }
            taskOwner = userService.getById(userId);
        } else {

            if (currentUser.getUserRole().equals(UserRole.ADMINISTRATOR) && selectedUserId != null) {
                taskOwner = userService.getById(selectedUserId);
            } else {

                taskOwner = currentUser;
            }
        }

        Task newTask = new Task();
        newTask.setOwner(taskOwner);

        prepareTaskForm(model, taskOwner.getId(), false, newTask);

        return "taskCreating";
    }

    @PostMapping({"/users/tasks", "/users/{userId}/tasks"})
    public String createTask(
            @PathVariable(required = false) Long userId,
            @RequestParam(required = false) Long selectedUserId,
            @Valid Task task,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            Model model) {

        User currentUser = userService.getCurrentUser();
        User taskOwner;

        if (currentUser.getUserRole() == UserRole.ADMINISTRATOR) {

            if (selectedUserId != null) {
                taskOwner = userService.getById(selectedUserId);
            } else if (userId != null) {
                taskOwner = userService.getById(userId);
            } else {
                taskOwner = currentUser;
            }

        } else {

            if (userId != null && !userId.equals(currentUser.getId())) {
                return "redirect:/accessDenied";
            }

            taskOwner = currentUser;
        }

        task.setOwner(taskOwner);

        if (result.hasErrors()) {
            prepareTaskForm(model, taskOwner.getId(), false, task);
            return "taskCreating";
        }

        try {
            taskService.createTask(task);
            redirectAttributes.addFlashAttribute("success", "Task created successfully");
        } catch (TaskAlreadyExistException e) {
            result.rejectValue("title", "task.exists", e.getMessage());
            prepareTaskForm(model, taskOwner.getId(), false, task);
            return "taskCreating";
        } catch (IllegalArgumentException e) {
            result.reject("task.invalid", e.getMessage());
            prepareTaskForm(model, taskOwner.getId(), false, task);
            return "taskCreating";
        }

        return "redirect:/users/" + taskOwner.getId() + "/tasks";
    }

    @GetMapping("/users/{userId}/tasks/{taskId}/edit")
    public String editTask(@PathVariable Long userId,
                           @PathVariable Long taskId,
                           Model model) {
        try {
            Task task = taskService.getTaskForEdit(taskId);
            prepareTaskForm(model, userId, true,task);
            return "taskCreating";
        } catch (EntityNotFoundException e) {
            return "redirect:/users/" + userId + "/tasks";
        } catch (AccessDeniedException e) {
            return "redirect:/access-denied";
        }
    }


    @PostMapping("/users/{userId}/tasks/{taskId}")
    public String updateTask(@PathVariable Long userId,
                             @PathVariable Long taskId,
                             @Valid Task task,
                             BindingResult result,
                             RedirectAttributes redirectAttributes,
                             Model model) {
        task.setId(taskId);
        task.setOwner(userService.getById(userId));

        if (result.hasErrors()) {
            prepareTaskForm(model, userId, true,task);
            return "taskCreating";
        }

        Task updatedTask;
        try {
            updatedTask = taskService.updateTask(task);
        } catch (TaskAlreadyExistException e) {
            result.rejectValue("title", "task.exists", e.getMessage());
            prepareTaskForm(model, userId, true,task);
            return "taskCreating";
        } catch (AccessDeniedException e) {
            return "redirect:/access-denied";
        }
        redirectAttributes.addFlashAttribute(
                "success",
                "Task " + task.getTitle() + " updated successfully"
        );
        return "redirect:/users/" + updatedTask.getOwner().getId() + "/tasks";
    }

    @PostMapping("/users/{userId}/tasks/{taskId}/delete")
    public String deleteTask(@PathVariable Long userId,
                             @PathVariable Long taskId,
                             RedirectAttributes redirectAttributes) {
        Task task;
        try {
            task = taskService.getTaskForEdit(taskId);
            taskService.deleteTaskById(taskId);
            redirectAttributes.addFlashAttribute(
                    "success",
                    "Task deleted successfully"
            );
        } catch (EntityNotFoundException e) {
            return "redirect:/users/" + userId + "/tasks";
        } catch (AccessDeniedException e) {
            return "redirect:/access-denied";
        }
        return "redirect:/users/" + task.getOwner().getId() + "/tasks";
    }

    @GetMapping("/accessDenied")
    public String accessDeniedPage() {
        return "accessDenied";
    }

    @GetMapping("/users/{userId}/tasks")
    public String listTasks(@PathVariable Long userId,
                            Model model) {

        User taskOwner = userService.getById(userId);

        model.addAttribute("tasks",
                taskService.findAllUserTasks(userId));
        model.addAttribute("userId", userId);
        model.addAttribute("taskOwner", taskOwner);
        return "tasks";
    }

    @GetMapping("/users/tasks")
    public String listTasksForAllUsers(Model model) {

        model.addAttribute("tasks",
                taskService.findAllTasks());

        User currentUser = userService.getCurrentUser();
        model.addAttribute("userId", currentUser.getId());

        if (currentUser.getUserRole() == UserRole.ADMINISTRATOR) {
            model.addAttribute("allUsers", userService.getAll());
        }
        return "tasks";
    }

}

