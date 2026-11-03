
package com.skillgap.skillgap.service;

import com.skillgap.skillgap.dto.LearningRoadmapItem;
import com.skillgap.skillgap.dto.ProficiencyGap;
import com.skillgap.skillgap.dto.SkillGapResponse;
import com.skillgap.skillgap.dto.SkillRecommendation;

import com.skillgap.skillgap.entity.JobRole;
import com.skillgap.skillgap.entity.JobRoleSkill;
import com.skillgap.skillgap.entity.LearningResource;
import com.skillgap.skillgap.entity.Skill;
import com.skillgap.skillgap.entity.User;
import com.skillgap.skillgap.entity.UserSkill;

import com.skillgap.skillgap.repository.JobRoleRepository;
import com.skillgap.skillgap.repository.JobRoleSkillRepository;
import com.skillgap.skillgap.repository.LearningResourceRepository;
import com.skillgap.skillgap.repository.UserRepository;
import com.skillgap.skillgap.repository.UserSkillRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.NoSuchElementException;

@Service
public class SkillGapService {

    private final UserRepository userRepository;
    private final JobRoleRepository jobRoleRepository;
    private final LearningResourceRepository learningResourceRepository;
    private final UserSkillRepository userSkillRepository;
    private final JobRoleSkillRepository jobRoleSkillRepository;

    public SkillGapService(
            UserRepository userRepository,
            JobRoleRepository jobRoleRepository,
            LearningResourceRepository learningResourceRepository,
            UserSkillRepository userSkillRepository,
            JobRoleSkillRepository jobRoleSkillRepository) {

        this.userRepository = userRepository;
        this.jobRoleRepository = jobRoleRepository;
        this.learningResourceRepository = learningResourceRepository;
        this.userSkillRepository = userSkillRepository;
        this.jobRoleSkillRepository = jobRoleSkillRepository;
    }

    @Transactional(readOnly = true)
    public SkillGapResponse calculateSkillGap(
            Long userId,
            Long jobRoleId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new NoSuchElementException("User not found"));

        JobRole jobRole = jobRoleRepository.findById(jobRoleId)
                .orElseThrow(() ->
                        new NoSuchElementException("Job role not found"));

        /*
         * ------------------------------------------------
         * USER PROFICIENCY SKILLS
         * ------------------------------------------------
         *
         * UserSkill is the single source of truth
         * for the user's skills.
         */

        List<UserSkill> userSkillLevels =
                userSkillRepository.findByUserId(userId);

        List<JobRoleSkill> requiredSkillLevels =
                jobRoleSkillRepository.findByJobRoleId(jobRoleId);

        /*
         * ------------------------------------------------
         * BASIC SKILL MATCH
         * ------------------------------------------------
         */

        Set<Skill> requiredSkills =
                jobRole.getRequiredSkills();

        Set<Skill> userSkills =
                new HashSet<>();

        for (UserSkill userSkill : userSkillLevels) {
            userSkills.add(userSkill.getSkill());
        }

        Set<Skill> skillGap =
                new HashSet<>(requiredSkills);

        skillGap.removeAll(userSkills);

        Set<Skill> matchedSkillList =
                new HashSet<>(requiredSkills);

        matchedSkillList.retainAll(userSkills);

        /*
         * ------------------------------------------------
         * PROFICIENCY + RECOMMENDATIONS + ROADMAP
         * ------------------------------------------------
         */

        List<SkillRecommendation> recommendations =
                new ArrayList<>();

        List<ProficiencyGap> proficiencyGaps =
                new ArrayList<>();

        List<LearningRoadmapItem> learningRoadmap =
                new ArrayList<>();

        double totalProficiencyScore = 0;
        double totalRequiredScore = 0;

