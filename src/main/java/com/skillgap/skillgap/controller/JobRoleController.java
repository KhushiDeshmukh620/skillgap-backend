
package com.skillgap.skillgap.controller;

import com.skillgap.skillgap.dto.JobRoleResponse;
import com.skillgap.skillgap.entity.JobRole;
import com.skillgap.skillgap.service.JobRoleService;
import com.skillgap.skillgap.entity.Skill;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job-roles")
public class JobRoleController {

    private final JobRoleService jobRoleService;

    public JobRoleController(JobRoleService jobRoleService) {
        this.jobRoleService = jobRoleService;
    }

    // Create a new job role
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JobRoleResponse addJobRole(
            @RequestBody JobRole jobRole) {

        JobRole savedRole = jobRoleService.addJobRole(jobRole);

        return toResponse(savedRole);
    }

    // Get all job roles
    @GetMapping
    public List<JobRoleResponse> getAllJobRoles() {

        return jobRoleService.getAllJobRoles();
    }


    // Complete job role details for Admin Dashboard
    @GetMapping("/admin")
    public List<JobRoleResponse> getAdminJobRoles() {

        return jobRoleService.getAdminJobRoles();
    }


    // Get job roles by engineering domain
    @GetMapping("/domain/{domainId}")
    public List<JobRoleResponse> getJobRolesByDomain(
            @PathVariable Long domainId) {

        return jobRoleService.getJobRolesByDomainId(domainId);
    }

    // Update an existing job role
    @PutMapping("/{id}")
    public JobRoleResponse updateJobRole(
            @PathVariable Long id,
            @RequestBody JobRole updatedRole) {

        JobRole savedRole =
                jobRoleService.updateJobRole(id, updatedRole);

        return toResponse(savedRole);
    }

    // Delete a job role
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteJobRole(@PathVariable Long id) {

        jobRoleService.deleteJobRole(id);
    }

    // Assign a required skill to a job role
    @PostMapping("/{jobRoleId}/skills/{skillId}")
    public JobRoleResponse addSkillToJobRole(
            @PathVariable Long jobRoleId,
            @PathVariable Long skillId) {

        JobRole savedRole = jobRoleService.addSkillToJobRole(
                jobRoleId,
                skillId
        );

        return toResponse(savedRole);
    }

    // Convert entity to a simple API response
    private JobRoleResponse toResponse(JobRole jobRole) {

        return new JobRoleResponse(
                jobRole.getId(),
                jobRole.getName()
        );
    }

    // Get all required skills for a specific job role
    @GetMapping("/{jobRoleId}/skills")
    public List<Skill> getRequiredSkills(
            @PathVariable Long jobRoleId) {

        return jobRoleService.getRequiredSkills(jobRoleId);
    }
}
