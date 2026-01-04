package com.manser.pr.controller;

import com.manser.pr.domain.User;
import com.manser.pr.domain.UserRole;
import com.manser.pr.service.UserService;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("roles")
    public UserRole[] roles() {
        return UserRole.values();
    }

    @GetMapping("/users")
    public String listUsers(Model model) {

        model.addAttribute("users", userService.getAll());
        return "userlist";
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
            userService.save(user);
        } catch (ConstraintViolationException e) {
            redirectAttributes.addFlashAttribute("registrationfail", "FALE " + e.getMessage());
            return "redirect:/successfull";
        } catch (Exception j) {
            redirectAttributes.addFlashAttribute("registrationfail", "WRONG REGISTRATION " + j.getMessage());
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
    public String updateUser(@Valid User user,
                             BindingResult result,
                             RedirectAttributes redirectAttributes,
                             Model model) {
        if (result.hasErrors()) {
            model.addAttribute("edit", true);
            return "registration";
        }
        userService.update(user);
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

}
