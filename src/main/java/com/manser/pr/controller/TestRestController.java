package com.manser.pr.controller;

import com.manser.pr.domain.User;
import com.manser.pr.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restcontroller")
public class TestRestController {

    private final UserService userService;

    public TestRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public User get(@PathVariable Long id) {
        return userService.getById(id);
    }

    @PostMapping
    public ResponseEntity<Long> create( @RequestBody User user) {
        return ResponseEntity
                .status(201)
                .body(userService.save(user));
    }
}
