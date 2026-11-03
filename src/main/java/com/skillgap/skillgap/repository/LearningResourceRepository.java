
package com.skillgap.skillgap.repository;

import com.skillgap.skillgap.entity.LearningResource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LearningResourceRepository
        extends JpaRepository<LearningResource, Long> {

    List<LearningResource> findBySkillIdOrderByLearningOrderAsc(
            Long skillId
    );

    boolean existsBySkillId(Long skillId);

    boolean existsBySkillIdAndTitleIgnoreCase(
            Long skillId,
            String title
    );

    long countBySkillId(Long skillId);
}
