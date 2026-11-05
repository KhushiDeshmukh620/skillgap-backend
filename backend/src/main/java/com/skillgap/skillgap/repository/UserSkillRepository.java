
package com.skillgap.skillgap.repository;

import com.skillgap.skillgap.entity.UserSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserSkillRepository extends JpaRepository<UserSkill, Long> {

    List<UserSkill> findByUserId(Long userId);

    Optional<UserSkill> findByUserIdAndSkillId(
            Long userId,
            Long skillId
    );

    // Check whether any user has this skill
    boolean existsBySkillId(Long skillId);
}