        for (JobRoleSkill requiredSkill : requiredSkillLevels) {

            int requiredLevelValue =
                    requiredSkill.getRequiredLevel().ordinal() + 1;

            totalRequiredScore += requiredLevelValue;

            UserSkill matchingUserSkill = null;

            for (UserSkill userSkill : userSkillLevels) {

                if (userSkill.getSkill().getId().equals(
                        requiredSkill.getSkill().getId())) {

                    matchingUserSkill = userSkill;
                    break;
                }
            }

            /*
             * Calculate proficiency score
             */

            if (matchingUserSkill != null) {

                int userLevelValue =
                        matchingUserSkill
                                .getProficiencyLevel()
                                .ordinal() + 1;

                totalProficiencyScore += Math.min(
                        userLevelValue,
                        requiredLevelValue
                );
            }

            /*
             * Completely missing skill
             */

            if (matchingUserSkill == null) {

                proficiencyGaps.add(
                        new ProficiencyGap(
                                requiredSkill.getSkill().getId(),
                                requiredSkill.getSkill().getName(),
                                null,
                                requiredSkill.getRequiredLevel(),
                                "MISSING"
                        )
                );

                learningRoadmap.add(
                        new LearningRoadmapItem(
                                0,
                                requiredSkill.getSkill().getId(),
                                requiredSkill.getSkill().getName(),
                                null,
                                requiredSkill.getRequiredLevel(),
                                "MISSING"
                        )
                );

                List<LearningResource> resources =
                        learningResourceRepository
                                .findBySkillIdOrderByLearningOrderAsc(
                                        requiredSkill.getSkill().getId()
                                );

                recommendations.add(
                        new SkillRecommendation(
                                requiredSkill.getSkill(),
                                resources
                        )
                );

                /*
                 * Skill exists but proficiency is too low
                 */

            } else if (
                    matchingUserSkill
                            .getProficiencyLevel()
                            .ordinal()
                            <
                            requiredSkill
                                    .getRequiredLevel()
                                    .ordinal()
            ) {

                proficiencyGaps.add(
                        new ProficiencyGap(
                                requiredSkill.getSkill().getId(),
                                requiredSkill.getSkill().getName(),
                                matchingUserSkill.getProficiencyLevel(),
                                requiredSkill.getRequiredLevel(),
                                "LEVEL_GAP"
                        )
                );

                learningRoadmap.add(
                        new LearningRoadmapItem(
                                0,
                                requiredSkill.getSkill().getId(),
                                requiredSkill.getSkill().getName(),
                                matchingUserSkill.getProficiencyLevel(),
                                requiredSkill.getRequiredLevel(),
                                "LEVEL_GAP"
                        )
                );

                List<LearningResource> resources =
                        learningResourceRepository
                                .findBySkillIdOrderByLearningOrderAsc(
                                        requiredSkill.getSkill().getId()
                                );

                recommendations.add(
                        new SkillRecommendation(
                                requiredSkill.getSkill(),
                                resources
                        )
                );

                /*
                 * Required proficiency achieved
                 */

            } else {

                proficiencyGaps.add(
                        new ProficiencyGap(
                                requiredSkill.getSkill().getId(),
                                requiredSkill.getSkill().getName(),
                                matchingUserSkill.getProficiencyLevel(),
                                requiredSkill.getRequiredLevel(),
                                "MATCHED"
                        )
                );
            }
        }

        /*
         * ------------------------------------------------
         * INTELLIGENT LEARNING ROADMAP ORDER
         * ------------------------------------------------
         *
         * Priority factors:
         *
         * 1. Foundational skills first
         * 2. Skills already started before equally
         *    important missing skills
         * 3. Higher required proficiency
         * 4. Alphabetical tie-breaker
         *
         * This is a rule-based prioritization system.
         */

        learningRoadmap.sort((item1, item2) -> {

            int foundation1 =
                    getFoundationPriority(item1.getSkillName());

            int foundation2 =
                    getFoundationPriority(item2.getSkillName());

            // Foundational skills first
            if (foundation1 != foundation2) {

                return Integer.compare(
                        foundation1,
                        foundation2
                );
            }

            // Prioritize improvement within the same category
            int status1 =
                    "LEVEL_GAP".equals(item1.getStatus()) ? 0 : 1;

            int status2 =
                    "LEVEL_GAP".equals(item2.getStatus()) ? 0 : 1;

            if (status1 != status2) {

                return Integer.compare(
                        status1,
                        status2
                );
            }

            // Higher required proficiency first
            int required1 =
                    item1.getRequiredLevel().ordinal();

            int required2 =
                    item2.getRequiredLevel().ordinal();

            if (required1 != required2) {

                return Integer.compare(
                        required2,
                        required1
                );
            }

            // Consistent alphabetical ordering
            return item1.getSkillName()
                    .compareToIgnoreCase(
                            item2.getSkillName()
                    );
        });

