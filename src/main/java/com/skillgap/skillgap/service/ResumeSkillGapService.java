package com.skillgap.skillgap.service;

import com.skillgap.skillgap.ai.NormalizedSkillsResponse;
import com.skillgap.skillgap.ai.ResumeSkillGapResponse;
import com.skillgap.skillgap.dto.LearningRoadmapItem;
import com.skillgap.skillgap.dto.SkillRecommendation;
import com.skillgap.skillgap.entity.JobRole;
import com.skillgap.skillgap.entity.LearningResource;
import com.skillgap.skillgap.entity.Skill;
import com.skillgap.skillgap.repository.JobRoleRepository;
import com.skillgap.skillgap.repository.LearningResourceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResumeSkillGapService {

    private final JobRoleRepository jobRoleRepository;
    private final LearningResourceRepository learningResourceRepository;

    public ResumeSkillGapService(
            JobRoleRepository jobRoleRepository,
            LearningResourceRepository learningResourceRepository) {

        this.jobRoleRepository = jobRoleRepository;
        this.learningResourceRepository = learningResourceRepository;
    }

    @Transactional(readOnly = true)
    public ResumeSkillGapResponse calculateResumeSkillGap(
            Long jobRoleId,
            NormalizedSkillsResponse normalizedSkills) {

        JobRole jobRole = jobRoleRepository.findById(jobRoleId)
                .orElseThrow(() ->
                        new RuntimeException("Job role not found"));

        List<String> resumeMatchedSkills =
                normalizedSkills.getMatchedSkills();

        List<String> matchedSkills =
                new ArrayList<>();

        List<String> missingSkills  =
                new ArrayList<>();

        List<SkillRecommendation> recommendations =
                new ArrayList<>();

        List<LearningRoadmapItem> learningRoadmap =
                new ArrayList<>();

        int priority = 1;

        for (Skill requiredSkill : jobRole.getRequiredSkills()) {

            boolean found = resumeMatchedSkills.stream()
                    .anyMatch(skill ->
                            skill.equalsIgnoreCase(
                                    requiredSkill.getName()));

            if (found) {

                matchedSkills.add(
                        requiredSkill.getName());

            } else {

                missingSkills.add(
                        requiredSkill.getName());

                List<LearningResource> resources =
                        learningResourceRepository
                                .findBySkillIdOrderByLearningOrderAsc(
                                        requiredSkill.getId()
                                );

                recommendations.add(
                        new SkillRecommendation(
                                requiredSkill,
                                resources
                        )
                );

                learningRoadmap.add(
                        new LearningRoadmapItem(
                                priority,
                                requiredSkill.getId(),
                                requiredSkill.getName(),
                                null,
                                null,
                                "MISSING"
                        )
                );

                priority++;
            }
        }

        int totalRequiredSkills =
                jobRole.getRequiredSkills().size();

        int matchedSkillsCount =
                matchedSkills.size();

        double matchPercentage = 0.0;

        if (totalRequiredSkills > 0) {

            matchPercentage =
                    ((double) matchedSkillsCount
                            / totalRequiredSkills) * 100;

            matchPercentage =
                    Math.round(
                            matchPercentage * 100.0
                    ) / 100.0;
        }

        return new ResumeSkillGapResponse(
                jobRole.getName(),
                totalRequiredSkills,
                matchedSkillsCount,
                matchPercentage,
                matchedSkills,
                missingSkills,
                normalizedSkills.getOtherExtractedSkills(),
                recommendations,
                learningRoadmap
        );
    }
}