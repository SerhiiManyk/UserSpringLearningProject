package com.manser.pr.controller;

import com.manser.pr.domain.Task;
import com.manser.pr.exception.TaskAlreadyExistException;
import com.manser.pr.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
        }catch (TaskAlreadyExistException e) {
                result.rejectValue("title", "task.exists", e.getMessage());
                return "taskCreating";
        } catch (IllegalArgumentException e) {
            result.reject("task.invalid", e.getMessage());
            return "taskCreating";
        }
        return "redirect:/taskSuccess";
    }
}
