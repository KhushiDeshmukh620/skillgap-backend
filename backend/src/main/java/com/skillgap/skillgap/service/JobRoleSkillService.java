
package com.skillgap.skillgap.service;

import com.skillgap.skillgap.entity.JobRole;
import com.skillgap.skillgap.entity.JobRoleSkill;
import com.skillgap.skillgap.entity.ProficiencyLevel;
import com.skillgap.skillgap.entity.Skill;

import com.skillgap.skillgap.repository.JobRoleRepository;
import com.skillgap.skillgap.repository.JobRoleSkillRepository;
import com.skillgap.skillgap.repository.SkillRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class JobRoleSkillService {

    private final JobRoleSkillRepository jobRoleSkillRepository;
    private final JobRoleRepository jobRoleRepository;
    private final SkillRepository skillRepository;

    public JobRoleSkillService(
            JobRoleSkillRepository jobRoleSkillRepository,
            JobRoleRepository jobRoleRepository,
            SkillRepository skillRepository) {

        this.jobRoleSkillRepository = jobRoleSkillRepository;
        this.jobRoleRepository = jobRoleRepository;
        this.skillRepository = skillRepository;
    }

    // Add or update a required skill and proficiency level
    @Transactional
    public JobRoleSkill addJobRoleSkill(
            Long jobRoleId,
            Long skillId,
            ProficiencyLevel requiredLevel) {

        if (requiredLevel == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Required proficiency level cannot be empty."
            );
        }

        JobRole jobRole = jobRoleRepository.findById(jobRoleId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Job role not found."
                ));

        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Skill not found."
                ));

        // Look for an existing requirement
        JobRoleSkill jobRoleSkill = jobRoleSkillRepository
                .findByJobRoleId(jobRoleId)
                .stream()
                .filter(item ->
                        item.getSkill().getId().equals(skillId))
                .findFirst()
                .orElse(null);

        if (jobRoleSkill == null) {
            // Create a new requirement
            jobRoleSkill = new JobRoleSkill();
            jobRoleSkill.setJobRole(jobRole);
            jobRoleSkill.setSkill(skill);
        }

        // Update the required level
        jobRoleSkill.setRequiredLevel(requiredLevel);

        // Keep the job role's required skills relationship
        // in sync with proficiency requirements
        jobRole.getRequiredSkills().add(skill);

        return jobRoleSkillRepository.save(jobRoleSkill);
    }

// Update an existing skill requirement without deleting it
    @Transactional
    public JobRoleSkill updateJobRoleSkill(
            Long jobRoleId,
            Long skillId,
            ProficiencyLevel requiredLevel) {

        if (requiredLevel == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Required proficiency level cannot be empty."
            );
        }

        JobRoleSkill existingRequirement =
                jobRoleSkillRepository.findByJobRoleId(jobRoleId)
                        .stream()
                        .filter(item ->
                                item.getSkill().getId().equals(skillId))
                        .findFirst()
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Skill requirement not found."
                        ));

        existingRequirement.setRequiredLevel(requiredLevel);

        return jobRoleSkillRepository.save(existingRequirement);
    }


    // View all required skills for a job role
    @Transactional(readOnly = true)
    public List<JobRoleSkill> getJobRoleSkills(Long jobRoleId) {

        if (!jobRoleRepository.existsById(jobRoleId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Job role not found."
            );
        }

        return jobRoleSkillRepository.findByJobRoleId(jobRoleId);
    }

    // Remove a skill requirement from a job role
    @Transactional
    public void deleteJobRoleSkill(
            Long jobRoleId,
            Long skillId) {

        JobRole jobRole = jobRoleRepository.findById(jobRoleId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Job role not found."
                ));

        JobRoleSkill jobRoleSkill = jobRoleSkillRepository
                .findByJobRoleId(jobRoleId)
                .stream()
                .filter(item ->
                        item.getSkill().getId().equals(skillId))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Skill requirement not found."
                ));

        // Remove the proficiency requirement
        jobRoleSkillRepository.delete(jobRoleSkill);

        // Remove the job role-to-skill association too
        jobRole.getRequiredSkills().removeIf(
                skill -> skill.getId().equals(skillId)
        );
    }
}