        /*
         * ------------------------------------------------
         * ASSIGN ROADMAP PRIORITY NUMBERS
         * ------------------------------------------------
         */

        for (int i = 0; i < learningRoadmap.size(); i++) {

            learningRoadmap.get(i).setPriority(i + 1);
        }

        /*
         * ------------------------------------------------
         * BASIC MATCH PERCENTAGE
         * ------------------------------------------------
         */

        int totalRequiredSkills =
                requiredSkills.size();

        int missingSkills =
                skillGap.size();

        int matchedSkills =
                matchedSkillList.size();

        double matchPercentage = 0;

        if (totalRequiredSkills > 0) {

            matchPercentage =
                    ((double) matchedSkills /
                            totalRequiredSkills) * 100;

            matchPercentage =
                    Math.round(matchPercentage * 100.0) / 100.0;
        }

        /*
         * ------------------------------------------------
         * PROFICIENCY SCORE
         * ------------------------------------------------
         */

        double proficiencyScore = 0;

        if (totalRequiredScore > 0) {

            proficiencyScore =
                    (totalProficiencyScore /
                            totalRequiredScore) * 100;

            proficiencyScore =
                    Math.round(proficiencyScore * 100.0) / 100.0;
        }

        /*
         * ------------------------------------------------
         * BUILD RESPONSE
         * ------------------------------------------------
         */

        SkillGapResponse response =
                new SkillGapResponse();

        response.setUserName(user.getName());

        response.setTargetRole(jobRole.getName());

        response.setTotalRequiredSkills(totalRequiredSkills);

        response.setMatchedSkills(matchedSkills);

        response.setMissingSkills(missingSkills);

        response.setMatchPercentage(matchPercentage);

        response.setProficiencyScore(proficiencyScore);

        response.setSkillGap(skillGap);

        response.setMatchedSkillList(matchedSkillList);

        response.setRecommendations(recommendations);

        response.setProficiencyGaps(proficiencyGaps);

        response.setLearningRoadmap(learningRoadmap);

        return response;
    }

    /*
     * ------------------------------------------------
     * FOUNDATION PRIORITY HELPER
     * ------------------------------------------------
     *
     * Lower number = earlier learning priority.
     *
     * Priority 1: Foundational skills
     * Priority 2: Core concepts
     * Priority 3: Applied technical skills
     * Priority 4: Advanced operations
     * Priority 5: Other skills
     */

    private int getFoundationPriority(String skillName) {

        if (skillName == null) {
            return 5;
        }

        String name = skillName.toLowerCase(Locale.ROOT);

        /*
         * Priority 1: Foundational programming
         * and mathematical skills
         */

        if (name.equals("python")
                || name.equals("java")
                || name.contains("programming fundamentals")
                || name.contains("statistical analysis")
                || name.contains("operating systems fundamentals")
                || name.contains("tcp/ip")
                || name.contains("version control")) {

            return 1;
        }

        /*
         * Priority 2: Core technical concepts
         */

        if (name.contains("data preprocessing")
                || name.contains("machine learning fundamentals")
                || name.contains("network traffic analysis")
                || name.contains("linux")
                || name.contains("sql fundamentals")) {

            return 2;
        }

        /*
         * Priority 3: Applied technical skills
         */

        if (name.contains("model training")
                || name.contains("deep learning")
                || name.contains("data pipeline")
                || name.contains("log analysis")
                || name.contains("vulnerability scanning")) {

            return 3;
        }

        /*
         * Priority 4: Advanced operations
         */

        if (name.contains("model deployment")
                || name.contains("mlops")
                || name.contains("model monitoring")
                || name.contains("kubernetes")
                || name.contains("docker")) {

            return 4;
        }

        /*
         * Priority 5: Skills without a defined category
         */

        return 5;
    }
}
