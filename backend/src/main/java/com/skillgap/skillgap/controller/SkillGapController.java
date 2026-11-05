package com.skillgap.skillgap.controller;

import com.skillgap.skillgap.dto.SkillGapResponse;
import com.skillgap.skillgap.entity.User;
import com.skillgap.skillgap.service.SkillGapService;
import com.skillgap.skillgap.service.UserService;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/skill-gap")
public class SkillGapController {

    private final SkillGapService skillGapService;
    private final UserService userService;

    public SkillGapController(
            SkillGapService skillGapService,
            UserService userService) {

        this.skillGapService = skillGapService;
        this.userService = userService;
    }

    @GetMapping("/{jobRoleId}")
    public SkillGapResponse getSkillGap(
            @PathVariable Long jobRoleId,
            Authentication authentication) {

        String email = authentication.getName();

        User loggedInUser =
                userService.getUserByEmail(email);

        return skillGapService.calculateSkillGap(
                loggedInUser.getId(),
                jobRoleId
        );
    }
}