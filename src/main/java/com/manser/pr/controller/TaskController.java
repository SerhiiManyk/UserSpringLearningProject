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
        model.addAttribute("edit", edit);
        model.addAttribute("statuses", TaskStatus.values());
        model.addAttribute("priorities", Priority.values());
        model.addAttribute("userId", userId);
        model.addAttribute("task", task);
    }

    @GetMapping("/users/{userId}/tasks/new")
    public String newTask(@PathVariable Long userId,
                          Model model,
                          RedirectAttributes redirectAttributes) {

        User currentUser = userService.getCurrentUser();

        boolean isAdmin = currentUser.getUserRole() == UserRole.ADMINISTRATOR;
        boolean isOwner = currentUser.getId().equals(userId);

        if (!isAdmin && !isOwner) {
            redirectAttributes.addFlashAttribute(
                    "alertMessage",
                    "You cannot create tasks for another user."
            );
            return "redirect:/access-denied";
        }
        Task task = new Task();
        task.setOwner(userService.getById(userId));

        prepareTaskForm(model, userId, false,task);
        return "taskCreating";
    }

    @PostMapping("/users/{userId}/tasks")
    public String createTask(@PathVariable Long userId,
                             @Valid Task task,
                             BindingResult result,
                             RedirectAttributes redirectAttributes,
                             Model model) {

        if (result.hasErrors()) {
            prepareTaskForm(model, userId, false,task);
            return "taskCreating";
        }

        task.setOwner(userService.getById(userId));

        try {
            taskService.createTask(task);
            redirectAttributes.addFlashAttribute(
                    "success",
                    "Task created successfully");
        } catch (TaskAlreadyExistException e) {
            result.rejectValue("title", "task.exists", e.getMessage());
            prepareTaskForm(model, userId, false,task);
            return "taskCreating";
        } catch (IllegalArgumentException e) {
            result.reject("task.invalid", e.getMessage());
            prepareTaskForm(model, userId, false,task);
            return "taskCreating";
        }
        return "redirect:/users/" + userId + "/tasks";
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

    @GetMapping("/access-denied")
    public String accessDenied(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute(
                "alertMessage",
                "You do not have permission to perform this action."
        );
        redirectAttributes.addFlashAttribute("backUrl", "/users");
        redirectAttributes.addFlashAttribute("backLabel", "Back to tasks");
        return "redirect:/successfull";
    }

    @GetMapping("/users/{userId}/tasks")
    public String listTasks(@PathVariable Long userId,
                            Model model) {

        model.addAttribute("tasks",
                taskService.findAllUserTasks(userId));
        model.addAttribute("userId", userId);
        return "tasks";
    }
}

