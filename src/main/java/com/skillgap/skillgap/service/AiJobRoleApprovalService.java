
package com.skillgap.skillgap.service;

import com.skillgap.skillgap.entity.EngineeringDomain;
import com.skillgap.skillgap.entity.JobRole;
import com.skillgap.skillgap.entity.JobRoleSkill;
import com.skillgap.skillgap.entity.ProficiencyLevel;
import com.skillgap.skillgap.entity.Skill;
import com.skillgap.skillgap.entity.LearningResource;
import com.skillgap.skillgap.entity.SkillPriority;

import com.skillgap.skillgap.repository.LearningResourceRepository;
import com.skillgap.skillgap.repository.EngineeringDomainRepository;
import com.skillgap.skillgap.repository.JobRoleRepository;
import com.skillgap.skillgap.repository.JobRoleSkillRepository;
import com.skillgap.skillgap.repository.SkillRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Service
public class AiJobRoleApprovalService {

    private final JobRoleRepository jobRoleRepository;
    private final SkillRepository skillRepository;
    private final EngineeringDomainRepository domainRepository;
    private final JobRoleSkillRepository jobRoleSkillRepository;
    private final LearningResourceRepository learningResourceRepository;

    public AiJobRoleApprovalService(
            JobRoleRepository jobRoleRepository,
            SkillRepository skillRepository,
            EngineeringDomainRepository domainRepository,
            JobRoleSkillRepository jobRoleSkillRepository,
            LearningResourceRepository learningResourceRepository) {

        this.jobRoleRepository = jobRoleRepository;
        this.skillRepository = skillRepository;
        this.domainRepository = domainRepository;
        this.jobRoleSkillRepository = jobRoleSkillRepository;
        this.learningResourceRepository = learningResourceRepository;
    }

    public record ApprovedResource(
            String title,
            String resourceType,
            String resourceUrl
    ) {}

    public record ApprovedSkill(
            String skillName,
            String category,
            ProficiencyLevel requiredLevel,
            SkillPriority priority,
            String relevanceExplanation,
            List<ApprovedResource> learningResources
    ) {}

    public record ApprovalRequest(
            String roleName,
            String description,
            Long engineeringDomainId,
            List<ApprovedSkill> requiredSkills
    ) {}

    public record ApprovalResponse(
            Long jobRoleId,
            String roleName,
            int savedSkills,
            String message
    ) {}

    /*
     * ------------------------------------------------
     * APPROVE NEW AI-GENERATED JOB ROLE
     * ------------------------------------------------
     */

    @Transactional
    public ApprovalResponse approveJobRole(ApprovalRequest request) {

        if (request == null ||
                request.roleName() == null ||
                request.roleName().isBlank()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Job role name is required."
            );
        }

        String roleName = request.roleName().trim();

