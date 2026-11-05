package com.skillgap.skillgap.controller;

import com.skillgap.skillgap.dto.UserSkillResponse;
import com.skillgap.skillgap.entity.ProficiencyLevel;
import com.skillgap.skillgap.entity.User;
import com.skillgap.skillgap.service.UserService;
import com.skillgap.skillgap.service.UserSkillService;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-skills")
public class UserSkillController {

    private final UserSkillService userSkillService;
    private final UserService userService;

    public UserSkillController(
            UserSkillService userSkillService,
            UserService userService) {

        this.userSkillService = userSkillService;
        this.userService = userService;
    }

    @PostMapping("/{skillId}/{proficiencyLevel}")
    public UserSkillResponse addUserSkill(
            @PathVariable Long skillId,
            @PathVariable ProficiencyLevel proficiencyLevel,
            Authentication authentication) {

        String email = authentication.getName();

        User loggedInUser =
                userService.getUserByEmail(email);

        return userSkillService.addUserSkill(
                loggedInUser.getId(),
                skillId,
                proficiencyLevel
        );
    }

    @GetMapping
    public List<UserSkillResponse> getUserSkills(
            Authentication authentication) {

        String email = authentication.getName();

        User loggedInUser =
                userService.getUserByEmail(email);

        return userSkillService.getUserSkills(
                loggedInUser.getId()
        );
    }


    @DeleteMapping("/{skillId}")
    @ResponseStatus(org.springframework.http.HttpStatus.NO_CONTENT)
    public void deleteUserSkill(
            @PathVariable Long skillId,
            Authentication authentication) {

        String email = authentication.getName();

        User loggedInUser =
                userService.getUserByEmail(email);

        userSkillService.deleteUserSkill(
                loggedInUser.getId(),
                skillId
        );
    }

}