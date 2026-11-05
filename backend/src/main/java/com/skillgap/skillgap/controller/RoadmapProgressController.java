
package com.skillgap.skillgap.controller;

import com.skillgap.skillgap.entity.User;
import com.skillgap.skillgap.service.RoadmapProgressService;
import com.skillgap.skillgap.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/roadmap-progress")
public class RoadmapProgressController {

    private final RoadmapProgressService progressService;
    private final UserService userService;

    public RoadmapProgressController(
            RoadmapProgressService progressService,
            UserService userService) {

        this.progressService = progressService;
        this.userService = userService;
    }

    @GetMapping("/{jobRoleId}")
    public ResponseEntity<List<Long>> getProgress(
            @PathVariable Long jobRoleId,
            Authentication authentication) {

        User user = userService.getUserByEmail(
                authentication.getName()
        );

        List<Long> completedSkillIds =
                progressService.getCompletedSkillIds(
                        user.getId(),
                        jobRoleId
                );

        return ResponseEntity.ok(completedSkillIds);
    }

    @PutMapping("/{jobRoleId}/{skillId}")
    public ResponseEntity<Map<String, Object>> updateProgress(
            @PathVariable Long jobRoleId,
            @PathVariable Long skillId,
            @RequestBody ProgressRequest request,
            Authentication authentication) {

        User user = userService.getUserByEmail(
                authentication.getName()
        );

        progressService.updateProgress(
                user.getId(),
                jobRoleId,
                skillId,
                request.completed()
        );

        return ResponseEntity.ok(
                Map.of(
                        "message", "Roadmap progress updated",
                        "skillId", skillId,
                        "completed", request.completed()
                )
        );
    }

    public record ProgressRequest(boolean completed) {
    }
}
