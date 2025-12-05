package com.manser.pr.controller;

import com.manser.pr.domain.User;
import com.manser.pr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    MessageSource messageSource;

    @GetMapping(value = {"/users"})
    public String listUsers(Model model){
        List<User> users = userService.getAll();
        model.addAttribute("users", users);
        return "userlist";
    }
}
