
package com.skillgap.skillgap.service;

import com.skillgap.skillgap.dto.JobRoleResponse;
import com.skillgap.skillgap.entity.EngineeringDomain;
import com.skillgap.skillgap.entity.JobRole;
import com.skillgap.skillgap.entity.Skill;

import com.skillgap.skillgap.repository.EngineeringDomainRepository;
import com.skillgap.skillgap.repository.JobRoleRepository;
import com.skillgap.skillgap.repository.SkillRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class JobRoleService {

    private final JobRoleRepository jobRoleRepository;
    private final SkillRepository skillRepository;
    private final EngineeringDomainRepository engineeringDomainRepository;

    public JobRoleService(
            JobRoleRepository jobRoleRepository,
            SkillRepository skillRepository,
            EngineeringDomainRepository engineeringDomainRepository) {

        this.jobRoleRepository = jobRoleRepository;
        this.skillRepository = skillRepository;
        this.engineeringDomainRepository = engineeringDomainRepository;
    }

    // Validate job role name
    private String validateName(String name) {

        if (name == null || name.trim().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Job role name is required."
            );
        }

        String cleanedName = name.trim();

        if (cleanedName.length() > 255) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Job role name is too long."
            );
        }

        return cleanedName;
    }

    // Find engineering domain
    private EngineeringDomain findDomain(Long domainId) {

        if (domainId == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Engineering domain is required."
            );
        }

        return engineeringDomainRepository.findById(domainId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Engineering domain not found."
                        )
                );
    }

    // Find job role
    private JobRole findJobRole(Long id) {

        return jobRoleRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Job role not found."
                        )
                );
    }

    // Create job role
    @Transactional
    public JobRole addJobRole(JobRole jobRole) {

        String name = validateName(jobRole.getName());

        if (jobRoleRepository.existsByNameIgnoreCase(name)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Job role already exists."
            );
        }

        Long domainId = jobRole.getEngineeringDomain() == null
                ? null
                : jobRole.getEngineeringDomain().getId();

        EngineeringDomain domain = findDomain(domainId);

        JobRole newRole = new JobRole();

        newRole.setName(name);
        newRole.setDescription(jobRole.getDescription());
        newRole.setEngineeringDomain(domain);

        return jobRoleRepository.save(newRole);
    }

    // Get all job roles
    @Transactional(readOnly = true)
    public List<JobRoleResponse> getAllJobRoles() {
        return jobRoleRepository.findAllJobRoleResponses();
    }


    // Get complete job role details for Admin Dashboard
    @Transactional(readOnly = true)
    public List<JobRoleResponse> getAdminJobRoles() {

        return jobRoleRepository.findAll().stream()
                .map(jobRole -> {

                    EngineeringDomain domain =
                            jobRole.getEngineeringDomain();

                    return new JobRoleResponse(
                            jobRole.getId(),
                            jobRole.getName(),
                            jobRole.getDescription(),
                            domain != null ? domain.getId() : null,
                            domain != null ? domain.getName() : null
                    );
                })
                .toList();
    }


    // Get roles belonging to a domain
    @Transactional(readOnly = true)
    public List<JobRoleResponse> getJobRolesByDomainId(
            Long domainId) {

        return jobRoleRepository.findJobRolesByDomainId(domainId);
    }

    // Update job role
    @Transactional
    public JobRole updateJobRole(
            Long id,
            JobRole updatedRole) {

        JobRole existingRole = findJobRole(id);

        String name = validateName(updatedRole.getName());

        if (jobRoleRepository.existsByNameIgnoreCaseAndIdNot(
                name, id)) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Another job role already has this name."
            );
        }

        Long domainId = updatedRole.getEngineeringDomain() == null
                ? null
                : updatedRole.getEngineeringDomain().getId();

        EngineeringDomain domain = findDomain(domainId);

        existingRole.setName(name);
        existingRole.setDescription(updatedRole.getDescription());
        existingRole.setEngineeringDomain(domain);

        return jobRoleRepository.save(existingRole);
    }

    // Delete job role safely
    @Transactional
    public void deleteJobRole(Long id) {

        JobRole jobRole = findJobRole(id);

        if (!jobRole.getRequiredSkills().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Cannot delete this job role because it has assigned skills."
            );
        }

        jobRoleRepository.delete(jobRole);
    }

    // Add required skill to a job role
    @Transactional
    public JobRole addSkillToJobRole(
            Long jobRoleId,
            Long skillId) {

        JobRole jobRole = findJobRole(jobRoleId);

        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Skill not found."
                        )
                );

        jobRole.getRequiredSkills().add(skill);

        return jobRoleRepository.save(jobRole);
    }

    // Get required skills associated with a job role
    @Transactional(readOnly = true)
    public List<Skill> getRequiredSkills(Long jobRoleId) {

        JobRole jobRole = findJobRole(jobRoleId);

        return List.copyOf(jobRole.getRequiredSkills());
    }
}
