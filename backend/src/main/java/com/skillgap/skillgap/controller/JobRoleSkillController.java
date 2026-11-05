package com.skillgap.skillgap.controller;

import com.skillgap.skillgap.dto.JobRoleSkillResponse;
import com.skillgap.skillgap.entity.JobRoleSkill;
import com.skillgap.skillgap.entity.ProficiencyLevel;
import com.skillgap.skillgap.service.JobRoleSkillService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job-role-skills")
public class JobRoleSkillController {

    private final JobRoleSkillService jobRoleSkillService;

    public JobRoleSkillController(
            JobRoleSkillService jobRoleSkillService) {
        this.jobRoleSkillService = jobRoleSkillService;
    }

    // Add a new requirement or update through the existing POST operation
    @PostMapping("/{jobRoleId}/{skillId}/{requiredLevel}")
    public JobRoleSkillResponse addJobRoleSkill(
            @PathVariable Long jobRoleId,
            @PathVariable Long skillId,
            @PathVariable ProficiencyLevel requiredLevel) {

        JobRoleSkill saved = jobRoleSkillService.addJobRoleSkill(
                jobRoleId,
                skillId,
                requiredLevel
        );

        return toResponse(saved);
    }

    // Edit the proficiency level of an existing requirement
    @PutMapping("/{jobRoleId}/{skillId}/{requiredLevel}")
    public JobRoleSkillResponse updateJobRoleSkill(
            @PathVariable Long jobRoleId,
            @PathVariable Long skillId,
            @PathVariable ProficiencyLevel requiredLevel) {

        JobRoleSkill updated =
                jobRoleSkillService.updateJobRoleSkill(
                        jobRoleId,
                        skillId,
                        requiredLevel
                );

        return toResponse(updated);
    }

    // Get all requirements for a job role
    @GetMapping("/{jobRoleId}")
    public List<JobRoleSkillResponse> getJobRoleSkills(
            @PathVariable Long jobRoleId) {

        return jobRoleSkillService.getJobRoleSkills(jobRoleId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // Remove a requirement
    @DeleteMapping("/{jobRoleId}/{skillId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteJobRoleSkill(
            @PathVariable Long jobRoleId,
            @PathVariable Long skillId) {

        jobRoleSkillService.deleteJobRoleSkill(
                jobRoleId,
                skillId
        );
    }

    private JobRoleSkillResponse toResponse(JobRoleSkill item) {
        return new JobRoleSkillResponse(
                item.getId(),
                item.getJobRole().getId(),
                item.getSkill().getId(),
                item.getSkill().getName(),
                item.getRequiredLevel()
        );
    }
}
