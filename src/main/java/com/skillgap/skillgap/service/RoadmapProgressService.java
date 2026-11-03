
package com.skillgap.skillgap.service;

import com.skillgap.skillgap.entity.JobRole;
import com.skillgap.skillgap.entity.RoadmapProgress;
import com.skillgap.skillgap.entity.Skill;
import com.skillgap.skillgap.entity.User;
import com.skillgap.skillgap.repository.JobRoleRepository;
import com.skillgap.skillgap.repository.RoadmapProgressRepository;
import com.skillgap.skillgap.repository.SkillRepository;
import com.skillgap.skillgap.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoadmapProgressService {

    private final RoadmapProgressRepository progressRepository;
    private final UserRepository userRepository;
    private final JobRoleRepository jobRoleRepository;
    private final SkillRepository skillRepository;

    public RoadmapProgressService(
            RoadmapProgressRepository progressRepository,
            UserRepository userRepository,
            JobRoleRepository jobRoleRepository,
            SkillRepository skillRepository) {

        this.progressRepository = progressRepository;
        this.userRepository = userRepository;
        this.jobRoleRepository = jobRoleRepository;
        this.skillRepository = skillRepository;
    }

    @Transactional(readOnly = true)
    public List<Long> getCompletedSkillIds(
            Long userId,
            Long jobRoleId) {

        return progressRepository
                .findByUserIdAndJobRoleId(userId, jobRoleId)
                .stream()
                .filter(RoadmapProgress::isCompleted)
                .map(progress -> progress.getSkill().getId())
                .toList();
    }

    @Transactional
    public void updateProgress(
            Long userId,
            Long jobRoleId,
            Long skillId,
            boolean completed) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found"));

        JobRole jobRole = jobRoleRepository.findById(jobRoleId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Job role not found"));

        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Skill not found"));

        boolean belongsToRole = jobRole.getRequiredSkills()
                .stream()
                .anyMatch(requiredSkill ->
                        requiredSkill.getId().equals(skillId));

        if (!belongsToRole) {
            throw new IllegalArgumentException(
                    "This skill does not belong to the selected job role");
        }

        RoadmapProgress progress = progressRepository
                .findByUserIdAndJobRoleIdAndSkillId(
                        userId, jobRoleId, skillId)
                .orElseGet(RoadmapProgress::new);

        progress.setUser(user);
        progress.setJobRole(jobRole);
        progress.setSkill(skill);
        progress.setCompleted(completed);

        progressRepository.save(progress);
    }
}
