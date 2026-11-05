
package com.skillgap.skillgap.service;

import com.skillgap.skillgap.entity.Skill;

import com.skillgap.skillgap.repository.SkillRepository;
import com.skillgap.skillgap.repository.UserSkillRepository;
import com.skillgap.skillgap.repository.JobRoleSkillRepository;
import com.skillgap.skillgap.repository.JobRoleRepository;
import com.skillgap.skillgap.repository.LearningResourceRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class SkillService {

    private final SkillRepository skillRepository;
    private final UserSkillRepository userSkillRepository;
    private final JobRoleSkillRepository jobRoleSkillRepository;
    private final JobRoleRepository jobRoleRepository;
    private final LearningResourceRepository learningResourceRepository;

    public SkillService(
            SkillRepository skillRepository,
            UserSkillRepository userSkillRepository,
            JobRoleSkillRepository jobRoleSkillRepository,
            JobRoleRepository jobRoleRepository,
            LearningResourceRepository learningResourceRepository) {

        this.skillRepository = skillRepository;
        this.userSkillRepository = userSkillRepository;
        this.jobRoleSkillRepository = jobRoleSkillRepository;
        this.jobRoleRepository = jobRoleRepository;
        this.learningResourceRepository = learningResourceRepository;
    }

    // Validate skill name
    private String validateName(String name) {

        if (name == null || name.trim().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Skill name is required."
            );
        }

        String cleanedName = name.trim();

        if (cleanedName.length() > 255) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Skill name is too long."
            );
        }

        return cleanedName;
    }

    // Find skill by ID
    private Skill findSkill(Long id) {

        return skillRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Skill not found."
                        )
                );
    }

    // Add a new skill
    @Transactional
    public Skill addSkill(Skill skill) {

        String name = validateName(skill.getName());

        if (skillRepository.existsByNameIgnoreCase(name)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Skill already exists."
            );
        }

        Skill newSkill = new Skill();

        newSkill.setName(name);

        newSkill.setCategory(
                skill.getCategory() == null
                        ? null
                        : skill.getCategory().trim()
        );

        return skillRepository.save(newSkill);
    }

    // Get all skills
    @Transactional(readOnly = true)
    public List<Skill> getAllSkills() {

        return skillRepository.findAll();
    }

    // Update an existing skill
    @Transactional
    public Skill updateSkill(
            Long id,
            Skill updatedSkill) {

        Skill existingSkill = findSkill(id);

        String name = validateName(updatedSkill.getName());

        if (skillRepository.existsByNameIgnoreCaseAndIdNot(
                name, id)) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Another skill already has this name."
            );
        }

        existingSkill.setName(name);

        existingSkill.setCategory(
                updatedSkill.getCategory() == null
                        ? null
                        : updatedSkill.getCategory().trim()
        );

        return skillRepository.save(existingSkill);
    }

    // Delete a skill safely
    @Transactional
    public void deleteSkill(Long id) {

        Skill skill = findSkill(id);

        // Check whether users have this skill
        if (userSkillRepository.existsBySkillId(id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Cannot delete: this skill is assigned to users."
            );
        }

        // Check required proficiency levels
        if (jobRoleSkillRepository.existsBySkillId(id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Cannot delete: job roles require this skill."
            );
        }

        // Check job role and skill relationship
        if (jobRoleRepository.existsByRequiredSkills_Id(id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Cannot delete: this skill is linked to a job role."
            );
        }

        // Check learning resources
        if (learningResourceRepository.existsBySkillId(id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Cannot delete: learning resources use this skill."
            );
        }

        // Delete only when no relationships exist
        skillRepository.delete(skill);
    }
}
