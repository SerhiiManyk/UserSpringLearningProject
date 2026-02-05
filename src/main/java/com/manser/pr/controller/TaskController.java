package com.manser.pr.controller;

import com.manser.pr.domain.Task;
import com.manser.pr.domain.User;
import com.manser.pr.exception.TaskAlreadyExistException;
import com.manser.pr.service.TaskService;
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

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/newtask")
    public String newTask(Model model) {
        model.addAttribute("task", new Task());
        model.addAttribute("edit", false);
        return "taskCreating";
    }

    @PostMapping("/newtask")
    public String createTask(@Valid Task task,
                             BindingResult result) {
        if (result.hasErrors()) {
            return "taskCreating";
        }
        try {
            taskService.createTask(task);
        } catch (TaskAlreadyExistException e) {
            result.rejectValue("title", "task.exists");
            return "taskCreating";
        } catch (IllegalArgumentException e) {
            result.reject("task.invalid");
            return "taskCreating";
        }
        return "redirect:/taskSuccess";
    }

    @GetMapping("/edit-task-{id}")
    public String editTask(@PathVariable Long id, Model model) {
        try {
            Task task = taskService.getTaskForEdit(id);
            model.addAttribute("task", task);
            model.addAttribute("edit", true);
            return "taskCreating";
        } catch (
                EntityNotFoundException e) {
            return "redirect:/tasks";
        } catch (
                AccessDeniedException e) {
            return "redirect:/access-denied";
        }
    }

    @PostMapping("/edit-task-{id}")
    public String updateTask(@PathVariable Long id,
                             @Valid Task task,
                             BindingResult result,
                             RedirectAttributes redirectAttributes,
                             Model model) {
        if (result.hasErrors()) {
            model.addAttribute("edit", true);
            return "taskCreating";
        }
        task.setId(id);
        try {
            taskService.updateTask(task);
        } catch (TaskAlreadyExistException e) {
            result.rejectValue("title", "task.exists", e.getMessage());
            model.addAttribute("edit", true);
            return "taskCreating";
        } catch (AccessDeniedException e) {
            return "redirect:/access-denied";
        }
        redirectAttributes.addFlashAttribute(
                "success",
                "Task " + task.getTitle() + " updated successfully"
        );
        return "redirect:/tasks";
    }

    @PostMapping("/delete-task-{id}")
    public String deleteTask(@PathVariable Long id,
                             RedirectAttributes redirectAttributes) {
        try {
            taskService.deleteTaskById(id);
            redirectAttributes.addFlashAttribute(
                    "success",
                    "Task deleted successfully"
            );
        } catch (EntityNotFoundException e) {
            return "redirect:/tasks";
        } catch (AccessDeniedException e) {
            return "redirect:/access-denied";
        }
        return "redirect:/tasks";
    }

    @GetMapping("/access-denied")
    public String accessDenied(Model model) {
        model.addAttribute("message", "You do not have permission to access this page.");
        return "access-denied";
    }
}

