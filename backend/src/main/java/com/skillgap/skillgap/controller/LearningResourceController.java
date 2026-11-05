
package com.skillgap.skillgap.controller;

import com.skillgap.skillgap.entity.LearningResource;
import com.skillgap.skillgap.service.LearningResourceService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resources")
public class LearningResourceController {

    private final LearningResourceService learningResourceService;

    public LearningResourceController(
            LearningResourceService learningResourceService) {
        this.learningResourceService = learningResourceService;
    }

    // ADD LEARNING RESOURCE
    @PostMapping("/skill/{skillId}")
    public LearningResource addResource(
            @PathVariable Long skillId,
            @RequestBody LearningResource resource) {

        return learningResourceService.addResource(
                skillId, resource);
    }

    // GET LEARNING RESOURCES BY SKILL
    @GetMapping("/skill/{skillId}")
    public List<LearningResource> getResourcesBySkill(
            @PathVariable Long skillId) {

        return learningResourceService
                .getResourcesBySkill(skillId);
    }

    // UPDATE LEARNING RESOURCE
    @PutMapping("/{resourceId}")
    public LearningResource updateResource(
            @PathVariable Long resourceId,
            @RequestBody LearningResource resource) {

        return learningResourceService
                .updateResource(resourceId, resource);
    }

    // DELETE LEARNING RESOURCE
    @DeleteMapping("/{resourceId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteResource(
            @PathVariable Long resourceId) {

        learningResourceService.deleteResource(resourceId);
    }
}
