
package com.skillgap.skillgap.repository;

import com.skillgap.skillgap.entity.JobRoleSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JobRoleSkillRepository
        extends JpaRepository<JobRoleSkill, Long> {

    // Get required skills for a job role
    List<JobRoleSkill> findByJobRoleId(Long jobRoleId);

    // Check whether a skill is assigned to any job role
    boolean existsBySkillId(Long skillId);

    // Find existing role-skill relationship
    Optional<JobRoleSkill> findByJobRoleIdAndSkillId(
            Long jobRoleId,
            Long skillId
    );
}
