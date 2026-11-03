package com.skillgap.skillgap.controller;

import com.skillgap.skillgap.dto.LoginRequest;
import com.skillgap.skillgap.dto.LoginResponse;
import com.skillgap.skillgap.dto.RegisterRequest;
import com.skillgap.skillgap.dto.UserResponse;
import com.skillgap.skillgap.entity.User;
import com.skillgap.skillgap.service.UserService;
import jakarta.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Registration
    @PostMapping
    public UserResponse addUser(
            @Valid @RequestBody RegisterRequest registerRequest) {

        return userService.addUser(registerRequest);
    }

    // Get currently logged-in user's profile
    @GetMapping("/me")
    public UserResponse getCurrentUser(
            Authentication authentication) {

        String email = authentication.getName();

        User loggedInUser =
                userService.getUserByEmail(email);

        return new UserResponse(
                loggedInUser.getId(),
                loggedInUser.getName(),
                loggedInUser.getEmail(),
                loggedInUser.getRole().name()
        );
    }


    // Add skill to currently logged-in user
    @PostMapping("/skills/{skillId}")
    public UserResponse addSkillToUser(
            @PathVariable Long skillId,
            Authentication authentication) {

        String email = authentication.getName();

        User loggedInUser =
                userService.getUserByEmail(email);

        return userService.addSkillToUser(
                loggedInUser.getId(),
                skillId
        );
    }

    // Login
    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody LoginRequest loginRequest) {

        return userService.login(loginRequest);
    }


}