        if (roleName.length() > 255) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Job role name is too long."
            );
        }

        if (jobRoleRepository.existsByNameIgnoreCase(roleName)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "This job role already exists."
            );
        }

        if (request.engineeringDomainId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Please select an engineering domain."
            );
        }

        EngineeringDomain domain = domainRepository
                .findById(request.engineeringDomainId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Engineering domain not found."
                ));

        if (request.requiredSkills() == null ||
                request.requiredSkills().isEmpty()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "At least one required skill is needed."
            );
        }

        Set<String> uniqueSkillNames = new HashSet<>();

        for (ApprovedSkill item : request.requiredSkills()) {

            if (item == null ||
                    item.skillName() == null ||
                    item.skillName().isBlank() ||
                    item.skillName().trim().length() > 255 ||
                    item.requiredLevel() == null) {

                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Each skill needs a valid name and proficiency level."
                );
            }

            String normalizedName = item.skillName()
                    .trim()
                    .toLowerCase(Locale.ROOT);

            if (!uniqueSkillNames.add(normalizedName)) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Duplicate skill in approval request: "
                                + item.skillName()
                );
            }
        }

        JobRole jobRole = new JobRole();
        jobRole.setName(roleName);
        jobRole.setDescription(
                request.description() == null
                        ? ""
                        : request.description().trim()
        );
        jobRole.setEngineeringDomain(domain);

        JobRole savedRole = jobRoleRepository.save(jobRole);

        int savedCount = 0;

        for (ApprovedSkill item : request.requiredSkills()) {

            String skillName = item.skillName().trim();

            Skill skill = skillRepository
                    .findByNameIgnoreCase(skillName)
                    .orElseGet(() -> {

                        Skill newSkill = new Skill();
                        newSkill.setName(skillName);

                        newSkill.setCategory(
                                item.category() == null ||
                                        item.category().isBlank()
                                        ? "AI Generated"
                                        : item.category().trim()
                        );

                        return skillRepository.save(newSkill);
                    });

            savedRole.getRequiredSkills().add(skill);

            JobRoleSkill requirement = new JobRoleSkill();
            requirement.setJobRole(savedRole);
            requirement.setSkill(skill);
            requirement.setRequiredLevel(item.requiredLevel());

            requirement.setPriority(
                    item.priority() == null
                            ? SkillPriority.IMPORTANT
                            : item.priority()
            );

            requirement.setRelevanceExplanation(
                    item.relevanceExplanation()
            );

            jobRoleSkillRepository.save(requirement);

            saveLearningResources(
                    skill,
                    item.learningResources()
            );

            savedCount++;
        }

        jobRoleRepository.save(savedRole);

        return new ApprovalResponse(
                savedRole.getId(),
                savedRole.getName(),
                savedCount,
                "AI-generated job role approved and saved successfully."
        );
    }

    /*
     * ------------------------------------------------
     * UPDATE EXISTING JOB ROLE
     * ------------------------------------------------
     */

    @Transactional
    public ApprovalResponse updateExistingJobRole(
            Long jobRoleId,
            ApprovalRequest request) {

        if (request == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Update request is required."
            );
        }

        JobRole jobRole = jobRoleRepository
                .findById(jobRoleId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Job role not found."
                ));

        if (request.requiredSkills() == null ||
                request.requiredSkills().isEmpty()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "At least one required skill is needed."
            );
        }

        Set<String> uniqueNames = new HashSet<>();

        for (ApprovedSkill item : request.requiredSkills()) {

            if (item == null ||
                    item.skillName() == null ||
                    item.skillName().isBlank() ||
                    item.skillName().trim().length() > 255 ||
                    item.requiredLevel() == null) {

                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Each skill needs a valid name and proficiency level."
                );
            }

            String normalized = item.skillName()
                    .trim()
                    .toLowerCase(Locale.ROOT);

            if (!uniqueNames.add(normalized)) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Duplicate skill: " + item.skillName()
                );
            }
        }

        // Preserve existing role ID and domain.
        if (request.description() != null) {
            jobRole.setDescription(
                    request.description().trim()
            );
        }

        for (ApprovedSkill item : request.requiredSkills()) {

            String skillName = item.skillName().trim();

            Skill skill = skillRepository
                    .findByNameIgnoreCase(skillName)
                    .orElseGet(() -> {

                        Skill newSkill = new Skill();
                        newSkill.setName(skillName);

                        newSkill.setCategory(
                                item.category() == null ||
                                        item.category().isBlank()
                                        ? "AI Generated"
                                        : item.category().trim()
                        );

                        return skillRepository.save(newSkill);
                    });

            jobRole.getRequiredSkills().add(skill);

            JobRoleSkill requirement =
                    jobRoleSkillRepository
                            .findByJobRoleIdAndSkillId(
                                    jobRoleId,
                                    skill.getId()
                            )
                            .orElseGet(() -> {

                                JobRoleSkill newRequirement =
                                        new JobRoleSkill();

                                newRequirement.setJobRole(jobRole);
                                newRequirement.setSkill(skill);

                                return newRequirement;
                            });

            requirement.setRequiredLevel(
                    item.requiredLevel()
            );

            requirement.setPriority(
                    item.priority() == null
                            ? SkillPriority.IMPORTANT
                            : item.priority()
            );

            requirement.setRelevanceExplanation(
                    item.relevanceExplanation()
            );

            jobRoleSkillRepository.save(requirement);

            saveLearningResources(
                    skill,
                    item.learningResources()
            );
        }

        jobRoleRepository.save(jobRole);

        return new ApprovalResponse(
                jobRole.getId(),
                jobRole.getName(),
                request.requiredSkills().size(),
                "Existing job role skills updated successfully."
        );
    }

    /*
     * ------------------------------------------------
     * SAVE AI-GENERATED LEARNING RESOURCES
     * ------------------------------------------------
     */

    private void saveLearningResources(
            Skill skill,
            List<ApprovedResource> resources) {

        if (resources == null || resources.isEmpty()) {
            return;
        }

        List<LearningResource> existingResources =
                learningResourceRepository
                        .findBySkillIdOrderByLearningOrderAsc(
                                skill.getId()
                        );

        int learningOrder = existingResources.stream()
                .map(LearningResource::getLearningOrder)
                .filter(java.util.Objects::nonNull)
                .max(Integer::compareTo)
                .orElse(0);

        for (ApprovedResource item : resources) {

            if (item == null ||
                    item.title() == null ||
                    item.title().isBlank()) {
                continue;
            }

            String title = item.title().trim();

            boolean alreadyExists = existingResources.stream()
                    .anyMatch(existing ->
                            existing.getTitle() != null &&
                                    existing.getTitle()
                                            .trim()
                                            .equalsIgnoreCase(title)
                    );

            if (alreadyExists) {
                continue;
            }

            String resourceType =
                    item.resourceType() == null ||
                            item.resourceType().isBlank()
                            ? "Tutorial"
                            : item.resourceType().trim();

            String resourceUrl =
                    item.resourceUrl() == null
                            ? ""
                            : item.resourceUrl().trim();

            /*
             * Use shared URL validation.
             *
             * If Qwen provides an invalid or empty URL,
             * create a safe search URL instead.
             */

            if (!LearningResourceUrlValidator.isValid(resourceUrl)) {

                String searchQuery =
                        skill.getName() + " " + title;

                String encodedQuery = URLEncoder.encode(
                        searchQuery,
                        StandardCharsets.UTF_8
                );

                if (resourceType
                        .toLowerCase(Locale.ROOT)
                        .contains("youtube")) {

                    resourceUrl =
                            "https://www.youtube.com/results?search_query="
                                    + encodedQuery;

                } else {

                    resourceUrl =
                            "https://www.google.com/search?q="
                                    + encodedQuery;
                }
            }

            // Final validation before saving.
            resourceUrl =
                    LearningResourceUrlValidator.requireValid(
                            resourceUrl
                    );

            LearningResource resource = new LearningResource();
            resource.setTitle(title);
            resource.setResourceType(resourceType);
            resource.setResourceUrl(resourceUrl);
            resource.setLearningOrder(++learningOrder);
            resource.setSkill(skill);

            learningResourceRepository.save(resource);
            existingResources.add(resource);
        }
    }
}
